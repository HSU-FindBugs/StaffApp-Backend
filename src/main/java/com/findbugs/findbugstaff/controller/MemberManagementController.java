package com.findbugs.findbugstaff.controller;

import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.domain.Staff;
import com.findbugs.findbugstaff.dto.MemberDto;
import com.findbugs.findbugstaff.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manage")
public class MemberManagementController {

    private final MemberService memberService;

    public MemberManagementController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원 목록 조회
    @GetMapping
    public ResponseEntity<List<MemberDto>> getAllMembers(int page,int size) {
        List<MemberDto> members = memberService.getAllMembers(page,size);
        return ResponseEntity.ok(members);
    }

    // 회원 정보 검색
    @GetMapping("/search")
    public ResponseEntity<List<MemberDto>> searchMembers(@RequestParam String name,@RequestParam Long staffId) {
        List<MemberDto> searchResult = memberService.searchMemberData(name,staffId);
        return ResponseEntity.ok(searchResult);
    }

    // 회원 등록 -> Staff에 종속
    @PostMapping
    public ResponseEntity<String> registerMember(@RequestBody Member member, @RequestParam Long staffId) {
        memberService.registerMember(member,staffId);
        return ResponseEntity.ok("성공");
    }
    // 회원 프로필 정보 확인
    @GetMapping("/{member_id}") // 여기를 memberId로 바꾸면 안됩니다.
    public ResponseEntity<MemberDto> getMemberProfile(@PathVariable("member_id") Long memberId) {
        MemberDto member = memberService.getMemberById(memberId);
        return ResponseEntity.ok(member);
    }

    @PutMapping
    public  ResponseEntity<String> updateMember(@RequestBody Member member,@RequestParam Long memberId,@RequestParam Long staffId){
        memberService.updateMember(staffId,memberId,member);
        return ResponseEntity.ok("성공");
    }

}
