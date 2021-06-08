package com.studying.diploma.controller;

import com.studying.diploma.model.Recipe;
import com.studying.diploma.service.RecipeService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
@RequestMapping("/main")
public class MainController {

    private final RecipeService recipeService;

    public MainController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public String mainPage(@ModelAttribute("message") String message,
                           @ModelAttribute("recipes") Object recipes,
                           @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                           Model model) {
        final Page<Recipe> page;
        if (isRecipesFiltered(recipes)) {
            page = (PageImpl) recipes;
        } else {
            page = recipeService.getAllRecipes(pageable);
        }
        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("message", message);
        return "main";
    }

    @SneakyThrows
    @GetMapping("/sort")
    public String sort(@ModelAttribute("message") String message,
                       @ModelAttribute("recipes") Object recipes,
                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                       @ModelAttribute(name = "sort") String sort,
                       Model model) {
        final Page<Recipe> page;
        PageRequest pageRequest = (PageRequest) pageable;
        FieldUtils.writeField(pageRequest, "sort", Sort.by(sort).descending(), true);
        page = recipeService.getAllRecipes(pageRequest);
        model.addAttribute("page", page);
        model.addAttribute("url", "/main/sort?sort=" + sort + "&");
        model.addAttribute("message", message);
        return "main";
    }

    @PostMapping("/findRecipe")
    public String findRecipe() {
        return "redirect:/findRecipe";
    }

    @PostMapping("/fastFindRecipe")
    public String fastFindRecipe() {
        return "redirect:/fastFindRecipe";
    }

    @PostMapping("/createRecipe")
    public String createRecipe() {
        return "redirect:/createRecipe";
    }

    private boolean isRecipesFiltered(Object recipes) {
        try {
            PageImpl page = (PageImpl) recipes;
        } catch (ClassCastException e) {
            return false;
        }
        return true;
    }
}