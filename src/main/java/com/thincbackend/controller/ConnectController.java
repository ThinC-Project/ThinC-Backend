package com.thincbackend.controller;

import com.thincbackend.domain.Member;
import com.thincbackend.dto.MemberFormDto;
import com.thincbackend.repository.MemberRepository;
import com.thincbackend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
@RestController
@RequestMapping("/")
public class ConnectController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/join")
    public String joinForm(HttpServletRequest request){
        String id = request.getParameter("id");
        String pw = request.getParameter("pw");
        String nickname = request.getParameter("nick");

        try{
            MemberFormDto memberFormDto = MemberFormDto.builder()
                    .MemberID(id)
                    .Password(pw)
                    .Nickname(nickname)
                    .build();
            Member member = Member.createMember(memberFormDto);
            Member savedMember = memberService.saveMember(member);
            System.out.println("Member ID : " + savedMember.getMemberID());
            return "join success";
        } catch(IllegalStateException e){
            return "error";
        }
    }

    @GetMapping("/login")
    public String loginForm(HttpServletRequest request, HttpServletResponse response){

        try{
            String MemberPW = request.getParameter("pw");
            String MemberID = request.getParameter("id");
            System.out.println(MemberID+" "+MemberPW);

            Member member = memberService.findByMemberId(MemberID);

            System.out.println("member : "+member.getMemberID()+" member_pw : "+member.getPassword());
            if (member!=null && member.getPassword().equals(MemberPW)){

                System.out.println("login Success!");
                Cookie idCookie = new Cookie("nick", String.valueOf(member.getNickname()));
                response.addCookie(idCookie);
                return member.getNickname();
            }else{
                System.out.println("no member");

                return "no member";
            }
        } catch(IllegalStateException e){
            return "error";
        }
    }
}
