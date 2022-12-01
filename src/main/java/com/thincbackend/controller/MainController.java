package com.thincbackend.controller;

import com.google.gson.Gson;
import com.thincbackend.domain.Member;
import com.thincbackend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
@RestController
public class MainController {
    private final MemberService memberService;

    @GetMapping("/mypage")
    public String myPage(HttpSession session, Model model){
        String Nickname = session.getAttribute("Nickname").toString();
        String MemberID = session.getAttribute("UserID").toString();

        Member member = memberService.findByMemberId(MemberID);

        String json = new Gson().toJson(member);

        return json;
    }
}
