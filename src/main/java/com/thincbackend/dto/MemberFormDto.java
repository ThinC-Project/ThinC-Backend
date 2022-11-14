package com.thincbackend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class MemberFormDto {

    @NotBlank(message = "Please enter your ID!")
    private String memberID;

    @NotBlank(message = "Please enter your password!")
    @Length(min=8, max=16, message = "Please enter the password in 8 to 16 characters!")
    private String password;

    @NotBlank(message = "Please enter your Nickname!")
    private String nickname;


    @Builder
    public MemberFormDto(String MemberID, String Password, String Nickname){
        this.memberID = MemberID;
        this.password = Password;
        this.nickname = Nickname;
    }

}
