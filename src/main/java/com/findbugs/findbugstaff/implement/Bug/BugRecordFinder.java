package com.findbugs.findbugstaff.implement.Bug;

import com.findbugs.findbugstaff.domain.DetectionHistory;
import com.findbugs.findbugstaff.repository.DetectionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BugRecordFinder {
    private final DetectionHistoryRepository detectionHistoryRepository;

    public DetectionHistory getHistory(Long detectionHistoryId){
        Optional<DetectionHistory> detectionHistory = detectionHistoryRepository.findById(detectionHistoryId);
        if(detectionHistory.isPresent()){
            DetectionHistory detectionHistory1 = detectionHistory.get();
            return detectionHistory1;
        }
        return null;
    }
}
