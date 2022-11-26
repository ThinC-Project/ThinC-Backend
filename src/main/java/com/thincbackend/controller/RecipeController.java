package com.thincbackend.controller;

import com.thincbackend.domain.Recipe;
import com.thincbackend.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/")
    public String Recipe(Model model){
        List<Recipe> recipeList = recipeService.findAllRecipe();
        model.addAttribute("recipeList", recipeList);

        return "recipe";
    }

    @GetMapping("/recipe_category_list")
    public String RecipeCategoryList(@RequestParam(value = "category", defaultValue = "") String category, Model model){
        List<Recipe> recipeList = recipeService.findRecipeByCategory(category);
        model.addAttribute("recipeList", recipeList);

        return "recipe";
    }

    @GetMapping("/recipe_search_list")
    public String RecipeSearchList(@RequestParam(value = "keyword", defaultValue = "") String keyword, Model model){
        List<Recipe> recipeList = recipeService.findRecipeByKeyword(keyword);
        model.addAttribute("recipeList", recipeList);

        return "recipe";
    }

    @GetMapping("/recipe_detail")
    public String RecipeDetail(@RequestParam(value = "recipe_id", defaultValue = "0") Long recipe_id, Model model){
        Optional<Recipe> recipe = recipeService.findRecipeById(recipe_id);
        model.addAttribute("Recipe", recipe);

        return "recipeDetailPage";
    }

    @GetMapping("/recipe_bookmark")
    public String RecipeBookmark(HttpServletRequest request){
        Long recipe_id = Long.parseLong(request.getParameter("recipe_id"));
        System.out.println("recipe id : "+recipe_id);
        return "test";
    }
}
