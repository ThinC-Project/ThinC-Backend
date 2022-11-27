package com.thincbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    public String Post(Model model){
        List<Post> postList = postService.findAllPost();
        model.addAttribute("postList", postList);

        return "post";
    }

    @GetMapping("/post_search_list")
    public String PostSearchList(@RequestParam(value = "keyword", defaultValue = "") String keyword, Model model){
        List<Post> postList = postService.findPostByKeyword(keyword);
        model.addAttribute("postList", postList);

        return "post";
    }
    @GetMapping("/post_detail")
    public String PostDetail(@RequestParam(value = "post_id", defaultValue = "0") Long post_id, Model model){
        Optional<Post> post = postService.findPostById(post_id);
        model.addAttribute("Post", post);

        return "postDetailPage";
    }
//    @GetMapping("/write_post")
//    public String writePostForm(Model model){
//        model.addAttribute("postFormDto", new PostFormDto());
//
//        return "write_post";
//    }
//    @PostMapping("/write_post")
//    public String writePostForm(@Valid PostFormDto postFormDto, BindingResult bindingResult, Model model){
//        if(bindingResult.hasErrors()){
//            return "redirect:/write_post";
//        }
//        try{
//            Post post = Post.createPost(postFormDto);
//            Post savedPost = postService.savePost(post);
//            System.out.println("Post ID : " + savedPost.getId());
//        } catch(IllegalStateException e){
//            model.addAttribute("errorMessage", e.getMessage());
//            return "redirect:/board";
//        }
//        return "redirect:/write_post";
//    }

    @PostMapping("/write-post")
    public void writePost(HttpServletRequest request, HttpSession session) throws IOException {
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

                ImageIO.write(bufImg, "jpg", new File((fileSave)));
            }catch (Exception e){
                e.printStackTrace();
            }

            PostFormDto postFormDto = PostFormDto.builder()
                    .title(boardJson.getTitle())
                    .content(boardJson.getContent())
                    .img_post(ldtTime)
                    .owner("test")
                    .build();

            Post post = Post.createPost(postFormDto);
            Post savedPost = postService.savePost(post);

            System.out.println(savedPost.getTitle()+" "+savedPost.getContent()+" "+savedPost.getImg_post()+" "+savedPost.getOwner());


        } catch(IllegalStateException e){

        }

    }

//    @GetMapping("/edit")
//    public String editPost(@RequestParam(value = "post_id", defaultValue = "0") Long post_id, Model model){
//        Optional<Post> post = postService.findPostById(post_id);
//        model.addAttribute("post", post);
//
//        return  "post_edit";
//
//    }
    @PostMapping("/edit-post")
    public void editPost(HttpServletRequest request, HttpSession session) throws IOException{
        try{
            ServletInputStream inputStream = request.getInputStream();
            System.out.println("write-post");
            String json = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

            BoardJson boardJson = objectMapper.readValue(json, BoardJson.class);
            System.out.println(boardJson.getTitle());
            System.out.println(boardJson.getContent());
            System.out.println(boardJson.getImage());

            System.out.println(session.getAttribute("Nickname"));

            PostFormDto postFormDto = PostFormDto.builder()
                    .title(boardJson.getTitle())
                    .content(boardJson.getContent())
                    .img_post(boardJson.getImage())
                    .owner(session.getAttribute("Nickname").toString())
                    .build();

            Post post = Post.createPost(postFormDto);
            Post savedPost = postService.savePost(post);

            System.out.println(savedPost.getTitle()+" "+savedPost.getContent()+" "+savedPost.getImg_post()+" "+savedPost.getOwner());


        } catch(IllegalStateException e){

        }
    }

    @GetMapping("/delete")
    public String deletePost(@RequestParam(value = "Post_id") Long Post_id, HttpSession session, Model model) {
        if(postService.findPostById(Post_id).isEmpty()) { // 값 존재여부 확인
            model.addAttribute("message", "Post is not exist.");
        } else {
            postService.deletePostById(session.getAttribute("nickname").toString(), Post_id);
            model.addAttribute("message", "Post is deleted successful.");
        }
        return "redirect:/board";
    }
}

@Getter
@Setter
class BoardJson{
    private String title;
    private String content;
    private String image;
}
