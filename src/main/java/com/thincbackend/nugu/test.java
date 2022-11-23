package com.thincbackend.nugu;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class test {

    @ResponseBody
    @RequestMapping("/ans.cook_food")
    public String food(HttpServletRequest request, Model model){
        String time = "30";
        String food = request.getParameter("food");

        model.addAttribute("food", food);
        model.addAttribute("time", time);

        System.out.println(food);

        return "/ans.cook_food";
    }
}
