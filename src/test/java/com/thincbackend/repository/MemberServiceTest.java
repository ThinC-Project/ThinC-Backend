package com.thincbackend.repository;

import com.thincbackend.domain.Member;

import com.thincbackend.dto.MemberFormDto;
import com.thincbackend.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
class MemberServiceTest {


    @Autowired
    private MemberService memberService;

    public Member createMember(){
//        System.out.println("test");
        MemberFormDto memberFormDto = MemberFormDto.builder()
                .MemberID("test")
                .Password("1234")
                .Nickname("nickname")
                .build();

        return Member.createMember(memberFormDto);
    }

    @Test
    @DisplayName("유저 등록 테스트")
    public void saveMemberTest(){
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);

        assertEquals(member.getNickname(), savedMember.getNickname());
    }


}