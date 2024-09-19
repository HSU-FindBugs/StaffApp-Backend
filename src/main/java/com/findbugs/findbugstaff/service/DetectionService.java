package com.findbugs.findbugstaff.service;


import com.findbugs.findbugstaff.dto.Bug.BugDetectionRequestDto;
import com.findbugs.findbugstaff.implement.Detection.DetectionRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DetectionService {
    private final DetectionRegister detectionRegister;
    public void handleBugDetection(BugDetectionRequestDto bugDetectionDto) {
        detectionRegister.bugDetection(bugDetectionDto);
    }

}

