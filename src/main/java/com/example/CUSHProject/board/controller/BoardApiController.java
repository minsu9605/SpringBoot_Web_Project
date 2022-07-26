package com.example.CUSHProject.board.controller;

import com.example.CUSHProject.board.model.BoardDto;
import com.example.CUSHProject.board.model.NoticeBoardDto;
import com.example.CUSHProject.board.service.BoardService;
import com.example.CUSHProject.board.service.NoticeBoardService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
public class BoardApiController {

    private final BoardService boardService;
    private final NoticeBoardService noticeBoardService;

    /*일반 게시판 api*/
    @GetMapping("/api/board/list/table")
    public HashMap<String, Object> getBoardList(@RequestParam(required = false) Long categoryId,
                                                @RequestParam(required = false) int page,
                                                @RequestParam(required = false) int perPage,
                                                @RequestParam(required = false) String searchType,
                                                @RequestParam(required = false, defaultValue = "") String keyword
    ) {
        HashMap<String, Object> objectMap = new HashMap<>();
        HashMap<String, Object> dataMap = new HashMap<>();
        HashMap<String, Object> paginationMap = new HashMap<>();


        int total = boardService.getTotalSize(categoryId, searchType, keyword);
        List<BoardDto> boardDtoList = boardService.getBoardList(categoryId, page, perPage, searchType, keyword);

        objectMap.put("result", true);
        objectMap.put("data", dataMap);
        dataMap.put("contents", boardDtoList);
        dataMap.put("pagination", paginationMap);
        paginationMap.put("page", page);
        paginationMap.put("totalCount", total);
        return objectMap;
    }

    @GetMapping("/api/board/soldOut")
    public String boardMap(@RequestParam(required = false) Long id) {
        boardService.setSoldOut(id);
        return "success";
    }

    @PostMapping("/api/uploadSummernoteImageFile")
    public HashMap<String, Object> uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {
        return boardService.boardImageUpload(multipartFile);
    }

    @DeleteMapping("/api/board/delete")
    public void boardDelete(@RequestParam(required = false) Long id) {
        boardService.boardDelete(id);
    }

    /*내가 쓴 오래된 게시물 api*/
    @GetMapping("/api/board/myOldBoard/table")
    public HashMap<String, Object> getMyOldBoardList(@RequestParam(required = false) int page,
                                                     @RequestParam(required = false) int perPage,
                                                     @RequestParam(required = false) String searchType,
                                                     @RequestParam(required = false, defaultValue = "") String keyword, Authentication authentication) {
        HashMap<String, Object> objectMap = new HashMap<>();
        HashMap<String, Object> dataMap = new HashMap<>();
        HashMap<String, Object> paginationMap = new HashMap<>();

        int total = boardService.getMyOldBoardTotalSize(authentication.getName(), searchType, keyword);
        List<BoardDto> boardEntityList = boardService.getMyOldBoardList(authentication.getName(), page, perPage, searchType, keyword);

        objectMap.put("result", true);
        objectMap.put("data", dataMap);
        dataMap.put("contents", boardEntityList);
        dataMap.put("pagination", paginationMap);
        paginationMap.put("page", page);
        paginationMap.put("totalCount", total);
        return objectMap;
    }

    @GetMapping("/api/board/getMyOldBoardList")
    public HashMap<String, Object> oldBoardAlertList(Authentication authentication,
                                                     @RequestParam(required = false) int startIndex,
                                                     @RequestParam(required = false) int searchStep) {
        HashMap<String, Object> map = new HashMap<>();
        List<BoardDto> boardDtoList = boardService.getMyOldBoardAlertList(authentication.getName(), startIndex, searchStep);
        map.put("totalCnt", boardService.getMyOldBoardAlertListCnt(authentication.getName()));
        map.put("data", boardDtoList);
        return map;
    }

    @GetMapping("/api/board/getMyOldBoardCnt")
    public HashMap<String, Object> getMyOldBoardAlertCnt(Authentication authentication) {
        HashMap<String, Object> map = new HashMap<>();

        if (authentication != null) {
            map.put("totalCnt", boardService.getMyOldBoardAlertListCnt(authentication.getName()));
        } else map.put("totalCnt", 0);

        return map;
    }

    @GetMapping("/api/board/setAlertReading")
    public HashMap<String, Object> setAlertReading(@RequestParam(required = false) Long id) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("result",boardService.setAlertReading(id));

        return map;
    }

    /*내가 쓴 게시물 api*/
    @GetMapping("/api/board/myBoard/table")
    public HashMap<String, Object> getMyBoardList(@RequestParam(required = false) int page,
                                                  @RequestParam(required = false) int perPage,
                                                  @RequestParam(required = false) String searchType,
                                                  @RequestParam(required = false, defaultValue = "") String keyword, Authentication authentication) {
        HashMap<String, Object> objectMap = new HashMap<>();
        HashMap<String, Object> dataMap = new HashMap<>();
        HashMap<String, Object> paginationMap = new HashMap<>();

        int total = boardService.getMyBoardTotalSize(authentication.getName(), searchType, keyword);
        List<BoardDto> boardEntityList = boardService.getMyBoardList(authentication.getName(), page, perPage, searchType, keyword);

        objectMap.put("result", true);
        objectMap.put("data", dataMap);
        dataMap.put("contents", boardEntityList);
        dataMap.put("pagination", paginationMap);
        paginationMap.put("page", page);
        paginationMap.put("totalCount", total);
        return objectMap;
    }

    @GetMapping("/api/admin/adminBoardChart")
    public HashMap<String, Object> adminBoardCnt(@RequestParam(required = false) String monthOption,
                                                 @RequestParam(required = false) String yearOption) {
        return boardService.getBoardCount(yearOption, monthOption);
    }

    /*공지사항 api*/
    @GetMapping("/api/notice/list/table")
    public HashMap<String, Object> getNoticeList( @RequestParam(required = false) int page,
                                                  @RequestParam(required = false) int perPage,
                                                  @RequestParam(required = false) String searchType,
                                                  @RequestParam(required = false, defaultValue = "") String keyword){
        HashMap<String, Object> objectMap = new HashMap<>();
        HashMap<String, Object> dataMap = new HashMap<>();
        HashMap<String, Object> paginationMap = new HashMap<>();


        int total = noticeBoardService.getTotalSize(searchType,keyword);
        List<NoticeBoardDto> noticeBoardDtoList = noticeBoardService.getNoticeBoardList(page, perPage, searchType, keyword);

        objectMap.put("result", true);
        objectMap.put("data", dataMap);
        dataMap.put("contents", noticeBoardDtoList);
        dataMap.put("pagination", paginationMap);
        paginationMap.put("page", page);
        paginationMap.put("totalCount", total);
        return objectMap;
    }

    @DeleteMapping("/api/notice/delete")
    public void noticeBoardDelete(@RequestParam(required = false)Long id) {
        noticeBoardService.noticeBoardDelete(id);
    }

    /*지도에 리스트 불러오기*/
    @GetMapping("/api/mapMenu/list")
    public HashMap<String, Object> markerList(@RequestParam double startLat,
                                              @RequestParam double startLng,
                                              @RequestParam double endLat,
                                              @RequestParam double endLng){

        HashMap<String, Object> map = new HashMap<>();
        List<BoardDto> boardDtoList = boardService.mapList(startLat,startLng,endLat,endLng);
        if(boardDtoList.size()!=0) {
            map.put("data",boardDtoList);
        }else{
            map.put("empty",1);
        }
        return map;
    }
}
