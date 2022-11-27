package com.thincbackend.nugu;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
public class AnswerHistory {
    @PostMapping(value="/ans.history")
    public String answerHistory(@RequestBody Map<String,Object> request){

        Result result = new Result();

        // request에서 날짜 가져오기
        Map<String, Object> action = (Map<String,Object>)request.get("action");
        Map<String,Object> parameters = (Map<String,Object>)action.get("parameters");
        Map<String,String> day = (Map<String,String>)parameters.get("day");
        String dayValue = day.get("value"); // 어제, 그제, 오늘, 저번주, 지난주.

        LocalDate localDate = LocalDate.now();
        int monthValue = localDate.getMonthValue();
        int dayOfMonth = localDate.getDayOfMonth();

        // 날짜에 해당되는 음식을 mysql에서 가져오는 코드
        // String food = ....

        Map<String,String> output = new HashMap<>();
        String food = null;
        output.put("day",dayValue);
        output.put("food", food);
        result.setOutput(output);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(result);

        return json;
    }
}
