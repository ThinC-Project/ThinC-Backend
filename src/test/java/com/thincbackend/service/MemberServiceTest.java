package com.thincbackend.service;

import com.thincbackend.domain.Member;

import com.thincbackend.dto.MemberFormDto;
import com.thincbackend.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
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
    @DisplayName("유저_등록_테스트")
    public void saveMemberTest(){
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);

        assertThat(savedMember.getNickname()).isEqualTo("nickname");
    }


    @Test
    @DisplayName("유저_아이디_중복_확인_테스트")
    public void validDuplicationTest(){
        Member member1 = createMember();
        memberService.saveMember(member1);

        Member member2 = createMember();

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.saveMember(member2));
        assertThat(e.getMessage()).isEqualTo("이미 가입된 아이디입니다.");

    }

}