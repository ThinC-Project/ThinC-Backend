package com.thincbackend.nugu;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AnswerRecommend {
    @PostMapping(value ="/ans.recommend")
    public String answerRecommend(@RequestBody Map<String,Object> request){
        Result result = new Result();

        //request에서 재료 가져오기
        Map<String, Object> action = (Map<String,Object>)request.get("action");
        Map<String,Object> parameters = (Map<String,Object>)action.get("parameters");
        Map<String,String> ingredient = (Map<String,String>)parameters.get("ingredient");
        String ingredientName = ingredient.get("value");

        // 재료에 해당되는 음식들 mysql에서 리스트 가져와서 shuffle random해서 하나 가져오기
        // String food = ...

        Map<String,String> output = new HashMap<>();
        String food = null;
        output.put("ingredient", ingredientName);
        output.put("food", food);
        result.setOutput(output);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(request);

        return json;
    }
}
