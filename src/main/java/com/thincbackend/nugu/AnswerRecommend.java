package com.thincbackend.nugu;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thincbackend.domain.Recipe;
import com.thincbackend.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RequiredArgsConstructor
@Controller
@RestController
public class AnswerRecommend {
    private final RecipeService recipeService;
    @PostMapping(value ="/ans.recommend")
    public String answerRecommend(@RequestBody Map<String,Object> request){
        Result result = new Result();

        //request에서 재료 가져오기
        Map<String, Object> action = (Map<String,Object>)request.get("action");
        Map<String,Object> parameters = (Map<String,Object>)action.get("parameters");
        Map<String,String> ingredient = (Map<String,String>)parameters.get("ingredient");
        String ingredientName = ingredient.get("value");

        // 재료에 해당되는 음식들 mysql에서 리스트 가져와서 shuffle random해서 하나 가져오기

        List<Recipe> recipeByIntegrate = recipeService.findRecipeByIntegrate(ingredientName);

        List<String> foodList = new ArrayList<>();
        for(Recipe recipe : recipeByIntegrate){
            String foodName = recipe.getTitle();
            foodList.add(foodName);
        }

        // 랜덤으로 하나 선택
        String food = getFood(foodList);

        Map<String,String> output = new HashMap<>();
        output.put("ingredient", ingredientName);
        output.put("food", food);
        result.setOutput(output);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(result);

        return json;
    }

    private String getFood(List<String> foodList){
        Collections.shuffle(foodList);
        return foodList.get(0);
    }
}
