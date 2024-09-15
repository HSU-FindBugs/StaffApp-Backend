package com.findbugs.findbugstaff.repository;

import com.findbugs.findbugstaff.domain.DetectedBugStats;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DetectedBugStatsRepository extends JpaRepository<DetectedBugStats, Long> {

    // 해당 Repository 에 대한 테스트는 서비스 테스트에서 겸하여 진행

    // 특정 일자 기반 탐지된 벌레의 수 반환
    @EntityGraph(attributePaths = {"bug"})
    @Query("select dbs from DetectedBugStats dbs where dbs.bug.id = :bugId and dbs.calculatedDate = :date")
    DetectedBugStats findDetectedBugsStatsByBugId(@Param("bugId") Long bugId, @Param("date") LocalDate date);

    // 가장 많이 발견된 벌레에 대한 통계를 조회
    @EntityGraph(attributePaths = {"bug"})
    @Query("select dbs from DetectedBugStats dbs order by dbs.detectedCount desc")
    List<DetectedBugStats> findMostDetectedBugStats(Pageable pageable);


}
