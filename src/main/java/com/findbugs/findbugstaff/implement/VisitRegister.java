package com.findbugs.findbugstaff.implement;

import com.findbugs.findbugstaff.domain.DetectionHistory;
import com.findbugs.findbugstaff.domain.Visit;
import com.findbugs.findbugstaff.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Slf4j
@Component
@RequiredArgsConstructor
public class VisitRegister {

    private final VisitRepository visitRepository;

    private final StaffReader staffReader;
    private final MemberFinder memberFinder;
    private final DetectionHistoryFinder detectionHistoryFinder;

    @Transactional
    public boolean register(Long staffId, Long memberId) {

        Visit visit = Visit.builder()
                .visitPurpose("실시간 방문")
                .visitedAt(LocalDateTime.now())
                .visitComment(setComment())
                .manager(staffReader.getById(staffId))
                .member(memberFinder.getById(memberId))
                .build();

        visitRepository.save(visit);

        List<DetectionHistory> unDetectionHistories = detectionHistoryFinder.findUnVisited(memberId);

        for(DetectionHistory history : unDetectionHistories) {
            history.setVisit(visit);
        }

        return true;
    }

    // 랜덤으로 방문 코멘트가 설정된다 - 기획에 의해 변경될 여지가 다수 있음
    // 이후 기획에 맞춰 해당 기능 고도화 할 것
    public String setComment(){

        List<String> comments = new ArrayList<>();

        comments.add("현장 조사 및 평가 수행");
        comments.add("약제 분사 및 젤 타입 살충제 사용");
        comments.add("트랩 및 유인제 설치");
        comments.add("구멍 및 틈새 봉합");
        comments.add("흡입 및 수동 제거");
        comments.add("배수구 및 하수구 점검");

        Random random = new Random();

        int firstIndex = random.nextInt(comments.size());
        int secondIndex;
        do {
            secondIndex = random.nextInt(comments.size());
        } while (firstIndex == secondIndex);

        return comments.get(firstIndex) + " + " + comments.get(secondIndex);


    }

}
