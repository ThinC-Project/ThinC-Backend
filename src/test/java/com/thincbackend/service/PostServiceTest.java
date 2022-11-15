package com.thincbackend.service;

import com.thincbackend.domain.Post;

import com.thincbackend.dto.PostFormDto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
class PostServiceTest {

    @Autowired
    private PostService postService;

    public Post createPost(String title){
        PostFormDto postFormDto = PostFormDto.builder()
                .title(title)
                .content("post_content")
                .img_post("/drive/test/img.png")
                .owner("owner")
                .build();
        return Post.createPost(postFormDto);
    }

    @Test
    @DisplayName("포스트_등록_테스트")
    public void savePostTest(){
        Post post = createPost("post1");

        Post savedPost = postService.savePost(post);

        assertThat(savedPost.getTitle()).isEqualTo("post1");
    }

//    System.out.println(post1.getTitle()+" "+post1.getImg_post()+" "+post1.getOwner()+" "+post1.getContent());

    @Test
    @DisplayName("포스트_전체_테스트")
    public void findAllPostTest(){
        Post post1 = createPost("post1");
        Post post2 = createPost("post2");
        Post post3 = createPost("post3");

        Post savedPost1 = postService.savePost(post1);
        Post savedPost2 = postService.savePost(post2);
        Post savedPost3 = postService.savePost(post3);

        List<Post> postList = postService.findAllPost();
        System.out.println(postList);
    }


}