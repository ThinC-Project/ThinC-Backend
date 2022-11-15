package com.thincbackend.repository;

import com.thincbackend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select * from Post where id = #{id}", nativeQuery = true)
    Post findPostById(Long id);

    List<Post> findAll();

    @Query(value = "select * from Post where title like %#{keyword}% OR content like %#{keyword}%", nativeQuery = true)
    List<Post> findPostByKeyword(String keyword);

    @Query(value = "select * from Post where owner = #{nickname}", nativeQuery = true)
    List<Post> findOwnerPost(String nickname);

}
