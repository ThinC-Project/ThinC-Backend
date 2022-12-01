package com.thincbackend.service;

import com.thincbackend.domain.Recipe;
import com.thincbackend.dto.RecipeFormDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {"spring.config.location=classpath:application.properties"})
class RecipeServiceTest {
    @Autowired
    private RecipeService recipeService;

    public Recipe createRecipe(String title, String owner, String category){
        RecipeFormDto recipeFormDto = RecipeFormDto.builder()
                .title(title)
                .category(category)
                .integrate("apple, mango, egg")
                .process("test")
                .img_food("/drive/test/img.png")
                .owner(owner)
                .build();
        return Recipe.createRecipe(recipeFormDto);
    }

    @Test
    @DisplayName("레시피_등록_테스트")
    public void saveRecipeTest(){
        Recipe recipe = createRecipe("recipe1", "owner1", "category1");

        Recipe savedRecipe = recipeService.saveRecipe(recipe);

        assertThat(savedRecipe.getTitle()).isEqualTo("recipe1");
    }

    @Test
    @DisplayName("레시피_전체_테스트")
    public void findAllRecipeTest(){
        Recipe recipe1 = createRecipe("recipe1", "owner1", "category1");
        Recipe recipe2 = createRecipe("recipe2", "owner1", "category2");
        Recipe recipe3 = createRecipe("recipe3", "owner1", "category3");

        recipeService.saveRecipe(recipe1);
        recipeService.saveRecipe(recipe2);
        recipeService.saveRecipe(recipe3);

        List<Recipe> recipeList = recipeService.findAllRecipe();
        System.out.println(recipeList);
    }

    @Test
    @DisplayName("멤버_레시피_테스트")
    public void memberFindTest(){
        Recipe recipe1 = createRecipe("recipe1", "owner1", "category1");
        Recipe recipe2 = createRecipe("recipe2", "owner2", "category2");
        Recipe recipe3 = createRecipe("recipe3", "owner1", "category3");

        recipeService.saveRecipe(recipe1);
        recipeService.saveRecipe(recipe2);
        recipeService.saveRecipe(recipe3);

        List<Recipe> recipeList = recipeService.findAllRecipe();
        List<Recipe> ownerRecipeList = recipeService.findOwnerRecipe("owner1");
        List<Recipe> otherRecipeList = recipeService.findOtherRecipe("owner2");

        System.out.println(recipeList);
        System.out.println(ownerRecipeList);
        System.out.println(otherRecipeList);
    }

    @Test
    @DisplayName("포스트_찾기_테스트")
    public void postDetailTest(){
        Recipe recipe1 = createRecipe("recipe1", "owner1", "category1");
        Recipe recipe2 = createRecipe("recipe2", "owner2", "category2");
        Recipe recipe3 = createRecipe("recipe3", "owner1", "category2");

        recipeService.saveRecipe(recipe1);
        recipeService.saveRecipe(recipe2);
        recipeService.saveRecipe(recipe3);

        Optional<Recipe> recipeOptional1 = recipeService.findRecipeById(recipe1.getId());
        Optional<Recipe> recipeOptional2 = recipeService.findRecipeById(recipe2.getId());

        assertThat(recipeOptional1.get().getTitle()).isEqualTo(recipe1.getTitle());
        assertThat(recipeOptional2.get().getTitle()).isEqualTo(recipe2.getTitle());

    }

    @Test
    @DisplayName("레시피_삭제_테스트")
    public void recipeDeleteTest(){
        Recipe recipe1 = createRecipe("post1", "owner1", "category1");

        recipeService.saveRecipe(recipe1);

        assertThat(recipeService.findRecipeById(recipe1.getId()).get().getTitle()).isEqualTo(recipe1.getTitle());
        //현재 유저를 owner2로 설정하여 owner1이 작성한 포스트 삭제 테스트
        IllegalStateException e1 = assertThrows(IllegalStateException.class, () -> recipeService.deleteRecipeById("owner2", recipe1.getId()));
        assertThat(e1.getMessage()).isEqualTo("권한이 없습니다.");

        recipeService.deleteRecipeById("owner1", recipe1.getId());

        //게시글 존재 여부 테스트
        IllegalStateException e2 = assertThrows(IllegalStateException.class, () -> recipeService.findRecipeById(recipe1.getId()));
        assertThat(e2.getMessage()).isEqualTo("레시피가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("레시피_수정_테스트")
    public void recipeUpdateTest(){
        Recipe recipe1 = createRecipe("recipe1", "owner1", "category1");
        Recipe savedRecipe = recipeService.saveRecipe(recipe1);
        recipe1.setProcess("change Process");
        Optional<Recipe> findRecipe = recipeService.findRecipeById(recipe1.getId());
        assertThat(findRecipe.get().getTitle()).isEqualTo("recipe1");
        assertThat(findRecipe.get().getProcess()).isEqualTo("change Process");
    }

    @Test
    @DisplayName("레시피_키워드_조회_테스트")
    public void recipeFindByKeywordTest(){
        Recipe recipe1 = createRecipe("test post test", "owner1", "category1");
        Recipe recipe2 = createRecipe("test keyword test", "owner1", "category1");
        Recipe recipe3 = Recipe.createRecipe(RecipeFormDto.builder()
                .title("test test test")
                .integrate("test, test, test, keyword")
                .process("1. test \n 2. Unique_keywords")
                .img_food("/drive/test/img.png")
                .owner("owner1")
                .build());
        Recipe recipe4 = Recipe.createRecipe(RecipeFormDto.builder()
                .title("test test")
                .integrate("test, test, test")
                .process("1. test \n 2. Unique_keywords")
                .img_food("/drive/test/img.png")
                .owner("owner1")
                .build());

        recipeService.saveRecipe(recipe1);
        recipeService.saveRecipe(recipe2);
        recipeService.saveRecipe(recipe3);
        recipeService.saveRecipe(recipe4);

        List<Recipe> recipeListAll = recipeService.findAllRecipe();
        List<Recipe> recipeListKeyword = recipeService.findRecipeByKeyword("keyword");

        System.out.println(recipeListAll);
        System.out.println(recipeListKeyword);
    }

}