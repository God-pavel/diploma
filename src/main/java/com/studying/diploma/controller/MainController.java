package com.studying.diploma.controller;

import com.studying.diploma.model.Recipe;
import com.studying.diploma.service.RecipeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        Page<Recipe> page = isRecipesFiltered(recipes) ? (PageImpl) recipes : recipeService.getAllRecipes(pageable);
        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("message", message);
        return "main";
    }

    @PostMapping("/findRecipe")
    public String findRecipe() {
        return "redirect:/findRecipe";
    }

    @PostMapping("/createRecipe")
    public String createRecipe() {
        return "redirect:/createRecipe";
    }

//    @PostMapping("/deleteCheck")
//    public String deleteCheck(Long checkId, RedirectAttributes ra) {
//        try {
//            checkService.deleteCheck(checkId);
//        } catch (CheckCantBeDeleted e) {
//            log.warn(e.getMessage());
//            ra.addFlashAttribute("message", e.getMessage());
//        }
//        return "redirect:/main";
//    }
    private boolean isRecipesFiltered(Object recipes){
        try{
            PageImpl page = (PageImpl) recipes;
        } catch (ClassCastException e){
            return false;
        }
        return true;
    }
}