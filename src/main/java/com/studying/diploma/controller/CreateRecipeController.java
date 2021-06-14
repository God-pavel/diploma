package com.studying.diploma.controller;

import com.studying.diploma.model.RecipeCategory;
import com.studying.diploma.model.TemporaryRecipe;
import com.studying.diploma.model.User;
import com.studying.diploma.service.ProductService;
import com.studying.diploma.service.RecipeService;
import com.studying.diploma.validator.IngredientValidator;
import com.studying.diploma.validator.RecipeValidator;
import com.studying.diploma.validator.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@Controller
@RequestMapping("/createRecipe")
public class CreateRecipeController {

    private final RecipeService recipeService;
    private final ProductService productService;
    private final RecipeValidator recipeValidator;
    private final IngredientValidator ingredientValidator;

    public CreateRecipeController(RecipeService recipeService, ProductService productService, RecipeValidator recipeValidator, IngredientValidator ingredientValidator) {
        this.recipeService = recipeService;
        this.productService = productService;
        this.recipeValidator = recipeValidator;
        this.ingredientValidator = ingredientValidator;
    }

    @GetMapping
    public String showRecipe(Model model) {
        TemporaryRecipe recipe = recipeService.createEmptyRecipe();
        model.addAttribute("recipe", recipe);
        model.addAttribute("categories", RecipeCategory.values());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("message", "");
        return "create_recipe";
    }

    @GetMapping("{recipeId}")
    public String recipeCreatingPage(@PathVariable Long recipeId,
                                     @ModelAttribute("message") String message,
                                     Model model) {
        TemporaryRecipe recipe = recipeService.getTemporaryRecipeById(recipeId);
        model.addAttribute("recipe", recipe);
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", RecipeCategory.values());
        model.addAttribute("message", message);
        return "create_recipe";
    }

    @PostMapping("{recipeId}")
    public String saveRecipe(@PathVariable Long recipeId,
                             @AuthenticationPrincipal User user,
                             @RequestParam("time") Integer time,
                             @RequestParam("name") String name,
                             @RequestParam("text") String text,
                             @RequestParam("category") String category,
                             @RequestParam("image") MultipartFile photo,
                             @RequestParam("video") MultipartFile video,
                             RedirectAttributes ra) {
        Result result = recipeValidator.validate(time, name);
        if (!result.isValid()) {
            ra.addFlashAttribute("message", result.getMessage());
            log.warn(result.getMessage());
            return "redirect:/createRecipe/" + recipeId;
        }
        if (!recipeService.saveRecipe(recipeId, time, name, text, category, user, photo, video)) {
            ra.addFlashAttribute("error", "error");
            return "redirect:/createRecipe/" + recipeId;
        }
        return "redirect:/main";
    }

    @PostMapping("/addIngredient/{recipeId}")
    public String addByName(@PathVariable Long recipeId,
                            @RequestParam("productName") String productName,
                            @RequestParam("weight") Long weight,
                            RedirectAttributes ra) {
        Result result = ingredientValidator.validate(weight);
        if (!result.isValid()) {
            ra.addFlashAttribute("message", result.getMessage());
            log.warn(result.getMessage());
            return "redirect:/createRecipe/" + recipeId;
        }
        recipeService.addIngredientToRecipe(recipeId, productName, weight);


        return "redirect:/createRecipe/" + recipeId;
    }
}
