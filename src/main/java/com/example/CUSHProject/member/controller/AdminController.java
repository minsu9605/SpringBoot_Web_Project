package com.example.CUSHProject.member.controller;

import com.example.CUSHProject.member.model.MemberDto;
import com.example.CUSHProject.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class AdminController {

    private final MemberService memberService;

    //전체회원 조회
    @GetMapping("/admin")
    public String adminList() {
        return "admin/memberlist";
    }

    //회원 정보 폼
    @GetMapping("/admin/memberform")
    public String adminForm(Model model, @RequestParam(required = false) Long id) throws Exception {
        MemberDto memberDto = memberService.findById(id);
        model.addAttribute("member", memberDto);
        return "admin/memberform";
    }

    //회원 정보 수정
    @PostMapping("/admin/memberform")
    public String updateForm(MemberDto memberDto) {
        memberService.memberUpdate(memberDto);
        return "redirect:/";
    }

    //회원 정보 폼
    @GetMapping("/admin/boardChart")
    public String adminBoardChart() {

        return "admin/adminBoardChart";
    }

    /*//카테고리 추가
    @GetMapping("/admin/boardChart")
    public String addCategory() {

        return "admin/adminBoardChart";
    }*/

}
