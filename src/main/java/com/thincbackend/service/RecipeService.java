package com.thincbackend.service;

import com.thincbackend.domain.Recipe;
import com.thincbackend.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public Recipe saveRecipe(Recipe recipe){
        return recipeRepository.save(recipe);
    }

    public List<Recipe> findAllRecipe(){
        return recipeRepository.findAll();
    }

    public Optional<Recipe> findRecipeById(Long id){
        return recipeRepository.findById(id);
    }

    public List<Recipe> findRecipeByKeyword(String keyword){
        return recipeRepository.findByTitleContainingOrIntegrateContainingOrProcessContaining(keyword, keyword, keyword);
    }

    public List<Recipe> findRecipeByCategory(String category){
        return recipeRepository.findByCategory(category);
    }

    public List<Recipe> findOwnerRecipe(String owner){
        return recipeRepository.findByOwner(owner);
    }

    public List<Recipe> findOtherRecipe(String owner){
        return recipeRepository.findByOwnerNot(owner);
    }

    public void deleteRecipeById(String owner, Long id){
        checkRecipeOwner(owner, id);
        checkRecipeExist(id);
        recipeRepository.deleteById(id);
    };

    public Optional<Recipe> checkRecipeExist(Long id){
        Optional<Recipe> findRecipe = recipeRepository.findById(id);
        if(findRecipe.isEmpty()){
            throw new IllegalStateException("레시피가 존재하지 않습니다.");
        }
        return findRecipe;
    }

    public void checkRecipeOwner(String owner, Long id){
        Optional<Recipe> findRecipe = recipeRepository.findById(id);
        if(findRecipe.get().getOwner()!=owner){
            throw new IllegalStateException("권한이 없습니다.");
        }
    }
}
