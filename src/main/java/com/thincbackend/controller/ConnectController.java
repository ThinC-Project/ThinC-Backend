package com.thincbackend.controller;

import com.thincbackend.domain.Member;
import com.thincbackend.dto.MemberFormDto;
import com.thincbackend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class ConnectController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String joinForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());

        return "join";
    }
    @PostMapping("/join")
    public String joinForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "/join";
        }
        try{
            Member member = Member.createMember(memberFormDto);
            Member savedMember = memberService.saveMember(member);
            System.out.println("Member ID : " + savedMember.getMemberID());
        } catch(IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "/join";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(){
        return "/login";
    }
    @PostMapping("/login")
    public String loginForm(HttpSession session, HttpServletRequest httpServletRequest, Model model){
        try{
            String MemberID = httpServletRequest.getParameter("ID");
            String MemberPW = httpServletRequest.getParameter("PW");

            Member member = memberService.loadMemberByMemberId(MemberID);
            if (member!=null && member.getPassword()==MemberPW){
//                session.setAttribute("Nickname", member.getNickname());
//                session.setAttribute("MemberID", member.getMemberID());
                memberService.createMemberSession(member, session);
                System.out.println("login Success!");
                return "redirect:/";
            }
        } catch(IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "/login";
        }

        return "/login";
    }


}
