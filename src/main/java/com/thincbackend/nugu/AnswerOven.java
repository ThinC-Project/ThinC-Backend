package com.thincbackend.nugu;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thincbackend.domain.Recipe;
import com.thincbackend.service.RecipeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class AnswerOven {
    private RecipeService recipeService;
    @PostMapping(value="/ans.oven")
    public String answer(@RequestBody Map<String,Object> request){
        Result result = new Result();

        // request에서 food 가져오기
        Map<String, Object> action = (Map<String,Object>)request.get("action");
        Map<String,Object> parameters = (Map<String,Object>)action.get("parameters");
        Map<String,String> food = (Map<String,String>)parameters.get("foodName1");
        String foodName = food.get("value");

        // db에서 가져오기
        Optional<Recipe> recipeOfFood = recipeService.findByTitleContaining(foodName);
        String process = recipeOfFood.get().getProcess();
        String cookType = getCookType(process);
        String times = getTimes(process);
        String temperature = getTemperature(process);

        Map<String,String> output = new HashMap<>();
        output.put("foodName1", foodName);
        output.put("cookType1", cookType);
        output.put("time1", times);
        output.put("temperature", temperature);
        result.setOutput(output);

        return makeJson(result);
    }

    public String getTimes(String process) {
        Pattern pattern = Pattern.compile("\\d+(?:초|분)");
        Matcher matcher = pattern.matcher(process);

        if(matcher.find()){
            String times = matcher.group(0);
            return times;
        }
        return null;
    }

    public String getCookType(String process){
        for(CookType cookType : CookType.values()){
            if(process.contains(cookType.getType())){
                return cookType.getType();
            }
        }
        return null;
    }

    public String getTemperature(String process) {
        Pattern pattern = Pattern.compile("\\d+(?:도)");
        Matcher matcher = pattern.matcher(process);

        if(matcher.find()){
            String temperature = matcher.group(0);
            return temperature;
        }
        return null;
    }


    public String makeJson(Result result){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(result);
        return json;
    }
}
