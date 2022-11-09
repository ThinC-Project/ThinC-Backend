package com.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class UserFormDto {

    @NotBlank(message = "Please enter your ID!")
    private String ID;

    @NotBlank(message = "Please enter your password!")
    @Length(min=8, max=16, message = "Please enter the password in 8 to 16 characters!")
    private String Password;

    @NotBlank(message = "Please enter your Nickname!")
    private String Nickname;


    @Builder
    public UserFormDto(String ID, String Password, String Nickname){
        this.ID = ID;
        this.Password = Password;
        this.Nickname = Nickname;
    }

}
