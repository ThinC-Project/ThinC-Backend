package com.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class LoginFormDto {

    @NotBlank(message = "Please enter your ID!")
    private String ID;

    @NotBlank(message = "Please enter your password!")
    private String Password;

    @Builder
    public LoginFormDto(String ID, String Password, String Nickname){
        this.ID = ID;
        this.Password = Password;
    }

}
