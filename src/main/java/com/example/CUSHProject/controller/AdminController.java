package com.example.CUSHProject.controller;

import com.example.CUSHProject.Pagination.Paging;
import com.example.CUSHProject.dto.MemberDto;
import com.example.CUSHProject.dto.NoticeBoardDto;
import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.entity.MemberEntity;
import com.example.CUSHProject.service.BoardService;
import com.example.CUSHProject.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor
public class AdminController {

    private final MemberService memberService;

    //전체회원 조회
    @GetMapping("/admin")
    public String adminList() {
        return "admin/memberlist";
    }

    /*회원조회 list api*/
    @GetMapping("/api/admin/list/table")
    @ResponseBody
    public HashMap<String, Object> getNoticeList( @RequestParam(required = false) int page,
                                                  @RequestParam(required = false) int perPage,
                                                  @RequestParam(required = false) String searchType,
                                                  @RequestParam(required = false, defaultValue = "") String keyword){
        HashMap<String, Object> objectMap = new HashMap<>();
        HashMap<String, Object> dataMap = new HashMap<>();
        HashMap<String, Object> paginationMap = new HashMap<>();

        int total = memberService.getTotalSize(searchType,keyword);
        List<MemberDto> memberDtoList = memberService.getMemberList(page, perPage, searchType, keyword);

        objectMap.put("result", true);
        objectMap.put("data", dataMap);
        dataMap.put("contents", memberDtoList);
        dataMap.put("pagination", paginationMap);
        paginationMap.put("page", page);
        paginationMap.put("totalCount", total);
        return objectMap;
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
    public String updateForm(MemberDto memberDto){
        memberService.memberUpdate(memberDto);
        return "redirect:/";
    }

    //회원탈퇴
    @ResponseBody
    @DeleteMapping("/api/admin/withdrawal")
    public Long withdrawalMember(@RequestParam(required = false) Long id) {
        memberService.deleteUser(id);
        return id;
    }
}
