package com.thincbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.thincbackend.domain.Post;
import com.thincbackend.dto.PostFormDto;
import com.thincbackend.service.PostService;
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
@RequestMapping("/board")
public class PostController {
    private final PostService postService;

    private ObjectMapper objectMapper = new ObjectMapper();

    //전체 포스트 리스트 불러오기
    @GetMapping({"/", ""})
    public String Post(){
        List<Post> postList = postService.findAllPost();

        String json = new Gson().toJson(postList);

        System.out.println(json);

        return json;
    }

    //포스트 상세 페이지 - 포스트 정보 불러오기
    @GetMapping("/post_detail")
    public String PostDetail(HttpServletRequest request){
        String post_id = request.getParameter("id");

        Optional<Post> post = postService.findPostById(Long.parseLong(post_id));

        String json = new Gson().toJson(post);

        return json;
    }

    //포스트 작성
    @PostMapping("/write-post")
    public void writePost(HttpServletRequest request, @CookieValue(name="nick", required = false) String nick) throws IOException {
        try{
            ServletInputStream inputStream = request.getInputStream();
            System.out.println("write-post");
            String json = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

            postJson postJson = objectMapper.readValue(json, postJson.class);
            System.out.println(postJson.getTitle());
            System.out.println(postJson.getContent());
            System.out.println(postJson.getImage());

            String imgSave = uploadImage(postJson.getImage());

            PostFormDto postFormDto = PostFormDto.builder()
                    .title(postJson.getTitle())
                    .content(postJson.getContent())
                    .img_post(imgSave)
                    .owner(nick)
                    .build();

            Post post = Post.createPost(postFormDto);
            Post savedPost = postService.savePost(post);

            System.out.println(savedPost.getTitle()+" "+savedPost.getContent()+" "+savedPost.getImg_post()+" "+savedPost.getOwner());


        } catch(IllegalStateException e){

        }

    }

    //포스트 수정
    @PostMapping("/edit-post")
    public void editPost(HttpServletRequest request) throws IOException{
        Long postid = Long.parseLong(request.getParameter("id"));

        try{
            ServletInputStream inputStream = request.getInputStream();
            System.out.println("edit-post");
            String json = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

            postJson postJson = objectMapper.readValue(json, postJson.class);
            System.out.println(postJson.getTitle());
            System.out.println(postJson.getContent());
            System.out.println(postJson.getImage());

//            System.out.println(session.getAttribute("Nickname"));
            String imgSave = uploadImage(postJson.getImage());

            Post findPost = postService.findPostById(postid).get();
            findPost.setTitle(postJson.getTitle());
            findPost.setContent(postJson.getContent());
            findPost.setImg_post(imgSave);
            postService.savePost(findPost);

            System.out.println(findPost.getTitle()+" "+findPost.getContent()+" "+findPost.getImg_post()+" "+findPost.getOwner());


        } catch(IllegalStateException e){

        }
    }

    //포스트 삭제
    @GetMapping("/delete")
    public String deletePost(HttpServletRequest request, HttpSession session, Model model) {
        Long postId = Long.parseLong(request.getParameter("id"));

        postService.deletePostById("test", postId);

        return "delete post";

    }

    //이미지 업로드 함수
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
class postJson {
    private String title;
    private String content;
    private String image;
}
