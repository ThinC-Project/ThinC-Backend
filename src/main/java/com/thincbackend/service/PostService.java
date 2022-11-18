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
        Optional<Post> findPost = checkPostExist(id);
        return findPost;
    }

    public List<Post> findPostByKeyword(String keyword){
        return postRepository.findByTitleContainingOrContentContaining(keyword, keyword);
    }

    public List<Post> findOwnerPost(String owner){
        return postRepository.findByOwner(owner);
    }

    public List<Post> findOtherPost(String owner){
        return postRepository.findByOwnerNot(owner);
    }

//    public Optional<Post> updatePost(Long id, Post post){
//
//    }

    public void deletePostById(String owner, Long id){
        checkPostOwner(owner, id);
        checkPostExist(id);
        postRepository.deleteById(id);
    };

    public Optional<Post> checkPostExist(Long id){
        Optional<Post> findPost = postRepository.findById(id);
        if(findPost.isEmpty()){
            throw new IllegalStateException("게시글이 존재하지 않습니다.");
        }
        return findPost;
    }

    public void checkPostOwner(String owner, Long id){
        Optional<Post> findPost = postRepository.findById(id);
        if(findPost.get().getOwner()!=owner){
            throw new IllegalStateException("권한이 없습니다.");
        }
    }
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
