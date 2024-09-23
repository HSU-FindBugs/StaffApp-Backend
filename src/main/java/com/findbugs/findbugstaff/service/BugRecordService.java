package com.findbugs.findbugstaff.service;

import com.findbugs.findbugstaff.domain.DetectionHistory;
import com.findbugs.findbugstaff.dto.Bug.BugRecordDto;
import com.findbugs.findbugstaff.implement.Bug.BugRecordFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BugRecordService {
    private final BugDetailService bugDetailService;
    private final BugRecordFinder bugRecordFinder;
    private final CameraService cameraService;
    public BugRecordDto getBugRecord(Long detectionHistoryId){
        DetectionHistory detectionHistory = bugRecordFinder.getHistory(detectionHistoryId);


        BugRecordDto bugRecordDto = BugRecordDto.builder().bugDetailDto(
                        bugDetailService.getBugDataByName(detectionHistory.getBug().getName())

                ).bugSolutionDto(
                        bugDetailService.getBugSolution(detectionHistory.getBug().getName())

                )
                .build();
        return bugRecordDto;
    }


}
