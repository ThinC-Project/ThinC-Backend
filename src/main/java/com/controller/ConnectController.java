package com.controller;

import com.domain.User;
import com.dto.UserFormDto;
import com.service.UserService;
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

    private final UserService userService;

    @GetMapping("/join")
    public String joinForm(Model model){
        model.addAttribute("userFormDto", new UserFormDto());

        return "join";
    }
    @PostMapping("/join")
    public String joinForm(@Valid UserFormDto userFormDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "/join";
        }
        try{
            User user = User.createUser(userFormDto);
            User savedUser = userService.saveUser(user);
//            System.out.println("User ID : " + savedUser.getID());
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
            String UserId = httpServletRequest.getParameter("ID");
            String UserPw = httpServletRequest.getParameter("PW");

            User User = userService.loadUserByUserId(UserId);
            if (User!=null && User.getPassword()==UserPw){
                session.setAttribute("Nickname", User.getNickname());
                session.setAttribute("UserID", User.getUserID());
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
