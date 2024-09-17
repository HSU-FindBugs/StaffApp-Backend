package com.findbugs.findbugstaff.controller;

import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.dto.Member.MemberDto;
import com.findbugs.findbugstaff.dto.Member.MemberListDto;
import com.findbugs.findbugstaff.dto.Member.MemberRegisterRequestDto;
import com.findbugs.findbugstaff.dto.Member.MemberUpdateRequestDto;
import com.findbugs.findbugstaff.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manage")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원 목록 조회
    // List<MemberListDto> -> 객체 구성 {Dto}
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

    // 회원 등록 -> Staff에 종속
    @PostMapping
    public ResponseEntity<String> registerMember(@RequestBody MemberRegisterRequestDto memberRegisterRequestDto) {

        try {
            memberService.registerMember(memberRegisterRequestDto);
            return ResponseEntity.ok("성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("입력 오류: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // 로그 기록
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("메인 서버 내에서 알 수 없는 오류가 발생했습니다.");
        }
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
