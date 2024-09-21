package com.findbugs.findbugstaff.controller.swagger;

import com.findbugs.findbugstaff.dto.ManagementProfilePage.DetectionHistoryResponseDto;
import com.findbugs.findbugstaff.dto.ManagementProfilePage.ManagementProfileResponseDto;
import com.findbugs.findbugstaff.dto.ManagementProfilePage.ManagementProfileSaveResponseDto;
import com.findbugs.findbugstaff.dto.ManagementProfilePage.ManagementProfileUpdateNoteRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "고객관리_고객정보확인", description = "고객관리_고객정보확인 API")
public interface ManagementProfilePageSwaggerInfo {

    @Operation(summary = "고객 프로필 조회", description = "특정 매니저 ID와 고객 ID로 고객 정보를 조회합니다.")
    @GetMapping("management/visit/{staff_id}/{member_id}")
    ResponseEntity<ManagementProfileResponseDto> getMemberProfile(
            @PathVariable("staff_id") Long staffId,
            @PathVariable("member_id") Long memberId
    );

    @Operation(summary = "고객 방문 등록", description = "특정 매니저 ID와 고객 ID로 고객 방문을 등록합니다.")
    @PostMapping("management/visit/{staff_id}/{member_id}")
    ResponseEntity<ManagementProfileSaveResponseDto> save(
            @PathVariable("staff_id") Long staffId,
            @PathVariable("member_id") Long memberId
    );

    @Operation(summary = "고객 특이사항 수정", description = "특정 매니저 ID와 고객 ID로 고객의 특이사항을 수정합니다.")
    @PostMapping("management/visit/{staff_id}/{member_id}/memo")
    ResponseEntity<ManagementProfileSaveResponseDto> saveMemo(
            @PathVariable("staff_id") Long staffId,
            @PathVariable("member_id") Long memberId,
            @RequestBody ManagementProfileUpdateNoteRequestDto managementProfileUpdateNoteRequestDto
    );

    @Operation(summary = "고객 감지영상 조회", description = "방문 처리되지 않은 벌레 감지 이미지를 반환합니다.")
    @PostMapping("management/visit/{staff_id}/{member_id}/history")
    public ResponseEntity<DetectionHistoryResponseDto> getMemberDetectionHistory(
            @PathVariable("staff_id") Long staffId,
            @PathVariable("member_id") Long memberId
    );

}