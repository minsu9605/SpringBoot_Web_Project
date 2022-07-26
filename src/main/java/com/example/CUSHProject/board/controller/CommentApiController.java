package com.example.CUSHProject.board.controller;

import com.example.CUSHProject.board.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
@AllArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/comment/post")
    public HashMap<String, Object> commentPost(@RequestParam Long bid,
                              @RequestParam String comment,
                              Authentication authentication) throws Exception {
        int cDepth = 0;
        Long cGroup = 0L;

        return commentService.commentPost(bid, comment, cDepth, cGroup, authentication.getName());
    }

    @GetMapping("/api/comment/list")
    public HashMap<String, Object> getCommentList(@RequestParam Long bid, HttpServletRequest request) {
        return commentService.getCommentList(bid);
    }

    @DeleteMapping("/api/comment/delete")
    public Long deleteComment(@RequestParam Long cid, HttpSession session) {
        Object roleSession = session.getAttribute("memberRole");

        return commentService.deleteComment(cid, roleSession);
    }

    @PutMapping("/api/comment/modify")
    public String modifyComment(@RequestParam Long cid, @RequestParam String comment) {
        commentService.modifyComment(cid, comment);
        return "success";
    }

    @GetMapping("/api/comment/reComment/list")
    public HashMap<String, Object> getReCommentList(@RequestParam Long cid) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("list", commentService.getReCommentList(cid));
        return map;
    }

    @PostMapping("/api/comment/reComment/post")
    public String reCommentPost(@RequestParam Long bid,
                                @RequestParam String comment,
                                @RequestParam Long cid, Authentication authentication) throws Exception {
        int cDepth = 1;

        commentService.commentPost(bid, comment, cDepth, cid, authentication.getName());
        return "success";
    }
}
