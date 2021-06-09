package com.studying.diploma.repository;


import com.studying.diploma.model.Mark;
import com.studying.diploma.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;


@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    Stream<Mark> findAllByRecipe(Recipe recipe);
}


