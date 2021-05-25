package com.studying.diploma.service;

import com.studying.diploma.dto.UserDTO;
import com.studying.diploma.model.Mark;
import com.studying.diploma.model.Role;
import com.studying.diploma.model.User;
import com.studying.diploma.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.studying.diploma.service.RecipeService.*;

@Log4j2
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
                            final UserDTO userDTO) {
        if (!user.getUsername().equals(userDTO.getUsername()) && userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            log.warn("email not unique!");
            return false;
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

    public Set<User> getSimilarUsers(final User user) {
        return
//                false ?
                userRepository.findAll().stream()
                        .filter(candidate -> areUsersSimilar(user, candidate))
                        .limit(SIMILAR_USERS)
                        .collect(Collectors.toSet());
//                : userRepository.findAll().stream().limit(3).collect(Collectors.toSet());
    }

    //Todo optimize
    public boolean areUsersSimilar(final User user1,
                                   final User user2) {
        final Map<Mark, Mark> commonMarks = getCommonMarks(getUserById(user1.getId()).getMarks(), getUserById(user2.getId()).getMarks());
        if (commonMarks.size() < MIN_COMMON_MARKS || user1.getId().equals(user2.getId())) {
            return false;
        }
        return commonMarks.entrySet().stream()
                .mapToInt(entry -> Math.abs(entry.getKey().getMark() - entry.getValue().getMark()))
                .average().orElse(10d) <= MAX_AVG_MARKS_DIFFERENCE;
    }

    public Map<Mark, Mark> getCommonMarks(final List<Mark> marks1,
                                          final List<Mark> marks2) {
        return marks1.stream()
                .filter(mark1 -> marks2.stream().anyMatch(mark2 -> mark2.getRecipe().getId().equals(mark1.getRecipe().getId())))
                .collect(Collectors.toMap(mark1 -> mark1, mark1 -> (Mark) marks2.stream().filter(mark2 -> mark2.getRecipe().getId().equals(mark1.getRecipe().getId())).findFirst().get()));
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
}
