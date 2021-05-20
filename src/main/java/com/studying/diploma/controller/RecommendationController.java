package com.studying.diploma.controller;

import com.studying.diploma.model.Recipe;
import com.studying.diploma.model.User;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecipeService recipeService;

    public RecommendationController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public String recommendationPage(@ModelAttribute("message") String message,
                                     @AuthenticationPrincipal User user,
                                     @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                                     Model model) {
        Page<Recipe> page = recipeService.getUserRecommendations(user);
        model.addAttribute("page", page);
        model.addAttribute("url", "/recommendation");
        return "recommendations";
    }
}