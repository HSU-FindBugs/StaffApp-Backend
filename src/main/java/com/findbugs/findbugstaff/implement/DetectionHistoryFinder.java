package com.findbugs.findbugstaff.implement;


import com.findbugs.findbugstaff.domain.DetectionHistory;
import com.findbugs.findbugstaff.repository.DetectionHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;


@Slf4j
@Component
@RequiredArgsConstructor
public class DetectionHistoryFinder {

    private final DetectionHistoryRepository detectionHistoryRepository;

    // 방문 처리 된 기록 중 가장 최신 기록 1개를 조회하는 기능
    public DetectionHistory findRecentVisitedDetectionHistory(Long memberId) {
        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "visitedAt"));
        return detectionHistoryRepository.findRecentVisitedByMemberId(memberId, pageable)
                .getContent().stream().findFirst().orElseThrow(NoSuchElementException::new);
    }

    // 방문 처리 되지 않은 기록 모두를 조회하는 기능
    public List<DetectionHistory> findUnVisited(Long memberId) {
        return detectionHistoryRepository.findUnVisitedByMemberId(memberId);
    }

}
