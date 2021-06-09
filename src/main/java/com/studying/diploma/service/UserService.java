package com.studying.diploma.service;

import com.studying.diploma.dto.UserDTO;
import com.studying.diploma.model.Mark;
import com.studying.diploma.model.Recipe;
import com.studying.diploma.model.Role;
import com.studying.diploma.model.User;
import com.studying.diploma.repository.MarkRepository;
import com.studying.diploma.repository.RecipeRepository;
import com.studying.diploma.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.studying.diploma.config.MvcConfig.PHOTO_FOLDER;
import static com.studying.diploma.service.RecipeService.SIMILAR_USERS;

@Log4j2
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RecipeRepository recipeRepository;
    private final MarkRepository markRepository;
    private final AmazonClient amazonClient;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RecipeRepository recipeRepository, MarkRepository markRepository, AmazonClient amazonClient) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.recipeRepository = recipeRepository;
        this.markRepository = markRepository;
        this.amazonClient = amazonClient;
    }


    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findByUsername(email);

        if (user.isEmpty()) {
            log.warn(MessageFormat.format("User with email {0} cannot be found.", email));
            throw new UsernameNotFoundException(MessageFormat.format("User with email {0} cannot be found.", email));
        }

        return user.get();
    }

    public boolean saveNewUser(final UserDTO userdto) {
        final Optional<User> userFromDb = userRepository.findByUsername(userdto.getUsername());

        if (userFromDb.isPresent()) {
            log.warn("email not unique!");
            return false;
        }
        final User user = User.builder()
                .username(userdto.getUsername())
                .password(passwordEncoder.encode(userdto.getPassword()))
                .active(true)
                .roles(Set.of(Role.USER))
                .name(userdto.getName())
                .surname(userdto.getSurname())
                .build();
        userRepository.save(user);
        log.info("User was saved. Username : " + user.getUsername());
        return true;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void userEditRoles(final User user,
                              final Map<String, String> form) {
        final Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        form.keySet().stream()
                .filter(roles::contains)
                .forEach(key -> user.getRoles().add(Role.valueOf(key)));

        userRepository.save(user);
    }

    public boolean userEdit(final User user,
                            final UserDTO userDTO,
                            final MultipartFile multipartFile) {
        if (!user.getUsername().equals(userDTO.getUsername()) && userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            log.warn("email not unique!");
            return false;
        }

        if (multipartFile.getOriginalFilename() != null && !StringUtils.cleanPath(multipartFile.getOriginalFilename()).equals("")) {
            String fileName = generateFileName(multipartFile);
            user.setPhoto(fileName);
            String uploadDir = PHOTO_FOLDER + user.getId();
            try {
                amazonClient.uploadFile(multipartFile, uploadDir + "/" + fileName);
            } catch (Exception e) {
                log.warn("File " + fileName + " can't be saved!");
            }
        }

        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setUsername(userDTO.getUsername());

        userRepository.save(user);

        return true;
    }

    public User getUserById(final Long id) {
        return userRepository.findById(id).get();
    }

    public List<User> getSimilarUsers(final User user) {
        return createCosDifMap(user).entrySet().stream()
                .sorted(Map.Entry.<User, Double> comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .limit(SIMILAR_USERS)
                .collect(Collectors.toList());
    }

    public Map<User, Double> createCosDifMap(User object) {
        return userRepository.findAll().stream()
                .filter(user -> !user.getId().equals(object.getId()))
                .collect(Collectors.toMap(user -> user, user -> calcCosDif(object, user)));
    }

    private double calcCosDif(User user1, User user2) {
        List<Recipe> allRecipes = recipeRepository.findAll();
        List<Integer> user1Marks = new ArrayList<>();
        List<Integer> user2Marks = new ArrayList<>();

        allRecipes.forEach(recipe -> {
            user1Marks.add(getRecipeRateByUser(user1, recipe));
            user2Marks.add(getRecipeRateByUser(user2, recipe));
        });

        return calcResult(user1Marks, user2Marks);
    }

    private double calcResult(List<Integer> user1Marks, List<Integer> user2Marks) {
        final double divider = calcLength(user1Marks) * calcLength(user2Marks);
        if (divider == 0){
            return 0;
        }
        final double divided = calcSum(user1Marks, user2Marks);
        return divided / divider;
    }

    private Double calcSum(final List<Integer> marks1,
                        final List<Integer> marks2) {
        return IntStream.range(0, marks1.size())
                .mapToDouble(i -> marks1.get(i) * marks2.get(i))
                .sum();
    }

    private Double calcLength(final List<Integer> marks) {
        return Math.sqrt(marks.stream()
                .mapToDouble(mark -> mark * mark)
                .sum());
    }

    private Integer getRecipeRateByUser(User user, Recipe recipe) {
        return recipe.getMarks().stream()
                .filter(mark -> mark.getUser().getId().equals(user.getId()))
                .map(Mark::getMark)
                .findFirst().orElse(0);
    }

    @PostConstruct
    public void add() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User user = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("1"))
                    .name("admin")
                    .surname("admin")
                    .build();
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.ADMIN));
            userRepository.save(user);
        }

    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }
}
