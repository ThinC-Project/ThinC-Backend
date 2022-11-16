package com.thincbackend.controller;

import com.thincbackend.domain.Post;
import com.thincbackend.dto.PostFormDto;
import com.thincbackend.repository.PostRepository;
import com.thincbackend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class PostController {
    private final PostService postService;

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
    @GetMapping("/write_post")
    public String writePostForm(Model model){
        model.addAttribute("postFormDto", new PostFormDto());

        return "write_post";
    }
    @PostMapping("/write_post")
    public String writePostForm(@Valid PostFormDto postFormDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "redirect:/write_post";
        }
        try{
            Post post = Post.createPost(postFormDto);
            Post savedPost = postService.savePost(post);
            System.out.println("Post ID : " + savedPost.getId());
        } catch(IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/board";
        }
        return "redirect:/write_post";
    }
    @GetMapping("/edit")
    public String editPost(@RequestParam(value = "post_id", defaultValue = "0") Long post_id, Model model){
        Optional<Post> post = postService.findPostById(post_id);
        model.addAttribute("post", post);

        return  "post_edit";

    }
    @PostMapping("/edit")
    public String editPost(@RequestParam(value = "Post") Post post, Model model){
        postService.savePost(Post
                .builder()
                .title(post.getTitle())
                .content(post.getContent())
                .img_post(post.getImg_post())
                .owner(post.getOwner())
                .build()
        );

        return "redirect:/post?id="+post.getId();
    }

    @GetMapping("/delete")
    public String deletePost(@RequestParam(value = "Post_id") Long Post_id, Model model) {
        if(postService.findPostById(Post_id).isEmpty()) { // 값 존재여부 확인
            model.addAttribute("message", "Post is not exist.");
        } else {
            postService.deletePostById(Post_id);
            model.addAttribute("message", "Post is deleted successful.");
        }
        return "redirect:/board";
    }
}
