package com.example.CUSHProject.board.repository;

import com.example.CUSHProject.board.model.BoardCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardCategoryRepository extends JpaRepository<BoardCategoryEntity, Long> {
    Optional<BoardCategoryEntity> findById(Long id);
    Optional<BoardCategoryEntity> findByName(String name);
    List<BoardCategoryEntity> findAll();
}
