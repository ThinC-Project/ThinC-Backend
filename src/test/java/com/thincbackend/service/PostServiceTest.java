package com.thincbackend.service;

import com.google.gson.Gson;
import com.thincbackend.domain.Post;

import com.thincbackend.dto.PostFormDto;

import com.thincbackend.repository.PostRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {"spring.config.location=classpath:application.properties"})
class PostServiceTest {

    @Autowired
    private PostService postService;

    public Post createPost(String title, String owner){
        PostFormDto postFormDto = PostFormDto.builder()
                .title(title)
                .content("post_content")
                .img_post("/drive/test/img.png")
                .owner(owner)
                .build();
        return Post.createPost(postFormDto);
    }

    @Test
    @DisplayName("포스트_등록_테스트")
    public void savePostTest(){
        Post post = createPost("post1", "owner1");

        Post savedPost = postService.savePost(post);

        assertThat(savedPost.getTitle()).isEqualTo("post1");
    }

    @Test
    @DisplayName("포스트_전체_테스트")
    public void findAllPostTest(){
        Post post1 = createPost("post1", "owner1");
        Post post2 = createPost("post2", "owner1");
        Post post3 = createPost("post3", "owner1");

        postService.savePost(post1);
        postService.savePost(post2);
        postService.savePost(post3);

        List<Post> postList = postService.findAllPost();

        String json = new Gson().toJson(postList);
        System.out.println("postList : "+json);
    }

    @Test
    @DisplayName("멤버_포스트_테스트")
    public void memberFindTest(){
        Post post1 = createPost("post1", "owner1");
        Post post2 = createPost("post2", "owner2");
        Post post3 = createPost("post3", "owner1");

        postService.savePost(post1);
        postService.savePost(post2);
        postService.savePost(post3);

        List<Post> postList = postService.findAllPost();
        List<Post> ownerPostList = postService.findOwnerPost("owner1");
        List<Post> otherPostList = postService.findOtherPost("owner1");

        System.out.println(ownerPostList);
        System.out.println(otherPostList);
    }

    @Test
    @DisplayName("포스트_찾기_테스트")
    public void postDetailTest(){
        Post post1 = createPost("post1", "owner1");
        Post post2 = createPost("post2", "owner2");

        postService.savePost(post1);
        postService.savePost(post2);

        Optional<Post> postOptional1 = postService.findPostById(post1.getId());
        Optional<Post> postOptional2 = postService.findPostById(post2.getId());
        List<Post> ownerPostList = postService.findOtherPost("owner1");

        assertThat(postOptional1.get().getTitle()).isEqualTo(post1.getTitle());
        assertThat(postOptional2.get().getTitle()).isEqualTo(post2.getTitle());

    }

    @Test
    @DisplayName("포스트_삭제_테스트")
    public void postDeleteTest(){
        Post post1 = createPost("post1", "owner1");

        postService.savePost(post1);

        assertThat(postService.findPostById(post1.getId()).get().getTitle()).isEqualTo(post1.getTitle());
        //현재 유저를 owner2로 설정하여 owner1이 작성한 포스트 삭제 테스트
        IllegalStateException e1 = assertThrows(IllegalStateException.class, () -> postService.deletePostById("owner2", post1.getId()));
        assertThat(e1.getMessage()).isEqualTo("권한이 없습니다.");

        postService.deletePostById("owner1", post1.getId());

        //게시글 존재 여부 테스트
        IllegalStateException e2 = assertThrows(IllegalStateException.class, () -> postService.findPostById(post1.getId()));
        assertThat(e2.getMessage()).isEqualTo("게시글이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("게시글_수정_테스트")
    public void postUpdateTest(){
        Post post1 = createPost("post1", "owner1");
        postService.savePost(post1);
        post1.setContent("change Content");
        Optional<Post> findPost = postService.findPostById(post1.getId());
        assertThat(findPost.get().getTitle()).isEqualTo("post1");
        assertThat(findPost.get().getContent()).isEqualTo("change Content");
    }

    @Test
    @DisplayName("게시글_키워드_조회_테스트")
    public void postFindByKeywordTest(){
        Post post1 = createPost("test post test", "owner1");
        Post post2 = createPost("test keyword test", "owner1");
        Post post3 = Post.createPost(PostFormDto.builder()
                .title("test test test")
                .content("test test test keyword")
                .img_post("/drive/test/img.png")
                .owner("owner1")
                .build());
        Post post4 = Post.createPost(PostFormDto.builder()
                .title("keyword")
                .content("test test test keyword")
                .img_post("/drive/test/img.png")
                .owner("owner1")
                .build());

        postService.savePost(post1);
        postService.savePost(post2);
        postService.savePost(post3);
        postService.savePost(post4);

        List<Post> postListAll = postService.findAllPost();
        List<Post> postListKeyword = postService.findPostByKeyword("keyword");

        System.out.println(postListAll);
        System.out.println(postListKeyword);
    }
}