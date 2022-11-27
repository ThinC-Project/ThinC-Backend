package com.thincbackend.controller;


import com.thincbackend.domain.History;
import com.thincbackend.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
@RestController
@RequestMapping("/history")
public class HistoryController{

    private final HistoryService historyService;

    @GetMapping("/")
    public String historyForm(HttpServletRequest request){
        String title = request.getParameter("title");
        String owner = request.getParameter("owner");
        String date = request.getParameter("date");


        History history = History.builder()
                .recipeTitle(title)
                .owner(owner)
                .date(date)
                .build();
        History savedHistory = historyService.saveHistory(history);

        return "Test";
    }
}
