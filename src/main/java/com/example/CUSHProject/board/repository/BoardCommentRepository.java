package com.example.CUSHProject.board.repository;

import com.example.CUSHProject.board.model.BoardCommentEntity;
import com.example.CUSHProject.board.model.BoardEntity;
import com.example.CUSHProject.member.model.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardCommentRepository extends JpaRepository<BoardCommentEntity, Long> {
    List<BoardCommentEntity> findByBoardId(BoardEntity boardEntity);
    List<BoardCommentEntity> findByWriter(MemberEntity memberEntity);

    boolean existsByBoardId(BoardEntity boardEntity);
    boolean existsByWriter(MemberEntity memberEntity);

}
