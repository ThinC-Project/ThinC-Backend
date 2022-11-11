package com.service;

import com.domain.Recipe;
import com.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
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

    public List<Recipe> findByKeyword(String keyword){
        return recipeRepository.findByKeyword(keyword);
    }

    public List<Recipe> findByCategory(String category){
        return recipeRepository.findByCategory(category);
    }

    public List<Recipe> findOwnerRecipe(String owner){
        return recipeRepository.findOwnerRecipe(owner);
    }

    public List<Recipe> findOtherRecipe(String owner){
        return recipeRepository.findOtherRecipe(owner);
    }

    public Recipe loadRecipeByRecipeId(Long id){
        Recipe recipe = recipeRepository.findByRecipeId(id);

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
