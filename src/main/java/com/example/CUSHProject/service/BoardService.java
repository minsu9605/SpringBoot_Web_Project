package com.example.CUSHProject.service;

import com.example.CUSHProject.dto.BoardDto;
import com.example.CUSHProject.entity.BoardCategoryEntity;
import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.entity.MemberEntity;
import com.example.CUSHProject.enums.Status;
import com.example.CUSHProject.repository.*;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardQueryRepository boardQueryRepository;
    private final BoardCategoryRepository boardCategoryRepository;
    private final BoardCommentRepository boardCommentRepository;


    /*한페이지 출력 리스트*/
    public List<BoardDto> getBoardList(Long categoryId, int page, int perPage, String searchType, String keyword){
        BoardCategoryEntity boardCategoryEntity = boardCategoryRepository.findById(categoryId).orElse(null);
        List<BoardEntity> boardEntityList = boardQueryRepository.getBoardList(boardCategoryEntity,page,perPage,searchType,keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();

        for(BoardEntity boardEntity : boardEntityList){
            boardDtoList.add(boardEntity.toDto());
        }
        return boardDtoList;
    }

    /*조회된 전체 데이터 수*/
    public int getTotalSize(Long categoryId, String searchType, String keyword){
        BoardCategoryEntity boardCategoryEntity = boardCategoryRepository.findById(categoryId).orElse(null);
        return Math.toIntExact(boardQueryRepository.getTotalCount(boardCategoryEntity,searchType,keyword));
    }

    /*한페이지 출력 리스트*/
    public List<BoardDto> getMyBoardList(String username, int page, int perPage, String searchType, String keyword){
        Optional<MemberEntity> memberEntity = memberRepository.findByUsername(username);
        List<BoardEntity> boardEntityList = boardQueryRepository.getMyBoardList(memberEntity.get(),page,perPage,searchType,keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();

        for(BoardEntity boardEntity : boardEntityList){
            boardDtoList.add(boardEntity.toDto());
        }
        return boardDtoList;
    }

    /*조회된 전체 데이터 수*/
    public int getMyBoardTotalSize(String username, String searchType, String keyword){
        Optional<MemberEntity> memberEntity = memberRepository.findByUsername(username);
        return Math.toIntExact(boardQueryRepository.getMyBoardTotalCount(memberEntity.get(),searchType,keyword));
    }

    //보드 글 상세보기
    public BoardDto boardContent(Long id) {
        BoardEntity boardEntity;
        if (id == null) {
            boardEntity = new BoardEntity();
        } else {
            boardEntity = boardRepository.findById(id).orElse(null);
        }
        return boardEntity.toDto();
    }

    /*조회수 증가*/
    public void boardHitUpdate(Long id){
        boardQueryRepository.boardHitUpdate(id);
    }

    public void setSoldOut(Long id){
        boardQueryRepository.boardStatusUpdate(id);
    }

    /*게시글 등록 후 전송*/
    public BoardDto boardWrite(BoardDto boardDto, String username, HttpServletRequest request){
        Optional<BoardCategoryEntity> boardCategoryEntity = boardCategoryRepository.findByName(boardDto.getCategoryName());
        Optional<MemberEntity> memberEntity = memberRepository.findByUsername(username);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        boardDto.setStatus(Status.sell);
        boardDto.setCreatedDate(LocalDateTime.now().format(formatter));
        boardDto.setUpdatedDate(LocalDateTime.now().format(formatter));

        /*ip찾는 로직*/
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        boardDto.setWriteIp(ip);

        BoardEntity boardEntity = boardDto.toEntity();
        boardEntity.setCategory(boardCategoryEntity.get());
        boardEntity.setWriter(memberEntity.get());

        return boardRepository.save(boardEntity).toDto();
    }

    /*보드 수정 후 전송*/
    public BoardDto boardModifySave(BoardDto boardDto, String username,HttpServletRequest request){
        /*Optional<BoardEntity> boardEntityOptional = boardRepository.findById(boardDto.getId());
        Optional<BoardCategoryEntity> boardCategoryEntity = boardCategoryRepository.findById(boardEntityOptional.get().getCategory().getId());*/
        Optional<BoardCategoryEntity> boardCategoryEntity = boardCategoryRepository.findByName(boardDto.getCategoryName());
        Optional<MemberEntity> memberEntity = memberRepository.findByUsername(username);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        boardDto.setUpdatedDate(LocalDateTime.now().format(formatter));

        /*ip찾는 로직*/
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        boardDto.setWriteIp(ip);


        BoardEntity boardEntity = boardDto.toEntity();

        boardEntity.setCategory(boardCategoryEntity.get());
        boardEntity.setWriter(memberEntity.get());

        return boardRepository.save(boardEntity).toDto();
    }

    /*게시글 삭제*/
    public void boardDelete(Long id) {
        boardRepository.deleteById(id);
    }

    public List<BoardDto> mapList(double startLat, double startLng, double endLat, double endLng){
        List<BoardEntity> boardEntityList = boardQueryRepository.getMapList(startLat, startLng, endLat, endLng);
        List<BoardDto> boardDtoList = new ArrayList<>();
        for(BoardEntity boardEntity : boardEntityList){
            boardDtoList.add(boardEntity.toDto());
        }
        return boardDtoList;
    }

    /*summernote 이미지 첨부*/
    public JsonObject boardImageUpload(MultipartFile multipartFile){
        JsonObject jsonObject = new JsonObject();

        String fileRoot = "D:\\summernote_image\\";

        String originalFileName = multipartFile.getOriginalFilename(); // 오리지널 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); //파일확장자
        String savedFileName = UUID.randomUUID() + extension; //저장될 파일 명

        File targetFile = new File(fileRoot + savedFileName);
        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);
            jsonObject.addProperty("url", "/summernoteImage/"+savedFileName);
            jsonObject.addProperty("responseCode", "success");
        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile); //저장된 파일 삭제
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();
        }
        return jsonObject;
    }

}

