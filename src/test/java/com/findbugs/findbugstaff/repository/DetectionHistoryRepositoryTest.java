package com.findbugs.findbugstaff.repository;

import com.findbugs.findbugstaff.domain.Bug;
import com.findbugs.findbugstaff.domain.DetectionHistory;
import com.findbugs.findbugstaff.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DetectionHistoryRepositoryTest {

    @Autowired
    DetectionHistoryRepository detectionHistoryRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BugRepository bugRepository;

    Member member;
    Bug bug;

    @BeforeEach
    void setUp(){
        member = Member.builder()
                .name("김우현")
                .build();

        memberRepository.save(member);

        bug = Bug.builder()
                .name("바퀴벌레")
                .description("바퀴벌레는 깊은 숲속 옹달샘을 좋아한다.")
                .build();

        bugRepository.save(bug);

        List<DetectionHistory> detectionHistories = new ArrayList<DetectionHistory>();

        for(int i = 0; i < 10; i++){
            DetectionHistory detectionHistory = DetectionHistory.builder()
                    .bug(bug)
                    .detectedAt(LocalDateTime.of(2024, 9, 1 + i, 0, 0))
                    .member(member)
                    .build();
            detectionHistories.add(detectionHistory);
        }

        // 통계 기능을 위해 같은 데이터 주입
        for (int i = 0; i < 5; i++) {
            DetectionHistory detectionHistory = DetectionHistory.builder()
                    .bug(bug)
                    .detectedAt(LocalDateTime.of(2024, 9, 1, 0, 0))
                    .member(member)
                    .build();
            detectionHistories.add(detectionHistory);
        }

        detectionHistoryRepository.saveAll(detectionHistories);
    }

    @Test
    @DisplayName("사용자_ID로_탐지기록_반환")
    void givenMember_whenFindHistoryByMember_thenReturnDetectionHistories(){
        // Given

        // When
        List<DetectionHistory> histories = detectionHistoryRepository.findHistoryByMember(member);

        // Then
        assertThat(histories).isNotEmpty();
        assertThat(histories).hasSize(15); // Expecting 10 records
        assertThat(histories).extracting("bug").containsOnly(bug); // All records should have the same bug
        assertThat(histories).extracting("member").containsOnly(member); // All records should be for the same member
    }

    @Test
    @DisplayName("특정_일자와 _벌레에_대한_발견_수_통계_조회")
    void givenBugAndDate_whenCountDetectedBugByLocalDate_thenReturnCount() {
        // Given
        LocalDateTime date = LocalDateTime.of(2024, 9, 1, 0, 0);
        Long bugId = bug.getId();

        // When
        Long count = detectionHistoryRepository.countDetectedBugByLocalDate(bugId, date);

        // Then
        assertThat(count).isEqualTo(6);
    }

}