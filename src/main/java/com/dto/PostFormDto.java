package com.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class PostFormDto {
    @NotBlank(message = "Please enter title!")
    private String title;

    @NotBlank(message = "Please enter content!")
    @Length(min = 2, max = 1000, message = "You can enter up to 1000 characters.")
    private String content;

    private String img_post;

    private String owner;

    @Builder
    public PostFormDto(String title, String content, String img_post, String owner){
        this.title = title;
        this.content = content;
        this.img_post = img_post;
        this.owner = owner;
    }
}
