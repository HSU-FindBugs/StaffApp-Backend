package com.findbugs.findbugstaff.controller;

import com.findbugs.findbugstaff.domain.DetectionHistory;
import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.domain.Visit;
import com.findbugs.findbugstaff.dto.ManagementProfilePage.ManagementProfileResponseDto;
import com.findbugs.findbugstaff.dto.ManagementProfilePage.ManagementProfileSaveResponseDto;
import com.findbugs.findbugstaff.dto.ManagementProfilePage.ManagementProfileUpdateNoteRequestDto;
import com.findbugs.findbugstaff.mapper.ManagementProfilePage.ManagementProfilePageMapper;
import com.findbugs.findbugstaff.service.DetectionHistoryService;
import com.findbugs.findbugstaff.service.MemberService;
import com.findbugs.findbugstaff.service.VisitService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VisitAPI {

    private final MemberService memberService;
    private final VisitService visitService;
    private final DetectionHistoryService detectionHistoryService;

    private final ManagementProfilePageMapper managementProfilePageMapper;

    @GetMapping("{staff_id}/visit/{member_id}")
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

    @PostMapping("{staff_id}/visit/{member_id}")
    public ResponseEntity<ManagementProfileSaveResponseDto> save(
            @PathVariable("staff_id") Long staffId,
            @PathVariable("member_id") Long memberId
    ) {
        visitService.staffVisitMember(memberId, staffId);

        return ResponseEntity.ok(
                ManagementProfileSaveResponseDto.builder().isSaved(true).build());
    }

    @PostMapping("{staff_id}/visit/{member_id}/memo")
    public ResponseEntity<ManagementProfileSaveResponseDto> saveMemo(
            @PathVariable("staff_id") Long staffId,
            @PathVariable("member_id") Long memberId,
            @RequestBody ManagementProfileUpdateNoteRequestDto managementProfileUpdateNoteRequestDto
    ){
        memberService.setNote(memberId, managementProfileUpdateNoteRequestDto.getNote());

        return ResponseEntity.ok(
                ManagementProfileSaveResponseDto.builder().isSaved(true).build());
    }

}
