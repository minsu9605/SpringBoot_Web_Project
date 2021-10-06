package com.example.CUSHProject.dto;

import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.entity.NoticeBoardEntity;
import com.example.CUSHProject.enums.Rating;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class NoticeBoardDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private String createdDate;
    private String updatedDate;
    private int hit;


    public NoticeBoardEntity toEntity() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return NoticeBoardEntity.builder()
                .id(id)
                .title(title)
                .content(content)
                .createdDate(LocalDateTime.parse(createdDate, formatter))
                .updatedDate(LocalDateTime.parse(updatedDate, formatter))
                .hit(hit)
                .build();
    }

    @Builder
    public NoticeBoardDto(Long id, String writer, String title, String content, String createdDate, String updatedDate, int hit) {
        this.id=id;
        this.writer=writer;
        this.title=title;
        this.content=content;
        this.createdDate=createdDate;
        this.updatedDate=updatedDate;
        this.hit=hit;
    }
}
