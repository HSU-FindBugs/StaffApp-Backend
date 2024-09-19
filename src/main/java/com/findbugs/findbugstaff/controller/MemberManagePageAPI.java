package com.findbugs.findbugstaff.controller;

import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.dto.Member.MemberDto;
import com.findbugs.findbugstaff.dto.Member.MemberListDto;
import com.findbugs.findbugstaff.dto.Member.MemberRegisterRequestDto;
import com.findbugs.findbugstaff.dto.Member.MemberUpdateRequestDto;
import com.findbugs.findbugstaff.mapper.MemberMapper;
import com.findbugs.findbugstaff.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/manage")
@RequiredArgsConstructor
public class MemberManagePageAPI {

    private final MemberService memberService;
    private final MemberMapper memberMapper;
    @GetMapping
    public ResponseEntity<MemberListDto> getAll(int page) {
        List<Member> members = memberService.getTenMember(page);
        CopyOnWriteArrayList<MemberDto> memberDtos = members.stream()
                .map(memberMapper::toMemberDto)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

        MemberListDto memberListDto = MemberListDto.builder()
                .memberLists(memberDtos)
                .build();

        return ResponseEntity.ok().body(memberListDto);
    }

    // 회원 정보 검색 (동명이인 처리를 위한 List)
    @GetMapping("/search")
    public ResponseEntity<MemberListDto> searchMembers(@RequestParam String name, @RequestParam Long staffId) {
        List<Member> searchResult = memberService.searchMemberData(name, staffId);
        List<MemberDto> memberDtos = searchResult.stream()
                .map(memberMapper::toMemberDto)
                .collect(Collectors.toList());

        // MemberListDto 생성
        MemberListDto memberListDto = MemberListDto.builder()
                .memberLists(new CopyOnWriteArrayList<>(memberDtos))
                .build();

        return ResponseEntity.ok().body(memberListDto);
    }


    // 최근 검색어에 대한 최대 10개의 정보 반환 -> redist
    @GetMapping("/recent/{staffId}")
    public ResponseEntity<List<String>> getRecentSearchData(@PathVariable Long staffId) {
        List<String> recentSearches = memberService.recentSearchData(staffId);
        return ResponseEntity.ok().body(recentSearches);
    }

    // 회원 등록
    @PostMapping
    public ResponseEntity<String> registerMember(@Validated @RequestBody MemberRegisterRequestDto memberRegisterRequestDto,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("요청 양식에 맞추어 다시 작성해주세요");
        }

        memberService.registerMember(memberRegisterRequestDto);
        return ResponseEntity.ok().body("성공적으로 등록 되었습니다.");
    }

    // 회원 프로필 정보 확인
    @GetMapping("/{member_id}")
    public ResponseEntity<MemberDto> getMemberProfile(@PathVariable("member_id") Long memberId) {
        Member member = memberService.getMemberById(memberId);
        MemberDto memberDto = memberMapper.toMemberDto(member);
        return ResponseEntity.ok().body(memberDto);
    }

    // 회원 정보 업데이트
    @PutMapping
    public ResponseEntity<String> updateMember(@RequestBody MemberUpdateRequestDto memberUpdateRequestDto) {
        memberService.updateMember(memberUpdateRequestDto);
        return ResponseEntity.ok().body("Success");
    }


}
