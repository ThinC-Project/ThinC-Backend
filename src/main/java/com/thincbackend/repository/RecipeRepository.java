package com.thincbackend.repository;

import com.thincbackend.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>{

    List<Recipe> findByTitleLikeOrIntegrateLikeOrProcessLike(String keyword);

    List<Recipe> findByCategory(String category);

    List<Recipe> findByOwner(String nickname);

    List<Recipe> findByOwnerNot(String nickname);

}
