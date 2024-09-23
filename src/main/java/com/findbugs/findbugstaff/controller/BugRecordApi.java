package com.findbugs.findbugstaff.controller;

import com.findbugs.findbugstaff.domain.DetectionHistory;
import com.findbugs.findbugstaff.dto.Bug.BugRecordDto;
import com.findbugs.findbugstaff.dto.ManagementProfilePage.DetectionHistoryDto;
import com.findbugs.findbugstaff.service.BugRecordService;
import com.findbugs.findbugstaff.service.DetectionHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bug-record")
@RequiredArgsConstructor
public class BugRecordApi {
    private final BugRecordService bugRecordService;
    private final DetectionHistoryService detectionHistoryService;

    @Operation(summary = "단일 버그 기록 세부정보 조회", description = "주어진 탐지 이력 ID에 대한 단일 버그 기록의 세부정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "버그 기록을 성공적으로 조회했습니다."),
            @ApiResponse(responseCode = "404", description = "주어진 탐지 이력 ID에 해당하는 버그 기록을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.")
    })
    @GetMapping("/detection-history/{detectionHistoryId}")
    public ResponseEntity<BugRecordDto> getSingleBugRecordDetail(
            @Parameter(description = "탐지 이력 ID", required = true)
            @PathVariable Long detectionHistoryId) {
        if(bugRecordService.getBugRecord(detectionHistoryId) == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(bugRecordService.getBugRecord(detectionHistoryId));
    }

    @Operation(summary = "버그 탐지 이력 목록 조회", description = "주어진 멤버 ID에 대한 버그 탐지 이력 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "버그 탐지 이력 목록을 성공적으로 조회했습니다."),
            @ApiResponse(responseCode = "404", description = "주어진 멤버 ID에 해당하는 버그 탐지 이력을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.")
    })
    @GetMapping("/member/{member_id}/detection-history")
    public ResponseEntity<List<DetectionHistoryDto>> getDetectionBugHistoryList(
            @Parameter(description = "멤버 ID", required = true)
            @PathVariable("member_id") Long memberId) {

        // DetectionHistory 리스트를 조회
        List<DetectionHistory> bugHistoryList = detectionHistoryService.findDetectionHistoryById(memberId);
        if(bugHistoryList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        // DetectionHistory를 DetectionHistoryDto로 변환
        List<DetectionHistoryDto> bugHistoryDtoList = bugHistoryList.stream()
                .map(history -> DetectionHistoryDto.builder()
                        .id(history.getId())
                        .detectionImgUrl(history.getImageUrl().toString())
                        .camera(history.getCamera().toString())
                        .name(history.getBug().getName())
                        .date(history.getDetectedAt().toLocalDate().toString()) // LocalDate로 변환 필요 시
                        .time(history.getDetectedAt().toLocalTime().toString()) // LocalTime으로 변환 필요 시
                        .localDateTime(history.getDetectedAt().toString()) // LocalDateTime으로 변환 필요 시
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(bugHistoryDtoList);
    }
}

