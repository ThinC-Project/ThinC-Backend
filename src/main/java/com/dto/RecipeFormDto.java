package com.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class RecipeFormDto {

    @NotBlank(message = "Please enter title!")
    private String title;

    @NotBlank(message = "Please enter integrates!")
    private String integrate;

    @NotBlank(message = "Please enter process!")
    private String process;

    private String img_food;

    @NotBlank(message = "Select category!")
    private String category;

    private String owner;


    @Builder
    public RecipeFormDto(String title, String integrate, String process, String img_food, String category, String owner){
        this.title = title;
        this.integrate = integrate;
        this.process = process;
        this.img_food = img_food;
        this.category = category;
        this.owner = owner;
    }

}
