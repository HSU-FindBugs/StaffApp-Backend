package com.findbugs.findbugstaff.service;

import com.findbugs.findbugstaff.domain.DetectionHistory;
import com.findbugs.findbugstaff.dto.Bug.BugRecordDto;
import com.findbugs.findbugstaff.implement.Bug.BugRecordFinder;
import com.findbugs.findbugstaff.implement.DetectionHistoryFinder;
import com.findbugs.findbugstaff.repository.DetectionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BugRecordService {
    private final BugDetailService bugDetailService;
    private final BugRecordFinder bugRecordFinder;

    public BugRecordDto getBugRecord(Long detectionHistoryId){
        DetectionHistory detectionHistory = bugRecordFinder.getHistory(detectionHistoryId);
        String bugname =  detectionHistory.getBug().getName();
        String bugUrl = detectionHistory.getBug().getBugImageUrl();
        String bugdescription = detectionHistory.getBug().getDescription();
        BugRecordDto bugRecordDto = BugRecordDto.builder().bugName(bugname)
                .bugDescription(bugdescription)
                .bugUrl(bugUrl)
                .cameraId(detectionHistory.getCamera().getId())
                .bugFindDate(detectionHistory.getDetectedAt().toLocalDate().toString())
                .bugFindTime(detectionHistory.getDetectedAt().toLocalTime().toString())
        .bugDetailDto(
                        bugDetailService.getBugDataByName(bugname)

                ).bugSolutionDto(
                        bugDetailService.getBugSolution(bugname)
                )
                .build();
        return bugRecordDto;
    }


}
