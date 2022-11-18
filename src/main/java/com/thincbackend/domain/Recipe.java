package com.thincbackend.domain;

import com.thincbackend.dto.RecipeFormDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "recipes")
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "recipe_id")
    private Long id;

    private String title;

    private String integrate;

    private String process;

    private String img_food;

    private String category;

    private String owner;

    @Builder
    public Recipe(String title, String integrate, String process, String img_food, String category, String owner){
        this.title = title;
        this.integrate = integrate;
        this.process = process;
        this.img_food = img_food;
        this.category = category;
        this.owner = owner;
    }

    public static Recipe createRecipe(RecipeFormDto recipeFormDto){
        Recipe recipe = Recipe.builder()
                .title(recipeFormDto.getTitle())
                .integrate(recipeFormDto.getIntegrate())
                .process(recipeFormDto.getProcess())
                .img_food(recipeFormDto.getImg_food())
                .category(recipeFormDto.getCategory())
                .owner(recipeFormDto.getOwner())
                .build();

        return recipe;
    }
}
