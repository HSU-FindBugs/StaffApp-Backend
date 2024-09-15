package com.findbugs.findbugstaff.repository;

import com.findbugs.findbugstaff.domain.DetectionHistory;
import com.findbugs.findbugstaff.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DetectionHistoryRepository extends JpaRepository<DetectionHistory, Long> {

    // 특정 고객 감지 내역 조회
    @Query("select dh from DetectionHistory dh where dh.member = :member")
    List<DetectionHistory> findHistoryByMember(@Param("member") Member member);

    // 통계 : 특정 일자 벌레 발견 통계 조회
    @Query("select count(dh) from DetectionHistory dh where dh.bug.id = :bugId and dh.detectedAt = :date")
    Long countDetectedBugByLocalDate(@Param("bugId") Long id, @Param("date") LocalDateTime date);

}
