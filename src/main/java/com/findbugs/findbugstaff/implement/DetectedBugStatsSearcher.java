package com.findbugs.findbugstaff.implement;

import com.findbugs.findbugstaff.domain.Bug;
import com.findbugs.findbugstaff.domain.DetectedBugStats;
import com.findbugs.findbugstaff.repository.BugRepository;
import com.findbugs.findbugstaff.repository.DetectedBugStatsRepository;
import com.findbugs.findbugstaff.repository.DetectionHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DetectedBugStatsSearcher {

    private final DetectedBugStatsRepository detectedBugsStatsRepository;
    private final DetectionHistoryRepository detectionHistoryRepository;
    private final BugRepository bugRepository;


    @Scheduled(cron = "0 0 2 * * *")
    public void calculateStats(){

        List<Bug> bugList = bugRepository.findAll();
        LocalDate yesterday = LocalDate.now().minusDays(1);

        for(Bug bug : bugList){
            Long detectedCount = detectionHistoryRepository.countDetectedBugByLocalDate(bug.getId(), yesterday);

            detectedBugsStatsRepository.save(
                    DetectedBugStats.builder()
                            .calculatedDate(yesterday)
                            .bug(bug)
                            .detectedCount(detectedCount)
                            .build()
            );
        }
        log.info("complete: {} 일자에 대한 통계 수행 완료", yesterday);
    }

    // 어제 감지된 벌레의 통계를 반환한다
    public DetectedBugStats getStatsForYesterday(Long bugId){
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return detectedBugsStatsRepository.findDetectedBugsStatsByBugId(bugId, yesterday);
    }

    // 특정 일자에 감지된 벌레의 수를 반환한다
    public DetectedBugStats getStatsForYesterday(Long bugId, LocalDate date){
        return detectedBugsStatsRepository.findDetectedBugsStatsByBugId(bugId, date);
    }

    // 작일 가장 많이 탐지된 벌레 반환
    public Bug getMostDetectedBug(){
        List<DetectedBugStats> mostDetectedBugStats = detectedBugsStatsRepository.findMostDetectedBugStats(PageRequest.of(0, 1));
        return mostDetectedBugStats.get(0).getBug();
    }

}
