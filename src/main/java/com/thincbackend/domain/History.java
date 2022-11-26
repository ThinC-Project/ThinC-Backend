package com.thincbackend.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Table(name = "historys")
@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "history_id")
    private Long id;

    @Column(nullable = false)
    private String recipeTitle;

    @Column(nullable = false)
    private String owner;

    @Column(nullable = false)
    private String date;

    @Builder
    public History(String recipeTitle, String owner, String date){
        this.recipeTitle = recipeTitle;
        this.owner = owner;
        this.date = date;
    }

}
