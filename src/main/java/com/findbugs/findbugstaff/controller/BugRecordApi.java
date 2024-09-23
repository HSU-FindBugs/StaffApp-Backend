package com.findbugs.findbugstaff.controller;

import com.findbugs.findbugstaff.dto.Bug.BugRecordDto;
import com.findbugs.findbugstaff.service.BugRecordService;
import com.findbugs.findbugstaff.service.DetectionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bug-record")
@RequiredArgsConstructor
public class BugRecordApi {
    private final BugRecordService bugRecordService;
    private final DetectionHistoryService detectionHistoryService;

    public BugRecordDto getSingleBugRecordDetail(Long detectionHistoryId){
        return bugRecordService.getBugRecord(detectionHistoryId);
    }
    
}
