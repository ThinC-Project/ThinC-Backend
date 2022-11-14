package com.thincbackend.controller;

import com.thincbackend.domain.Recipe;
import com.thincbackend.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeRepository recipeRepository;

    @GetMapping("/")
    public String Recipe(Model model){
        List<Recipe> recipeList = recipeRepository.findAllRecipe();
        model.addAttribute("recipeList", recipeList);

        return "recipe";
    }

    @GetMapping("/recipe_category_list")
    public String RecipeCategoryList(@RequestParam(value = "category", defaultValue = "") String category, Model model){
        List<Recipe> recipeList = recipeRepository.findRecipeByCategory(category);
        model.addAttribute("recipeList", recipeList);

        return "recipe";
    }

    @GetMapping("/recipe_search_list")
    public String RecipeSearchList(@RequestParam(value = "keyword", defaultValue = "") String keyword, Model model){
        List<Recipe> recipeList = recipeRepository.findRecipeByKeyword(keyword);
        model.addAttribute("recipeList", recipeList);

        return "recipe";
    }

    @GetMapping("/recipe_detail")
    public String RecipeDetail(@RequestParam(value = "recipe_id", defaultValue = "0") Long recipe_id, Model model){
        Recipe recipe = recipeRepository.findRecipeById(recipe_id);
        model.addAttribute("Recipe", recipe);

        return "recipeDetailPage";
    }
}
