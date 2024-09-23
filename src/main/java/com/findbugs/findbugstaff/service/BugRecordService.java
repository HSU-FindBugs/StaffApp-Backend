package com.findbugs.findbugstaff.service;

import com.findbugs.findbugstaff.dto.Bug.BugRecordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BugRecordService {
    private final BugDetailService bugDetailService;

    public BugRecordDto getBugRecord(Long detectionHistoryId){
        BugRecordDto bugRecordDto = BugRecordDto.builder().bugDetailDto().bugSolutionDto().cameraDto()
    }


}
