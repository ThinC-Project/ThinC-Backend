package com.thincbackend.repository;

import com.thincbackend.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>{

    @Query(value = "select * from Recipe where title like %:keyword% OR intigrate like %:keyword% OR process like %:keyword%", nativeQuery = true)
    List<Recipe> findByKeyword(String keyword);

    List<Recipe> findByCategory(String category);

    List<Recipe> findByOwner(String nickname);

    @Query(value = "select * from Recipe where owner != :nickname", nativeQuery = true)
    List<Recipe> findOtherRecipe(String nickname);

}
