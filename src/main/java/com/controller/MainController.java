package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping({"/", "/home", "/main"})
public class MainController {

    @GetMapping("/")
    public String main(){
        return "main";
    }

    @GetMapping("/recipe")
    public String recipePage(){
        return "recipe";
    }

    @GetMapping("/community")
    public String communityPage(){
        return "community";
    }

    @GetMapping("/history")
    public String historyPage(){
        return "history";
    }

    @GetMapping("/shopping")
    public String shoppingPage(){
        return "shopping";
    }

    @GetMapping("/mypage")
    public String myPage(HttpSession session, Model model){
        String Nickname = session.getAttribute("Nickname").toString();
        String UserID = session.getAttribute("UserID").toString();
        model.addAttribute("Nickname", Nickname);
        model.addAttribute("UserID", UserID);
        return "mypage";
    }

}
