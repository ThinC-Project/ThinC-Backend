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

    private Long recipe_id;

    @Builder
    public Bookmark(String owner, Long recipe_id){
        this.owner = owner;
        this.recipe_id = recipe_id;
    }
}
