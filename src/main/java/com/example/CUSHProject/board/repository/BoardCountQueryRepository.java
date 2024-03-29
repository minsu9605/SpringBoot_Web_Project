package com.example.CUSHProject.board.repository;


import com.example.CUSHProject.board.model.BoardCountEntity;
import com.example.CUSHProject.entity.QBoardCountEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardCountQueryRepository {
    private final JPAQueryFactory queryFactory;


    public List<BoardCountEntity> findByStatus(String statusName,String yearOption, String monthOption) {
        return queryFactory.selectFrom(QBoardCountEntity.boardCountEntity)
                .where(QBoardCountEntity.boardCountEntity.statusName.eq(statusName)
                    .and(QBoardCountEntity.boardCountEntity.batchDate.year().eq(Integer.parseInt(yearOption)))
                    .and(QBoardCountEntity.boardCountEntity.batchDate.month().eq(Integer.parseInt(monthOption)))
                )
                .orderBy(QBoardCountEntity.boardCountEntity.batchDate.asc())
                .fetch();

    }

}
