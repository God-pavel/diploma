package com.studying.diploma.repository;


import com.studying.diploma.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<Recipe, Long> {
}

