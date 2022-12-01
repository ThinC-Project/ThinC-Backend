package com.thincbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.thincbackend.domain.Post;
import com.thincbackend.dto.PostFormDto;
import com.thincbackend.repository.PostRepository;
import com.thincbackend.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.Servlet;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.Session;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class PostController {
    private final PostService postService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping({"/", ""})
    public String Post(){
        List<Post> postList = postService.findAllPost();

        String json = new Gson().toJson(postList);

        System.out.println(json);

        return json;
    }

    @GetMapping("/post_search_list")
    public String PostSearchList(HttpServletRequest request){
        String keyword = request.getParameter("keyword");

        List<Post> postList = postService.findPostByKeyword(keyword);

        String json = new Gson().toJson(postList);
        System.out.println(json);

        return json;
    }
    @GetMapping("/post_detail")
    public String PostDetail(HttpServletRequest request){
        String post_id = request.getParameter("id");

        Optional<Post> post = postService.findPostById(Long.parseLong(post_id));

        String json = new Gson().toJson(post);

        return json;
    }

    @PostMapping("/write-post")
    public void writePost(HttpServletRequest request, @CookieValue(name="nick", required = false) String nick) throws IOException {
        try{
            String ldtTime = LocalDateTime.now().toString();
            String fileSave = "C:\\Users\\wol59\\Desktop\\ThinC-Backend\\src\\main\\java\\com\\thincbackend\\uploads"+ldtTime;

            ServletInputStream inputStream = request.getInputStream();
            System.out.println("write-post");
            String json = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

            BoardJson boardJson = objectMapper.readValue(json, BoardJson.class);
            System.out.println(boardJson.getTitle());
            System.out.println(boardJson.getContent());
            System.out.println(boardJson.getImage());

            byte[] imageBytes = DatatypeConverter.parseBase64Binary(boardJson.getImage());
            try{
                BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
                System.out.println(bufImg);

//                bufImg.transferTo(new File(fullPath));
                ImageIO.write(bufImg, "jpg", new File((fileSave)));
            }catch (Exception e){
                e.printStackTrace();
            }

            PostFormDto postFormDto = PostFormDto.builder()
                    .title(boardJson.getTitle())
                    .content(boardJson.getContent())
                    .img_post(ldtTime)
                    .owner(nick)
                    .build();

            Post post = Post.createPost(postFormDto);
            Post savedPost = postService.savePost(post);

            System.out.println(savedPost.getTitle()+" "+savedPost.getContent()+" "+savedPost.getImg_post()+" "+savedPost.getOwner());


        } catch(IllegalStateException e){

        }

    }

    @PostMapping("/edit-post")
    public void editPost(HttpServletRequest request, HttpSession session) throws IOException{
        Long postid = Long.parseLong(request.getParameter("id"));

        try{
            ServletInputStream inputStream = request.getInputStream();
            System.out.println("edit-post");
            String json = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

            BoardJson boardJson = objectMapper.readValue(json, BoardJson.class);
            System.out.println(boardJson.getTitle());
            System.out.println(boardJson.getContent());
            System.out.println(boardJson.getImage());

//            System.out.println(session.getAttribute("Nickname"));

            Post findPost = postService.findPostById(postid).get();
            findPost.setTitle(boardJson.getTitle());
            findPost.setContent(boardJson.getContent());
            postService.savePost(findPost);

            System.out.println(findPost.getTitle()+" "+findPost.getContent()+" "+findPost.getImg_post()+" "+findPost.getOwner());


        } catch(IllegalStateException e){

        }
    }

    @GetMapping("/delete")
    public String deletePost(HttpServletRequest request, HttpSession session, Model model) {
        Long postId = Long.parseLong(request.getParameter("id"));

        postService.deletePostById("test", postId);

        return "delete post";

    }

}

@Getter
@Setter
class BoardJson{
    private String title;
    private String content;
    private String image;
}
