package com.findbugs.findbugstaff.controller.swagger;

import com.findbugs.findbugstaff.dto.Member.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "고객관리_고객찾기 페이지", description = "고객관리_고객찾기 페이지 API")
public interface ManagementPageSwaggerInfo {

    @Operation(
            summary = "고객관리_고객찾기 : 고객목록조회",
            description = "고객찾기 페이지에서 고객목록을 조회하는 페이지입니다"
    )
    @GetMapping("management/{staff_id}/{page}")
    ResponseEntity<ManagementPageResponseDto> getAllByStaffId(
            @PathVariable("staff_id") Long staffId,
            @PathVariable("page") int page
    );

    @Operation(
            summary = "고객관리_고객찾기 : 고객정보검색",
            description = "고객찾기 페이지에서 고객정보를 검색하는 API 입니다"
    )
    @GetMapping("management/search/{staff_id}/{member_name}")
    ResponseEntity<ManagementPageResponseDto> searchMembers(
            @PathVariable("staff_id") Long staffId,
            @PathVariable("member_name") String memberName
    );

    @Operation(
            summary = "고객관리_고객찾기 : 사용자 최신 검색 기록 조회",
            description = "검색 페이지에서 사용자의 최신 검색 기록을 반환합니다"
    )
    @GetMapping("management/recent/{staff_id}")
    ResponseEntity<ManagementPageRecentSearchResponseDto> getRecentSearchData(
            @PathVariable("staff_id") Long staffId
    );


    @Operation(
            summary = "고객관리_고객찾기 : 고객등록",
            description = "새로운 고객을 등록하기 위한 API 입니다."
    )
    @PostMapping
    ResponseEntity<ManagementPageSaveDto> registerMember(
            @Validated @RequestBody MemberRegisterRequestDto memberRegisterRequestDto,
            BindingResult bindingResult
    );

    @Operation(
            summary = "(재검토 필요) 회원 프로필 정보 확인",
            description = "특정 회원의 프로필 정보를 반환"
    )
    @GetMapping("management/{member_id}")
    ResponseEntity<ManagementPageMemberDto> getMemberProfile(
            @PathVariable("member_id") Long memberId
    );

    @Operation(
            summary = "(재검토 필요) 회원 정보 업데이트",
            description = "특정 회원의 정보를 업데이트"
    )
    @PutMapping("management")
    ResponseEntity<String> updateMember(
            @RequestBody MemberUpdateRequestDto memberUpdateRequestDto
    );
}
