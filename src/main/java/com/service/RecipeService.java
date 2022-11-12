package com.service;

import com.domain.Recipe;
import com.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class RecipeService {
    private RecipeRepository recipeRepository;

    public Recipe saveRecipe(Recipe recipe){
        return recipeRepository.save(recipe);
    }

    public List<Recipe> findAllRecipe(){
        return recipeRepository.findAllRecipe();
    }

    public Recipe findRecipeById(Long id){
        return recipeRepository.findRecipeById(id);
    }

    public List<Recipe> findRecipeByKeyword(String keyword){
        return recipeRepository.findRecipeByKeyword(keyword);
    }

    public List<Recipe> findRecipeByCategory(String category){
        return recipeRepository.findRecipeByCategory(category);
    }

    public List<Recipe> findOwnerRecipe(String owner){
        return recipeRepository.findOwnerRecipe(owner);
    }

    public List<Recipe> findOtherRecipe(String owner){
        return recipeRepository.findOtherRecipe(owner);
    }

    public Recipe loadRecipeByRecipeId(Long id){
        Recipe recipe = recipeRepository.findRecipeById(id);

        return Recipe.builder()
                .title(recipe.getTitle())
                .integrate(recipe.getIntegrate())
                .process(recipe.getProcess())
                .img_food(recipe.getImg_food())
                .category(recipe.getCategory())
                .owner(recipe.getOwner())
                .build();
    }
}
