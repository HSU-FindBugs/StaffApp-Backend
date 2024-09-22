package com.findbugs.findbugstaff.repository;

import com.findbugs.findbugstaff.domain.DetectionHistory;
import com.findbugs.findbugstaff.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DetectionHistoryRepository extends JpaRepository<DetectionHistory, Long> {

    // 방문 처리 된 감지 영상 중 가장 최신 감지 영상 조회 :테스트 반영 필요
    @EntityGraph(attributePaths = {"visit"})
    @Query("select dh from DetectionHistory dh where dh.member.id = :memberId and dh.visit is not null order by dh.visit.visitedAt desc")
    Page<DetectionHistory> findRecentVisitedByMemberId(@Param("memberId") Long memberId, Pageable pageable);

    // 방문 처리 되지 않은 감지 영상 모두를 조회
    @EntityGraph(attributePaths = {"camera, bug"})
    @Query("select dh from DetectionHistory dh where dh.member.id = :memberId and dh.visit is null")
    List<DetectionHistory> findUnVisitedByMemberId(@Param("memberId") Long memberId);

    // 특정 고객 감지 내역 조회
    @Query("select dh from DetectionHistory dh where dh.member = :member")
    List<DetectionHistory> findHistoryByMember(@Param("member") Member member);

    // 통계 : 특정 일자 벌레 발견 통계 조회
    @Query("select count(dh) from DetectionHistory dh where dh.bug.id = :bugId and dh.detectedAt = :date")
    Long countDetectedBugByLocalDate(@Param("bugId") Long id, @Param("date") LocalDateTime date);

}