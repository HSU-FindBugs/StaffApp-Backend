package com.findbugs.findbugstaff.controller;

import com.findbugs.findbugstaff.controller.swagger.ManagementProfilePageSwaggerInfo;
import com.findbugs.findbugstaff.domain.DetectionHistory;
import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.dto.ManagementProfilePage.DetectionHistoryResponseDto;
import com.findbugs.findbugstaff.dto.ManagementProfilePage.ManagementProfileResponseDto;
import com.findbugs.findbugstaff.dto.ManagementProfilePage.ManagementProfileSaveResponseDto;
import com.findbugs.findbugstaff.dto.ManagementProfilePage.ManagementProfileUpdateNoteRequestDto;
import com.findbugs.findbugstaff.implement.DetectionHistoryFinder;
import com.findbugs.findbugstaff.mapper.ManagementProfilePage.ManagementProfilePageMapper;
import com.findbugs.findbugstaff.service.DetectionHistoryService;
import com.findbugs.findbugstaff.service.MemberService;
import com.findbugs.findbugstaff.service.VisitService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ManagementProfilePageAPI implements ManagementProfilePageSwaggerInfo {

    private final MemberService memberService;
    private final VisitService visitService;
    private final DetectionHistoryService detectionHistoryService;

    private final ManagementProfilePageMapper managementProfilePageMapper;
    private final DetectionHistoryFinder detectionHistoryFinder;

    /**
     * 고객관리_고객정보확인 - 방문등록 이전 고객정보확인을 위한 API
     * @param staffId 매니저 ID
     * @param memberId 고객 ID
     * @return ManagementProfileResponseDto 고객 정보 Dto
     */
    @Override
    @GetMapping("management/visit/{staff_id}/{member_id}")
    public ResponseEntity<ManagementProfileResponseDto> getMemberProfile(
            @PathVariable("staff_id") Long staffId,
            @PathVariable("member_id") Long memberId
    ) {

        Member member = memberService.getById(memberId);
        String remainingMembershipDays = memberService.getRemainingMembershipDays(memberId);
        String visitStatus = visitService.checkStaffVisitedMember(memberId, staffId);
        DetectionHistory recentVisitHistory = detectionHistoryService.findRecentVisitedDetectionHistoryById(memberId);


        ManagementProfileResponseDto dto = managementProfilePageMapper
                .toManagementProfileResponseDto(member, remainingMembershipDays, visitStatus, recentVisitHistory);

        return ResponseEntity.ok(dto);
    }


    /**
     * 고객관리_고객정보확인 - 방문등록을 위한 API
     * @param staffId 매니저 ID
     * @param memberId 고객 ID
     * @return ManagementProfileSaveResponseDto true/false
     */
    @Override
    @PostMapping("management/visit/{staff_id}/{member_id}")
    public ResponseEntity<ManagementProfileSaveResponseDto> save(
            @PathVariable("staff_id") Long staffId,
            @PathVariable("member_id") Long memberId
    ) {
        visitService.staffVisitMember(memberId, staffId);

        return ResponseEntity.ok(
                ManagementProfileSaveResponseDto.builder().isSaved(true).build());
    }

    /**
     * 고객관리_고객정보확인 - 고객 특이사항 수정을 위한 API
     * @param staffId 매니저 ID
     * @param memberId 사용자 ID
     * @param managementProfileUpdateNoteRequestDto 수정한 고객 특이사항
     * @return ManagementProfileSaveResponseDto 저장 성공 여부 true/false
     */
    @Override
    @PostMapping("management/visit/{staff_id}/{member_id}/memo")
    public ResponseEntity<ManagementProfileSaveResponseDto> saveMemo(
            @PathVariable("staff_id") Long staffId,
            @PathVariable("member_id") Long memberId,
            @RequestBody ManagementProfileUpdateNoteRequestDto managementProfileUpdateNoteRequestDto
    ){
        memberService.setNote(memberId, managementProfileUpdateNoteRequestDto.getNote());

        return ResponseEntity.ok(
                ManagementProfileSaveResponseDto.builder().isSaved(true).build());
    }


    /**
     * 고객관리_고객감지기록조회
     * @param staffId 스태프 id
     * @param memberId 사용자 id
     * @return DetectionHistoryResponseDto 감지 내역 반환
     */
    @PostMapping("management/visit/{staff_id}/{member_id}/history")
    public ResponseEntity<DetectionHistoryResponseDto> getMemberDetectionHistory(
            @PathVariable("staff_id") Long staffId,
            @PathVariable("member_id") Long memberId
    ){

        List<DetectionHistory> historyList = detectionHistoryFinder.findUnVisited(memberId);
        return ResponseEntity.ok(managementProfilePageMapper.toDetectionHistoryResponseDto(historyList));
    }

}
