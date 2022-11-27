package com.thincbackend.nugu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
public class test {
    //@ResponseBody
    @RequestMapping(value = "/ans.cook_food", produces = "application/json; charset=utf8", method = {RequestMethod.GET, RequestMethod.POST})
    public String food(@RequestBody Map<String,Object> request){

        System.out.println(request);
        Result foodAndTime = new Result();

        // request 에서 음식 가져오기
        Map<String, Object> action = (Map<String,Object>)request.get("action");
        Map<String,Object> parameters = (Map<String,Object>)action.get("parameters");
        Map<String,String> food = (Map<String,String>)parameters.get("food");
        String foodValue = food.get("value");

        Map<String,String> output = new HashMap<>();
        String time = "30초";
        output.put("food", foodValue);
        output.put("time", time);
        foodAndTime.setOutput(output);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(foodAndTime);

        return json;
    }
}
