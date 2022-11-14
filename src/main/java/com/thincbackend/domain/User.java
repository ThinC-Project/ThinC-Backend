package com.thincbackend.domain;

import com.thincbackend.dto.UserFormDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String UserID;

    private String Password;

    @Column(unique = true)
    private String Nickname;

    @Builder
    public User(String UserID, String Password, String Nickname){
        this.UserID = UserID;
        this.Password = Password;
        this.Nickname = Nickname;
    }

    public static User createUser(UserFormDto userFormDto){
        User user = User.builder()
                .UserID(userFormDto.getID())
                .Password(userFormDto.getPassword())
                .Nickname(userFormDto.getNickname())
                .build();

        return user;
    }
}
