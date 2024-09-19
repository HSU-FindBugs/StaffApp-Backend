package com.findbugs.findbugstaff.controller;

import com.findbugs.findbugstaff.controller.swagger.ManagementPageSwaggerInfo;
import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.dto.Member.*;
import com.findbugs.findbugstaff.mapper.ManagementPage.ManagementPageMapper;
import com.findbugs.findbugstaff.mapper.MemberMapper;
import com.findbugs.findbugstaff.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ManagementPageAPI implements ManagementPageSwaggerInfo {

    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private final ManagementPageMapper managementPageMapper;


    /**
     * 고객관리_API (고객목록 조회)
     * @param page 입력 받고자 하는 페이지 (ex) 10 입력시 10~19번째 사용자 반환)
     * @return ManagementPageMemberListDto 사용자 내역 반환
     */
    @Override
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
    @Override
    @GetMapping("management/search/{staff_id}/{member_name}")
    public ResponseEntity<ManagementPageResponseDto> searchMembers(
            @PathVariable("staff_id") Long staffId,
            @PathVariable("member_name") String memberName
    ) {
        List<Member> members = memberService.searchMemberData(memberName, staffId);
        return ResponseEntity.ok().body(managementPageMapper.toManagementPageResponseDto(members));
    }


    /**
     * 고객관리_고객찾기 페이지 API - 최근 검색한 고객
     * @param staffId 매니저 ID
     * @return ManagementPageRecentSearchResponseDto 최근 검색 내역 Dto
     */
    @Override
    @GetMapping("management/recent/{staff_id}")
    public ResponseEntity<ManagementPageRecentSearchResponseDto> getRecentSearchData(
            @PathVariable("staff_id") Long staffId
    ) {
        return ResponseEntity.ok().body(managementPageMapper
                .toManagementPageRecentSearchResponseDto(
                        memberService.recentSearchData(staffId)
                ));
    }

    /**
     * 고객관리_고객찾기 API - 고객 등록
     * @param memberRegisterRequestDto 등록 내역 반환
     * @return ManagementPageSaveDto 저장 여부 반환
     */
    @Override
    @PostMapping
    public ResponseEntity<ManagementPageSaveDto> registerMember(
            @Validated @RequestBody MemberRegisterRequestDto memberRegisterRequestDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    ManagementPageSaveDto.builder()
                            .isChecked(false)
                            .build());
        }

        memberService.registerMember(memberRegisterRequestDto);
        return ResponseEntity.ok().body(
                ManagementPageSaveDto.builder()
                        .isChecked(true)
                        .build()
        );
    }

    // 회원 프로필 정보 확인
    @Override
    @GetMapping("/{member_id}")
    public ResponseEntity<ManagementPageMemberDto> getMemberProfile(@PathVariable("member_id") Long memberId) {
        Member member = memberService.getMemberById(memberId);
        ManagementPageMemberDto memberDto = memberMapper.toMemberDto(member);
        return ResponseEntity.ok().body(memberDto);
    }

    // 회원 정보 업데이트
    @Override
    @PutMapping
    public ResponseEntity<String> updateMember(
            @RequestBody MemberUpdateRequestDto memberUpdateRequestDto
    ) {
        memberService.updateMember(memberUpdateRequestDto);
        return ResponseEntity.ok().body("Success");
    }


}
