package com.service;

import com.domain.Post;
import com.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
    private PostRepository postRepository;

    public Post savePost(Post post){
        return postRepository.save(post);
    }

    public List<Post> findAllPost(){
        return postRepository.findAllPost();
    }

    public Post findPostById(Long id){
        return postRepository.findPostById(id);
    }

    public List<Post> findPostByKeyword(String keyword){
        return postRepository.findPostByKeyword(keyword);
    }

    public List<Post> findOwnerPost(String owner){
        return postRepository.findOwnerPost(owner);
    }

    public Post loadPostByRecipeId(Long id){
        Post post = postRepository.findPostById(id);

        return Post.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .img_post(post.getImg_post())
                .owner(post.getOwner())
                .build();
    }
}
