package com.thincbackend.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "bookmarks")
@Entity
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bookmark_id")
    private Long id;

    private String owner;

    private Long recipeId;

    @Builder
    public Bookmark(String owner, Long recipeId){
        this.owner = owner;
        this.recipeId = recipeId;
    }
}
