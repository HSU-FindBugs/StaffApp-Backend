package com.findbugs.findbugstaff.service;

import com.findbugs.findbugstaff.domain.Bug;
import com.findbugs.findbugstaff.domain.DetectedBugStats;
import com.findbugs.findbugstaff.implement.DetectedBugStatsSearcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
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

        return String.format("어제 발견된 %s의 수는 총 %d 마리입니다!",
                detectedBugStats.getBug().getName(), detectedBugStats.getDetectedCount());
    }
}
