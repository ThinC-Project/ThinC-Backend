package com.thincbackend.repository;

import com.thincbackend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitleContainingOrContentContaining(String title, String content);

    List<Post> findByOwner(String nickname);

    List<Post> findByOwnerNot(String nickname);

}
