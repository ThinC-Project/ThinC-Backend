package com.thincbackend.service;

import com.thincbackend.domain.Post;
import com.thincbackend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;

    public Post savePost(Post post){
        return postRepository.save(post);
    }

    public List<Post> findAllPost(){
        return postRepository.findAll();
    }

    public Optional<Post> findPostById(Long id){
        return postRepository.findById(id);
    }

    public List<Post> findPostByKeyword(String keyword){
        return postRepository.findPostByKeyword(keyword);
    }

    public List<Post> findOwnerPost(String owner){
        return postRepository.findByOwner(owner);
    }

    public List<Post> findOtherPost(String owner){
        return postRepository.findByOwnerNot(owner);
    }

    public void deletePostById(Long id){
        postRepository.deleteById(id);
    };
//    public Post loadPostByRecipeId(Long id){
//        Optional<Post> post = postRepository.findById(id);
//
//        return Post.builder()
//                .title(post.getTitle())
//                .content(post.getContent())
//                .img_post(post.getImg_post())
//                .owner(post.getOwner())
//                .build();
//    }
}
