package com.findbugs.findbugstaff.controller;

import com.findbugs.findbugstaff.dto.Bug.BugRecordDto;
import com.findbugs.findbugstaff.service.BugRecordService;
import com.findbugs.findbugstaff.service.DetectionHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/{detectionHistoryId}")
    public BugRecordDto getSingleBugRecordDetail(
            @Parameter(description = "탐지 이력 ID", required = true)
            @PathVariable Long detectionHistoryId) {
        return bugRecordService.getBugRecord(detectionHistoryId);
    }
}
