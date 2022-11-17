package com.thincbackend.domain;

import com.thincbackend.dto.PostFormDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@NoArgsConstructor
@Getter
@Setter
@Table(name = "posts")
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String content;

    private String img_post;

    private String owner;

    @Builder
    public Post(String title, String content, String img_post, String owner){
        this.title = title;
        this.content = content;
        this.img_post = img_post;
        this.owner = owner;
    }

    public static Post createPost(PostFormDto postFormDto){
        Post post = Post.builder()
                .title(postFormDto.getTitle())
                .content(postFormDto.getContent())
                .img_post(postFormDto.getImg_post())
                .owner(postFormDto.getOwner())
                .build();

        return post;
    }
}
