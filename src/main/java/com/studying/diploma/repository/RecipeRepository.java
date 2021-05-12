package com.studying.diploma.repository;


import com.studying.diploma.model.Recipe;
import com.studying.diploma.model.RecipeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Page<Recipe> findAll(Pageable pageable);
    List<Recipe> findAllByTimeIsLessThanAndRecipeCategory(Integer time, RecipeCategory category);
}


