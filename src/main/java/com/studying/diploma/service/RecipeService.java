package com.studying.diploma.service;

import com.studying.diploma.model.*;
import com.studying.diploma.repository.IngredientRepository;
import com.studying.diploma.repository.RecipeRepository;
import com.studying.diploma.repository.TemporaryRecipeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final TemporaryRecipeRepository temporaryRecipeRepository;
    private final ProductService productService;
    private final IngredientRepository ingredientRepository;


    public RecipeService(RecipeRepository recipeRepository, TemporaryRecipeRepository temporaryRecipeRepository, ProductService productService, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.temporaryRecipeRepository = temporaryRecipeRepository;
        this.productService = productService;
        this.ingredientRepository = ingredientRepository;
    }

    public Page<Recipe> getAllRecipes(Pageable pageable) {

        return recipeRepository.findAll(pageable);
    }

    public List<Recipe> getAllRecipes() {

        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipeById(long id) {

        return recipeRepository.findById(id);
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
                .rate(0)
                .text(text)
                .creator(user)
                .recipeCategory(RecipeCategory.valueOf(category))
                .ingredients(temporaryRecipe.getIngredients())
                .name(name)
                .time(time)
                .usersRated(Set.of())
                //Todo calc
                .totalEnergy(BigDecimal.ZERO)
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
        if (recipe.getUsersRated().contains(user)) {
            return false;
        }
        recipe.getUsersRated().add(user);
        recipe.setRate(calculateRate(recipe, rate));
        recipeRepository.save(recipe);
        return true;
    }

    private int calculateRate(final Recipe recipe,
                              final Integer rate) {
        return (recipe.getRate() * (recipe.getUsersRated().size() - 1) + rate) / recipe.getUsersRated().size();
    }
}
