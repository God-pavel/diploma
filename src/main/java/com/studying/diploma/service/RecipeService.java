package com.studying.diploma.service;

import com.studying.diploma.model.*;
import com.studying.diploma.repository.IngredientRepository;
import com.studying.diploma.repository.MarkRepository;
import com.studying.diploma.repository.RecipeRepository;
import com.studying.diploma.repository.TemporaryRecipeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class RecipeService {

    public static final Integer MIN_COMMON_MARKS = 5;
    public static final Integer MAX_AVG_MARKS_DIFFERENCE = 2;
    public static final Integer SIMILAR_USERS = 3;
    public static final Integer MIN_RECOMMENDATION_MARK = 9;


    private final ProductService productService;
    private final UserService userService;
    private final RecipeRepository recipeRepository;
    private final TemporaryRecipeRepository temporaryRecipeRepository;
    private final IngredientRepository ingredientRepository;
    private final MarkRepository markRepository;


    public RecipeService(RecipeRepository recipeRepository, TemporaryRecipeRepository temporaryRecipeRepository, ProductService productService, UserService userService, IngredientRepository ingredientRepository, MarkRepository markRepository) {
        this.recipeRepository = recipeRepository;
        this.temporaryRecipeRepository = temporaryRecipeRepository;
        this.productService = productService;
        this.userService = userService;
        this.ingredientRepository = ingredientRepository;
        this.markRepository = markRepository;
    }

    public Page<Recipe> getAllRecipes(Pageable pageable) {

        return recipeRepository.findAll(pageable);
    }

    public TemporaryRecipe getTemporaryRecipeById(long id) {

        return temporaryRecipeRepository.findById(id).get();
    }

    public TemporaryRecipe createEmptyRecipe() {
        return temporaryRecipeRepository.save(TemporaryRecipe.builder()
                .text("")
                .build());
    }

    public void saveRecipe(final Long recipeId,
                           final Integer time,
                           final String name,
                           final String text,
                           final String category,
                           final User user) {
        final TemporaryRecipe temporaryRecipe = getTemporaryRecipeById(recipeId);
        temporaryRecipeRepository.delete(temporaryRecipe);
        final Recipe recipe = Recipe.builder()
                .rate(0d)
                .text(text)
                .creator(user)
                .recipeCategory(RecipeCategory.valueOf(category))
                .ingredients(temporaryRecipe.getIngredients())
                .name(name)
                .time(time)
                .totalEnergy(calculateRecipeEnergy(temporaryRecipe.getIngredients()))
                .build();
        recipeRepository.save(recipe);
        log.info("Check was saved. Check id: " + recipe.getId());
    }

    public void addIngredientToRecipe(final Long recipeId,
                                      final String productName,
                                      final Long weight) {
        final TemporaryRecipe temporaryRecipe = getTemporaryRecipeById(recipeId);
        final Product product = productService.getProductByName(productName).get();
        final Ingredient ingredient = Ingredient.builder()
                .product(product)
                .weight(weight)
                .build();
        ingredientRepository.save(ingredient);
        temporaryRecipe.addIngredient(ingredient);
        temporaryRecipeRepository.save(temporaryRecipe);
    }

    public Page<Recipe> findRecipes(final Integer time,
                                    final String category,
                                    final List<String> products) {
        return new PageImpl<>(recipeRepository.findAll().stream()
                .filter(recipe -> time == null || recipe.getTime() <= time)
                .filter(recipe -> category == null || recipe.getRecipeCategory().equals(RecipeCategory.valueOf(category)))
                .filter(recipe -> products == null || recipe.getIngredients().stream().map(ingredient -> ingredient.getProduct().getName()).collect(Collectors.toList()).containsAll(products))
                .collect(Collectors.toList())
        );
    }


    public boolean rateRecipe(final Recipe recipe,
                              final Integer rate,
                              final User user) {
        if (recipe.getMarks().stream().map(m -> m.getUser().getId()).anyMatch(id -> id.equals(user.getId()))) {
            return false;
        }
        final Mark mark = Mark.builder().mark(rate).user(user).recipe(recipe).build();
        markRepository.save(mark);
        recipe.getMarks().add(mark);
        recipe.calculateRate();
        recipeRepository.save(recipe);
        return true;
    }

    public Page<Recipe> getUserRecommendations(final User user) {
        return new PageImpl<>(userService.getSimilarUsers(user).stream()
                .flatMap(similarUser -> similarUser.getMarks().stream())
                .filter(mark -> mark.getMark() >= MIN_RECOMMENDATION_MARK)
                .map(Mark::getRecipe)
                .filter(recipe -> filterPreviouslyRatedRecipes(user, recipe))
                .distinct()
                .collect(Collectors.toList()));
    }

    private boolean filterPreviouslyRatedRecipes(User user, Recipe recipe) {
        return userService.getUserById(user.getId()).getMarks()
                .stream()
                .map(Mark::getRecipe)
                .noneMatch(recipe1 -> recipe1.equals(recipe));
    }

    private BigDecimal calculateRecipeEnergy(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(ingredient -> ingredient.getProduct().getEnergy().multiply(BigDecimal.valueOf(ingredient.getWeight()).divide(BigDecimal.valueOf(100))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
