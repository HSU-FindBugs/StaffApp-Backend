package com.findbugs.findbugstaff.implement.Detection;

import com.findbugs.findbugstaff.domain.DetectionHistory;
import com.findbugs.findbugstaff.repository.DetectionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DetectionUpdater {
    private final DetectionHistoryRepository detectionHistoryRepository;

    public void changeDetectionHistoryUrl(Long detectionHistoryId,String changeUrl){
        Optional<DetectionHistory> detectionHistory = detectionHistoryRepository.findById(detectionHistoryId);
        if(detectionHistory.isPresent()){
            DetectionHistory changeData = detectionHistory.get();
            changeData.updateImageUrl(changeUrl);
            detectionHistoryRepository.save(changeData);
        }
    }
}
