package com.thincbackend.nugu;

import com.thincbackend.domain.Recipe;
import com.thincbackend.dto.RecipeFormDto;
import com.thincbackend.service.RecipeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {"spring.config.location=classpath:application.properties"})
class AnswerCookTest {

    @Autowired
    private RecipeService recipeService;

    public Recipe createRecipe(String title, String owner, String category){
        RecipeFormDto recipeFormDto = RecipeFormDto.builder()
                .title(title)
                .category("category")
                .integrate("apple, mango, egg")
                .process("1. test \n 2. test2 \n 3. test3")
                .img_food("/drive/test/img.png")
                .owner(owner)
                .build();
        return Recipe.createRecipe(recipeFormDto);
    }

    @Test
    @DisplayName("test")
    public void findbyTitle(){
        Recipe recipe = createRecipe("닭", "owner", "cate");
        Recipe savedRecipe = recipeService.saveRecipe(recipe);

        Optional<Recipe> findRecipe = recipeService.findByTitleContaining("닭");

        System.out.println(findRecipe.get().getProcess());
    }



}