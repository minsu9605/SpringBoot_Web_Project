package com.example.CUSHProject.member.controller;

import com.example.CUSHProject.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    //회원가입 이메일 중복 체크
    @GetMapping("/api/overlap/usernameRegister")
    public HashMap<String, Object> usernameOverlap(@RequestParam(value = "username", required = false) String username) {
        return memberService.usernameOverlap(username);
    }

    //회원가입 닉네임 중복 체크
    @GetMapping("/api/overlap/nicknameRegister")
    public HashMap<String, Object> nicknameOverlap(@RequestParam(value = "nickname", required = false) String nickname) {
        return memberService.nicknameOverlap(nickname);
    }

    //아이디 수정 중복 체크
    @PostMapping("/api/usernameModify")
    public HashMap<String, Object> usernameModify(@RequestParam(required = false) Long id, String username) {
        return memberService.usernameModify(username, id);
    }

    //닉네임 수정 중복 체크
    @PostMapping("/api/nicknameModify")
    public HashMap<String, Object> nicknameModify(@RequestParam(required = false) Long id, String nickname) {
        return memberService.nicknameModify(nickname, id);
    }

    //패스워드 확인
    @GetMapping("/api/pwCheck")
    public HashMap<String, Object> pwCheck(@RequestParam(required = false) String original_Pw, Authentication authentication) {
        return memberService.pwCheck(authentication,original_Pw);
    }
}
