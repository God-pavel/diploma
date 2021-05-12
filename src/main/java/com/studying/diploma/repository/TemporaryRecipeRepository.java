package com.studying.diploma.repository;


import com.studying.diploma.model.TemporaryRecipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TemporaryRecipeRepository extends JpaRepository<TemporaryRecipe, Long> {
    List<TemporaryRecipe> findAll();
}


