package com.controller;

import com.domain.Post;
import com.domain.Recipe;
import com.domain.User;
import com.dto.PostFormDto;
import com.dto.UserFormDto;
import com.repository.PostRepository;
import com.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class PostController {
    private static PostRepository postRepository;
    private static PostService postService;

    @GetMapping({"/", ""})
    public String Post(Model model){
        List<Post> postList = postRepository.findAllPost();
        model.addAttribute("postList", postList);

        return "post";
    }

    @GetMapping("/post_search_list")
    public String PostSearchList(@RequestParam(value = "keyword", defaultValue = "") String keyword, Model model){
        List<Post> postList = postRepository.findPostByKeyword(keyword);
        model.addAttribute("postList", postList);

        return "post";
    }
    @GetMapping("/post_detail")
    public String PostDetail(@RequestParam(value = "post_id", defaultValue = "0") Long post_id, Model model){
        Post post = postRepository.findPostById(post_id);
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
//    @GetMapping("/edit")
//    @PostMapping("/edit")
//    @DeleteMapping("/delete")
}
