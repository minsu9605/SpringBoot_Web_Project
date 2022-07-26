package com.example.CUSHProject.board.controller;

import com.example.CUSHProject.board.model.BoardCategoryDto;
import com.example.CUSHProject.board.model.BoardDto;
import com.example.CUSHProject.board.service.BoardService;
import com.example.CUSHProject.board.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CategoryService categoryService;

    /*메인 페이지*/
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /*일반 게시판*/
    @GetMapping("/board/list")
    public String boardList(Model model) {
        model.addAttribute("categoryList", categoryService.getCategoryList());
        return "board/boardlist";
    }

    //글쓰기
    @GetMapping("/board/write")
    public String boardWrite(Model model) {
        BoardDto boardForm = new BoardDto();
        List<BoardCategoryDto> categoryList = categoryService.getCategoryList();
        model.addAttribute("boardForm", boardForm);
        model.addAttribute("categoryList", categoryList);
        return "board/boardform";
    }

    @PostMapping("/board/write")
    public String boardWrite(@RequestParam(required = false) Long category, BoardDto boardDto, Authentication authentication, HttpServletRequest request) {
        boardService.boardWrite(boardDto, authentication.getName(), request);
        return "redirect:/board/list?category=" + category;
    }

    @GetMapping("/board/content")
    public String boardContent(Model model, @RequestParam(required = false) Long id) {
        boardService.boardHitUpdate(id);
        BoardDto boardForm = boardService.boardContent(id);

        model.addAttribute("categoryList", categoryService.getCategoryList());
        model.addAttribute("boardForm", boardForm);
        return "board/boardcontent";
    }

    @GetMapping("/board/modify")
    public String boardModify(Model model, @RequestParam(required = false) Long id) {
        BoardDto boardForm = boardService.boardContent(id);

        List<BoardCategoryDto> categoryList = categoryService.getCategoryList();
        model.addAttribute("boardForm", boardForm);
        model.addAttribute("categoryList", categoryList);
        return "board/boardmodify";
    }

    @PostMapping("/board/modify")
    public String boardModify(@RequestParam(required = false) Long id, BoardDto boardDto, Authentication authentication, HttpServletRequest request) {
        boardService.boardModifySave(boardDto, authentication.getName(), request);
        return "redirect:/board/content?id=" + id;
    }

    @GetMapping("/board/map")
    public String showMap() {
        return "board/map";
    }

    @GetMapping("/board/map_content")
    public String showMapContent() {
        return "board/map_content";
    }

    //내가 쓴 게시물(내정보)
    @GetMapping("/board/myBoard")
    public String myBoard() {
        return "account/myboard";
    }

    //내가 쓴 오래된 게시물
    @GetMapping("/board/myOldBoard")
    public String myOldBoard() {
        return "account/myOldBoard";
    }

}
