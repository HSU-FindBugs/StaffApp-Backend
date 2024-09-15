package com.findbugs.findbugstaff.implement;

import com.findbugs.findbugstaff.repository.DetectionHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class DetectedBugsFinder {

    private final DetectionHistoryRepository detectionHistoryRepository;

    // 최적화를 위해 오전 2시에 해당 내용들을 조회하여 통계 테이블 {@DetectedBugStats}에 저장한다.

    // 어제 기준 탐지 된 벌레의 수 조회
    public Long countDetectedBugByYesterday(Long bugId){
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return detectionHistoryRepository.countDetectedBugByLocalDate(bugId, yesterday);
    }

    // 오늘 기준 탐지 된 벌레의 수 조회
    public Long countDetectedBugByToday(Long bugId){
        LocalDate today = LocalDate.now();
        return detectionHistoryRepository.countDetectedBugByLocalDate(bugId, today);
    }

    // 특정 일자 기준 탐지 된 벌레의 수 조회
    public Long countDetectedBugByLocalDate(Long bugId, LocalDate localDate){
        return detectionHistoryRepository.countDetectedBugByLocalDate(bugId, localDate);
    }


}
