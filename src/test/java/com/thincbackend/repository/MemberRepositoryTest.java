package com.thincbackend.repository;

import com.thincbackend.domain.Member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
class MemberRepositoryTest {


    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("유저 등록 테스트")
    public void createMember(){
//        System.out.println("test");
        Member member = Member.builder()
                .MemberID("test")
                .Password("1234")
                .Nickname("nickname")
                .build();

        Member savedMember = memberRepository.save(member);
        assertEquals(savedMember.getNickname(), "nickname");
    }

}