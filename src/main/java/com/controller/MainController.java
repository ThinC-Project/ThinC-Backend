package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
