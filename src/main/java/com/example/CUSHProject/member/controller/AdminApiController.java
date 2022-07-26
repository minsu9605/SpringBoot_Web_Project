package com.example.CUSHProject.member.controller;

import com.example.CUSHProject.member.model.MemberDto;
import com.example.CUSHProject.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminApiController {
    private final MemberService memberService;
    /*회원조회 list api*/
    @GetMapping("/api/admin/list/table")
    public HashMap<String, Object> getNoticeList(@RequestParam(required = false) int page,
                                                 @RequestParam(required = false) int perPage,
                                                 @RequestParam(required = false) String searchType,
                                                 @RequestParam(required = false, defaultValue = "") String keyword) {
        HashMap<String, Object> objectMap = new HashMap<>();
        HashMap<String, Object> dataMap = new HashMap<>();
        HashMap<String, Object> paginationMap = new HashMap<>();

        int total = memberService.getTotalSize(searchType, keyword);
        List<MemberDto> memberDtoList = memberService.getMemberList(page, perPage, searchType, keyword);

        objectMap.put("result", true);
        objectMap.put("data", dataMap);
        dataMap.put("contents", memberDtoList);
        dataMap.put("pagination", paginationMap);
        paginationMap.put("page", page);
        paginationMap.put("totalCount", total);
        return objectMap;
    }

    //회원탈퇴
    @DeleteMapping("/api/admin/withdrawal")
    public Long withdrawalMember(@RequestParam(required = false) Long id) {
        memberService.deleteUser(id);
        return id;
    }
}
