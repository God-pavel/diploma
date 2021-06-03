package com.studying.diploma.controller;

import com.studying.diploma.model.Recipe;
import com.studying.diploma.model.RecipeCategory;
import com.studying.diploma.model.User;
import com.studying.diploma.service.ProductService;
import com.studying.diploma.service.RecipeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Log4j2
@Controller
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecipeService recipeService;
    private final ProductService productService;

    public RecommendationController(RecipeService recipeService, ProductService productService) {
        this.recipeService = recipeService;
        this.productService = productService;
    }

    @GetMapping
    public String recommendationPage(@ModelAttribute("message") String message,
                                     @ModelAttribute("recipes") Object recipes,
                                     @AuthenticationPrincipal User user,
                                     @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                                     Model model) {
        Page<Recipe> page = isRecipesFiltered(recipes) ? (PageImpl) recipes : new PageImpl<>(recipeService.getUserRecommendations(user));
        model.addAttribute("page", page);
        model.addAttribute("url", "/recommendation");
        return "recommendations";
    }

    @PostMapping("/filterRecipes")
    public String findRecipe() {
        return "redirect:/recommendation/findRecipe";
    }

    @GetMapping("/findRecipe")
    public String criteriaPage(Model model) {
        model.addAttribute("path", "/recommendation");
        model.addAttribute("categories", RecipeCategory.values());
        model.addAttribute("products", productService.getAllProducts());
        return "criteria";
    }

    @PostMapping("/findRecipe")
    public String sendFoundResults(RedirectAttributes ra,
                                   @AuthenticationPrincipal User user,
                                   @RequestParam(value = "time", required = false) Integer time,
                                   @RequestParam(value = "category", required = false) String category,
                                   @RequestParam(value = "products", required = false) List<String> products) {
        Page<Recipe> result = recipeService.findRecommendedRecipes(time, category, products, user);
        ra.addFlashAttribute("recipes", result);
        return "redirect:/recommendation";
    }

    private boolean isRecipesFiltered(Object recipes){
        try{
            PageImpl page = (PageImpl) recipes;
        } catch (ClassCastException e){
            return false;
        }
        return true;
    }
}