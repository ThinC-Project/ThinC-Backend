package com.thincbackend.nugu;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thincbackend.domain.History;
import com.thincbackend.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class AnswerHistory {
    private final HistoryService historyService;
    @PostMapping(value="/ans.history")
    public String answerHistory(@RequestBody Map<String,Object> request){

        Result result = new Result();

        // request에서 날짜 가져오기
        Map<String, Object> action = (Map<String,Object>)request.get("action");
        Map<String,Object> parameters = (Map<String,Object>)action.get("parameters");
        Map<String,String> day = (Map<String,String>)parameters.get("day");
        String dayValue = day.get("value"); // 어제, 그제, 오늘, 저번주, 지난주.

        // 날짜 차이 계산
        int calValue = 0;
        for(HistoryAndDay historyAndDay : HistoryAndDay.values()){
            if(historyAndDay.isEqualDay(dayValue)){
                calValue = historyAndDay.getCalValue();
            }
        }

        // ex. "1120"
        String answerDay = getDay(calValue);

        // 날짜에 해당되는 음식을 db애서 가져오는 코드
        List<History> historyByDate = historyService.findHistoryByDate(answerDay);
        List<String> historyList = new ArrayList<>();
        for(History history : historyByDate){
            String recipeTitle = history.getRecipeTitle();
            historyList.add(recipeTitle);
        }

        // 조회된 음식 리스트
        String foodString = listToString(historyList);

        Map<String,String> output = new HashMap<>();
        output.put("day",dayValue);
        output.put("historyFood", foodString);
        result.setOutput(output);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(result);

        return json;
    }

    public String getDay(int value){
        LocalDate localDate = LocalDate.now();
        int monthValue = localDate.getMonthValue();
        int dayOfMonth = localDate.getDayOfMonth();
        dayOfMonth -= value;
        String month = String.valueOf(monthValue);
        String day = String.valueOf(dayOfMonth);
        return (month + day);
    }

    public String listToString(List<String> list){
        String answer = "";
        for(String s : list){
            answer += s;
            answer += ", ";
        }
        return answer;
    }
}
