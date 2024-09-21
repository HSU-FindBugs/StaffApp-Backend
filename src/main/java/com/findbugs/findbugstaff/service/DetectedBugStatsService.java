package com.findbugs.findbugstaff.service;

import com.findbugs.findbugstaff.domain.Bug;
import com.findbugs.findbugstaff.domain.DetectedBugStats;
import com.findbugs.findbugstaff.implement.DetectedBugStatsSearcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetectedBugStatsService {

    private final DetectedBugStatsSearcher detectedBugStatsSearcher;

    public DetectedBugStats getDetectedBugsStats(Long bugId){

        // 예외 처리 필요
        return detectedBugStatsSearcher
                .getStatsForYesterday(bugId);
    }

    public Bug getMostDetectedBug(){
        return detectedBugStatsSearcher.getMostDetectedBug();
    }

    public String getDetectedBugStatsMessage(Long bugId){

        DetectedBugStats detectedBugStats = getDetectedBugsStats(bugId);

        log.info("detectedId = {}", detectedBugStats.getId());
        log.info("bugId = {}", detectedBugStats.getBug().getId());
        log.info("bugName = {}", detectedBugStats.getBug().getName());

        return String.format("어제 발견된 %s의 수는 총 %d마리입니다!",
                detectedBugStats.getBug().getName(), detectedBugStats.getDetectedCount());
    }
}
