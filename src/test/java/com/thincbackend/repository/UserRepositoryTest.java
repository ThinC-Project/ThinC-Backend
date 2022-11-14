package com.thincbackend.repository;

import com.thincbackend.domain.User;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
@Transactional
class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저 등록 테스트")
    public void createUser(){
//        System.out.println("test");
        User user = User.builder()
                .UserID("test")
                .Password("1234")
                .Nickname("nickname")
                .build();

        User savedUser = userRepository.save(user);
        assertEquals(savedUser.getNickname(), "nickname");
    }

}