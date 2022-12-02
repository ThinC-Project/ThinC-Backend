package com.thincbackend.nugu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thincbackend.domain.Recipe;
import com.thincbackend.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AnswerRecipe {
    private final RecipeService recipeService;
    private String globalProcess = null;
    private static Integer num = 0;
    @PostMapping(value = "/ans.recipe")
    public String answer(@RequestBody Map<String,Object> request){
        Result result = new Result();
        num++;

        // request에서 food 가져오기
        Map<String, Object> action = (Map<String,Object>)request.get("action");
        Map<String,Object> parameters = (Map<String,Object>)action.get("parameters");
        Map<String,String> food = (Map<String,String>)parameters.get("FoodName");
        String foodName = food.get("value");

        // db에서 가져오기
        Optional<Recipe> recipeOfFood = recipeService.findByTitleContaining(foodName);
        globalProcess = recipeOfFood.get().getProcess();

        Integer nextNum = num + 1;

        String resultString = globalProcess.substring(globalProcess.lastIndexOf(num.toString() + ".") + 2, globalProcess.lastIndexOf(nextNum.toString() + ".") - 1);
        globalProcess = globalProcess.substring(globalProcess.lastIndexOf(nextNum.toString()) -1);


        Map<String,String> output = new HashMap<>();
        output.put("FoodName", foodName);
        output.put("FirstRecipeString", resultString + "다음 순서를 듣고 싶으시다면 다음을 말해주세요");
        result.setOutput(output);

        return makeJson(result);
    }


    @PostMapping(value = "/ans.nextRecipe")
    public String answer2(@RequestBody Map<String,Object> request){
        Result result = new Result();
        num++;

        Map<String,String> output = new HashMap<>();
        output.put("next", "다음");

        if(globalProcess == null){
            output.put("nextRecipeString", "조리과정이 완료되었습니다");
        }
        else{
            Integer nextNum = num + 1;
            String resultString = globalProcess.substring(globalProcess.lastIndexOf(num.toString() + ".") + 2, globalProcess.lastIndexOf(nextNum.toString() + ".") - 1);
            globalProcess = globalProcess.substring(globalProcess.lastIndexOf(nextNum.toString()) -1);

            output.put("nextRecipeString", resultString + "다음 순서를 듣고 싶으시다면 다음을 말해주세요");
        }
        result.setOutput(output);
        return makeJson(result);
    }


    public String makeJson(Result result){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(result);
        return json;
    }
}
