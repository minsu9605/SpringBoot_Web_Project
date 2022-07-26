package com.example.CUSHProject.board.repository;

import com.example.CUSHProject.board.model.NoticeBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoardEntity, Long> {


}
