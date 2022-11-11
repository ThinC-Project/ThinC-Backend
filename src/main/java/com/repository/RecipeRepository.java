package com.repository;

import com.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>{

    @Override
    @Query(value = "select * from Recipe", nativeQuery = true)
    List<Recipe> findAll();

    @Query(value = "select * from Recipe where title like %#{keyword}% and intigrate like %#{keyword}% OR process like %#{keyword}%", nativeQuery = true)
    List<Recipe> findByKeyword(String keyword);

    @Query(value = "select * from Recipe where category = #{category}", nativeQuery = true)
    List<Recipe> findByCategory(String category);

    @Query(value = "select * from Recipe where owner = #{nickname}", nativeQuery = true)
    List<Recipe> findOwnerRecipe(String nickname);

    @Query(value = "select * from Recipe where owner != #{nickname}", nativeQuery = true)
    List<Recipe> findOtherRecipe(String nickname);

}
