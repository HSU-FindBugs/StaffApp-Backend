package com.findbugs.findbugstaff.controller;

import com.findbugs.findbugstaff.dto.Member.MemberDto;
import com.findbugs.findbugstaff.dto.Member.MemberListDto;
import com.findbugs.findbugstaff.dto.Member.MemberRegisterRequestDto;
import com.findbugs.findbugstaff.dto.Member.MemberUpdateRequestDto;
import com.findbugs.findbugstaff.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manage")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원 목록 조회
    @GetMapping
    public ResponseEntity<List<MemberListDto>> getAllMembers(int page) {
        List<MemberListDto> members = memberService.getAllMembers(page);
        return ResponseEntity.ok().body(members);
    }

    // 회원 정보 검색
    @GetMapping("/search")
    public ResponseEntity<List<MemberDto>> searchMembers(@RequestParam String name, @RequestParam Long staffId) {
        List<MemberDto> searchResult = memberService.searchMemberData(name,staffId);
        return ResponseEntity.ok().body(searchResult);
    }
    // 최근 검색어에 대한 최대 10개의 정보 반환 -> 각 staffId에 맞는
    @GetMapping("/recent/{staffId}")
    public ResponseEntity<List<String>> getRecentSearcheData(@PathVariable Long staffId) {
        List<String> getRecentSearchs = memberService.recentSearchData(staffId);

        return ResponseEntity.ok(getRecentSearchs);
    }



    // 회원 등록 -> Staff에 종속
    @PostMapping
    public ResponseEntity<String> registerMember(@RequestBody MemberRegisterRequestDto memberRegisterRequestDto) {
        memberService.registerMember(memberRegisterRequestDto);
        return ResponseEntity.ok("성공");
    }


    // 회원 프로필 정보 확인
    @GetMapping("/{member_id}")
    public ResponseEntity<MemberDto> getMemberProfile(@PathVariable("member_id") Long memberId) {
        MemberDto member = memberService.getMemberById(memberId);
        return ResponseEntity.ok().body(member);
    }

    @PutMapping
    public  ResponseEntity<String> updateMember(@RequestBody MemberUpdateRequestDto memberUpdateRequestDto){
        memberService.updateMember(memberUpdateRequestDto);
        return ResponseEntity.ok().body("성공");
    }

}
