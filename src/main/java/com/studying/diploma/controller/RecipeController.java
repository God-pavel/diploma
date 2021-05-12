package com.studying.diploma.controller;

import com.studying.diploma.model.Recipe;
import com.studying.diploma.model.RecipeCategory;
import com.studying.diploma.service.ProductService;
import com.studying.diploma.service.RecipeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Log4j2
@Controller
@RequestMapping("/findRecipe")
public class RecipeController {

    private final RecipeService recipeService;
    private final ProductService productService;

    public RecipeController(RecipeService recipeService, ProductService productService) {
        this.recipeService = recipeService;
        this.productService = productService;
    }

    @GetMapping
    public String criteriaPage(Model model) {
        model.addAttribute("categories", RecipeCategory.values());
        model.addAttribute("products", productService.getAllProducts());
        return "criteria";
    }

    @PostMapping
    public String sendCriteria(Model model,
                               RedirectAttributes ra,
                               @RequestParam(value = "time", required = false) Integer time,
                               @RequestParam(value = "category", required = false) String category,
                               @RequestParam(value = "products", required = false) List<String> products) {
        Page<Recipe> result = recipeService.findRecipes(time, category, products);
        ra.addFlashAttribute("recipes", result);
        return "redirect:/main";
    }
}
