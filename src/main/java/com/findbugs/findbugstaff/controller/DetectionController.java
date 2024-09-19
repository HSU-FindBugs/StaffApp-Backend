package com.findbugs.findbugstaff.controller;

import com.findbugs.findbugstaff.dto.Bug.BugDetectionRequestDto;
import com.findbugs.findbugstaff.service.DetectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/detect")
@RequiredArgsConstructor
public class DetectionController {

    private final DetectionService detectionService;

    @PostMapping("/bug-detection")
    public ResponseEntity<String> detectBug(@RequestBody BugDetectionRequestDto bugDetectionDto) {
        System.out.println("Received DTO: " + bugDetectionDto);
        detectionService.handleBugDetection(bugDetectionDto);
        return ResponseEntity.ok().body("성공적으로 벌래 탐지 알림을 매니저에게 전달했습니다.");
    }



}
