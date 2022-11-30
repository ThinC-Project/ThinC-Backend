package com.thincbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.thincbackend.domain.Bookmark;
import com.thincbackend.domain.History;
import com.thincbackend.domain.Post;
import com.thincbackend.domain.Recipe;
import com.thincbackend.dto.PostFormDto;
import com.thincbackend.dto.RecipeFormDto;
import com.thincbackend.service.BookmarkService;
import com.thincbackend.service.HistoryService;
import com.thincbackend.service.RecipeService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    private final BookmarkService bookmarkService;

    private ObjectMapper objectMapper = new ObjectMapper();


    //전체 레시피 리스트 불러오기
    @GetMapping({"/", ""})
    public String Recipe(){
        List<Recipe> recipeList = recipeService.findAllRecipe();

        String json = new Gson().toJson(recipeList);
        System.out.println(json);

        return json;
    }

    //카테고리별 레시피 리스트 불러오기
    @GetMapping("/recipe_category_list")
    public String RecipeCategoryList(HttpServletRequest request){
        String category = request.getParameter("category");

        List<Recipe> recipeList = recipeService.findRecipeByCategory(category);

        String json = new Gson().toJson(recipeList);
        System.out.println(json);

        return json;
    }

    //키워드에 따른 레시피 리스트 불러오기
    @GetMapping("/recipe_search_list")
    public String RecipeSearchList(HttpServletRequest request){
        String keyword = request.getParameter("keyword");

        List<Recipe> recipeList = recipeService.findRecipeByKeyword(keyword);

        String json = new Gson().toJson(recipeList);
        System.out.println(json);

        return json;
    }

    //유저의 레시피 리스트 불러오기
    @GetMapping("/recipe_owner_list")
    public String RecipeOwnerList(HttpServletRequest request){
        String owner = request.getParameter("owner");

        List<Recipe> recipeList = recipeService.findOwnerRecipe(owner);

        String json = new Gson().toJson(recipeList);
        System.out.println(json);

        return json;
    }

    //리스트 상세 페이지 - 레시피 정보 불러오기
    @GetMapping("/recipe_detail")
    public String RecipeDetail(HttpServletRequest request){
        Long recipe_id = Long.parseLong(request.getParameter("id"));

        Optional<Recipe> recipe = recipeService.findRecipeById(recipe_id);

        String json = new Gson().toJson(recipe);
        System.out.println(json);

        return json;
    }

    //레시피 작성
    @PostMapping("/write-recipe")
    public void writeRecipe(HttpServletRequest request) throws IOException {
        try{

            ServletInputStream inputStream = request.getInputStream();
            System.out.println("write-recipe");
            String json = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

            RecipeJson recipeJson = objectMapper.readValue(json, RecipeJson.class);
            System.out.println(recipeJson.getTitle());
            System.out.println(recipeJson.getIntegrate());
            System.out.println(recipeJson.getImage());
            System.out.println(recipeJson.getCategory());

            String imgSave = uploadImage(recipeJson.getImage());

            RecipeFormDto recipeFormDto = RecipeFormDto.builder()
                    .title(recipeJson.getTitle())
                    .integrate(recipeJson.getIntegrate())
                    .category(recipeJson.getCategory())
                    .process(recipeJson.getProcess())
                    .img_food(imgSave)
                    .owner("test")
                    .build();

            Recipe recipe = Recipe.createRecipe(recipeFormDto);
            Recipe savedRecipe = recipeService.saveRecipe(recipe);

            System.out.println(savedRecipe.getTitle()+" "+savedRecipe.getIntegrate()+" "+savedRecipe.getProcess()+" "+savedRecipe.getCategory()+" "
                    +savedRecipe.getOwner()+" "+savedRecipe.getImg_food());


        } catch(IllegalStateException e){

        }

    }

    //레시피 수정
    @PostMapping("/edit-recipe")
    public void editPost(HttpServletRequest request, HttpSession session) throws IOException{
        Long recipeId = Long.parseLong(request.getParameter("id"));

        try{
            ServletInputStream inputStream = request.getInputStream();
            System.out.println("edit-post");
            String json = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

            RecipeJson recipeJson = objectMapper.readValue(json, RecipeJson.class);
            System.out.println(recipeJson.getTitle());
            System.out.println(recipeJson.getIntegrate());
            System.out.println(recipeJson.getImage());
            System.out.println(recipeJson.getCategory());

//            System.out.println(session.getAttribute("Nickname"));

            String imgSave = uploadImage(recipeJson.getImage());

            Recipe findRecipe = recipeService.findRecipeById(recipeId).get();
            findRecipe.setTitle(recipeJson.getTitle());
            findRecipe.setProcess(recipeJson.getProcess());
            findRecipe.setImg_food(imgSave);
            findRecipe.setIntegrate(recipeJson.getIntegrate());
            recipeService.saveRecipe(findRecipe);

            System.out.println(findRecipe.getTitle()+" "+findRecipe.getIntegrate()+" "+findRecipe.getProcess()+" "+findRecipe.getCategory()+" "
                    +findRecipe.getOwner()+" "+findRecipe.getImg_food());


        } catch(IllegalStateException e){

        }
    }

    //레시피 삭제
    @GetMapping("/delete")
    public String deleteRecipe(HttpServletRequest request) {
        Long recipeId = Long.parseLong(request.getParameter("id"));

        recipeService.deleteRecipeById("test", recipeId);

        return "delete recipe";
    }

    //레시피 북마크 추가
    @GetMapping("/create_bookmark")
    public String createBookmark(HttpServletRequest request){
        Long recipeId = Long.parseLong(request.getParameter("id"));

        Bookmark bookmark = Bookmark.builder()
                .recipeId(recipeId)
                .owner("test")
                .build();
        Bookmark savedBookmark = bookmarkService.saveBookmark(bookmark);

        System.out.print(savedBookmark.toString());

        return "bookmark saved";
    }

    //레시피 북마크 삭제
    @PostMapping("/delete_bookmark")
    public void deleteBookmark(HttpServletRequest request){
        Long recipeId = Long.parseLong(request.getParameter("id"));

        bookmarkService.deleteBookmarkByRecipeIdAndOwner(recipeId, "test");
    }


    //이미지 관련 서버 저장 함수
    public String uploadImage(String img){
        String ldtTime = LocalDateTime.now().toString();
        String fileSave = "C:\\Users\\wol59\\Desktop\\ThinC-Backend\\src\\main\\java\\com\\thincbackend\\uploads"+ldtTime;

        byte[] imageBytes = DatatypeConverter.parseBase64Binary(img);
        try{
            BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
            System.out.println(bufImg);

            ImageIO.write(bufImg, "jpg", new File((fileSave)));
            return fileSave;
        }catch (Exception e){
            e.printStackTrace();
        }

        return fileSave;
    }
}

@Getter
@Setter
class RecipeJson{
    private String title;
    private String integrate;
    private String process;
    private String image;
    private String category;
}
