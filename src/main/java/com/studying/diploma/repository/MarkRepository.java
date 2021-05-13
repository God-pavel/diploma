package com.studying.diploma.repository;


import com.studying.diploma.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
}


