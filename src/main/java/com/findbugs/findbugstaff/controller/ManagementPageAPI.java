package com.findbugs.findbugstaff.controller;

import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.dto.Member.ManagementPageMemberDto;
import com.findbugs.findbugstaff.dto.Member.ManagementPageResponseDto;
import com.findbugs.findbugstaff.dto.Member.MemberRegisterRequestDto;
import com.findbugs.findbugstaff.dto.Member.MemberUpdateRequestDto;
import com.findbugs.findbugstaff.mapper.ManagementPage.ManagementPageMapper;
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
@RequiredArgsConstructor
public class ManagementPageAPI {

    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private final ManagementPageMapper managementPageMapper;


    /**
     * 고객관리_API (고객목록 조회)
     * @param page 입력 받고자 하는 페이지 (ex) 10 입력시 10~19번째 사용자 반환)
     * @return ManagementPageMemberListDto 사용자 내역 반환
     */
    @GetMapping("management/{staff_id}/{page}")
    public ResponseEntity<ManagementPageResponseDto> getAllByStaffId(
            @PathVariable("staff_id") Long staffId,
            @PathVariable("page") int page
    ) {
        List<Member> members = memberService.get10ByStaffId(staffId, page);
        return ResponseEntity.ok().body(managementPageMapper.toManagementPageResponseDto(members));
    }

    /**
     * 고객관리_고객찾기 페이지 API
     * @param staffId 매니저 ID
     * @param memberName 사용자 이름
     * @return ManagementPageResponseDto 사용자 내역 반환
     */
    @GetMapping("management/search/{staff_id}/{member_name}")
    public ResponseEntity<ManagementPageResponseDto> searchMembers(
            @PathVariable("staff_id") Long staffId,
            @PathVariable("member_name") String memberName
    ) {
        List<Member> members = memberService.searchMemberData(memberName, staffId);
        return ResponseEntity.ok().body(managementPageMapper.toManagementPageResponseDto(members));
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
    public ResponseEntity<ManagementPageMemberDto> getMemberProfile(@PathVariable("member_id") Long memberId) {
        Member member = memberService.getMemberById(memberId);
        ManagementPageMemberDto memberDto = memberMapper.toMemberDto(member);
        return ResponseEntity.ok().body(memberDto);
    }

    // 회원 정보 업데이트
    @PutMapping
    public ResponseEntity<String> updateMember(@RequestBody MemberUpdateRequestDto memberUpdateRequestDto) {
        memberService.updateMember(memberUpdateRequestDto);
        return ResponseEntity.ok().body("Success");
    }


}
