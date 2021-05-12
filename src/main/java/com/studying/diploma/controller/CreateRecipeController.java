package com.studying.diploma.controller;

import com.studying.diploma.model.RecipeCategory;
import com.studying.diploma.model.TemporaryRecipe;
import com.studying.diploma.model.User;
import com.studying.diploma.service.ProductService;
import com.studying.diploma.service.RecipeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@Controller
@RequestMapping("/createRecipe")
public class CreateRecipeController {

    private final RecipeService recipeService;
    private final ProductService productService;

    public CreateRecipeController(RecipeService recipeService, ProductService productService) {
        this.recipeService = recipeService;

        this.productService = productService;
    }

    @GetMapping
    public String showRecipe(@AuthenticationPrincipal User user, Model model) {
        TemporaryRecipe recipe = recipeService.createEmptyRecipe(user);
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
                             @RequestParam("time") Integer time,
                             @RequestParam("name") String name,
                             @RequestParam("text") String text,
                             @RequestParam("category") String category) {
        recipeService.saveRecipe(recipeId, time, name, text, category);

        return "redirect:/main";
    }

    @PostMapping("/addIngredient/{recipeId}")
    public String addByName(@PathVariable Long recipeId,
                            @RequestParam("productName") String productName,
                            @RequestParam("weight") Long weight,
                            RedirectAttributes ra) {

        recipeService.addIngredientToRecipe(recipeId, productName, weight);


        return "redirect:/createRecipe/" + recipeId;
    }
}
