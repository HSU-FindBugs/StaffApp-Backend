package com.findbugs.findbugstaff.service;

import com.findbugs.findbugstaff.domain.DetectionHistory;
import com.findbugs.findbugstaff.implement.DetectionHistoryFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetectionHistoryService {

    private final DetectionHistoryFinder detectionHistoryFinder;

    public DetectionHistory findRecentVisitedDetectionHistoryById(Long MemberId) {
        return detectionHistoryFinder.findRecentVisitedDetectionHistory(MemberId);
    }

}
