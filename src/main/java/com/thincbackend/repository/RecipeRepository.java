package com.thincbackend.repository;

import com.thincbackend.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>{

    List<Recipe> findByTitleContainingOrIntegrateContainingOrProcessContaining(String title, String integrate, String process);

    List<Recipe> findByProcessContaining(String process);

    List<Recipe> findRecipeByIntegrateContaining(String Integrate);

    List<Recipe> findByCategory(String category);

    List<Recipe> findByOwner(String nickname);

    List<Recipe> findByOwnerNot(String nickname);

    Optional<Recipe> findByTitleContaining(String title);

}
