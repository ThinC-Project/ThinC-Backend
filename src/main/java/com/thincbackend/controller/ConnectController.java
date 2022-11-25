package com.thincbackend.controller;

import com.thincbackend.domain.Member;
import com.thincbackend.dto.MemberFormDto;
import com.thincbackend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RestController
@RequestMapping("/")
public class ConnectController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String joinForm(@Valid MemberFormDto memberFormDto, HttpServletRequest request){
        try{
            Member member = Member.createMember(memberFormDto);
            Member savedMember = memberService.saveMember(member);
            System.out.println("Member ID : " + savedMember.getMemberID());
            return "join success";
        } catch(IllegalStateException e){
            return "error";
        }
    }
//    @PostMapping("/join")
//    public String joinForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
//        if(bindingResult.hasErrors()){
//            return "/join";
//        }
//        try{
//            Member member = Member.createMember(memberFormDto);
//            Member savedMember = memberService.saveMember(member);
//            System.out.println("Member ID : " + savedMember.getMemberID());
//        } catch(IllegalStateException e){
//            model.addAttribute("errorMessage", e.getMessage());
//            return "/join";
//        }
//        return "redirect:/login";
//    }

    @GetMapping("/login")
    public String loginForm(HttpSession session, HttpServletRequest request){
        try{
            String MemberPW = request.getParameter("pw");
            String MemberID = request.getParameter("id");
            System.out.println(MemberID+" "+MemberPW);

            Member member = memberService.findByMemberId(MemberID);
            if (member!=null && member.getPassword()==MemberPW){
//                session.setAttribute("Nickname", member.getNickname());
//                session.setAttribute("MemberID", member.getMemberID());
                memberService.createMemberSession(member, session);
                System.out.println("login Success!");
                return "login success";
            }else{
                System.out.println("no member");

                return "no member";
            }
        } catch(IllegalStateException e){
            return "error";
        }
    }
}
