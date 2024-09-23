package com.findbugs.findbugstaff.implement.Detection;

import com.findbugs.findbugstaff.domain.DetectionHistory;
import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.domain.Staff;
import com.findbugs.findbugstaff.domain.Visit;
import com.findbugs.findbugstaff.dto.Bug.BugDetectionAlertDto;
import com.findbugs.findbugstaff.dto.Bug.BugDetectionRequestDto;
import com.findbugs.findbugstaff.repository.*;
import com.findbugs.findbugstaff.sse.SseEmitters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DetectionRegister {
    private final SseEmitters sseEmitters;
    private final MemberRepository memberRepository;
    private final DetectionHistoryRepository detectionHistoryRepository;
    private final BugRepository bugRepository;
    private final VisitRepository visitRepository;
    private final CameraRepository cameraRepository;
    public void bugDetection(BugDetectionRequestDto bugDetectionRequestDto) {
        // 멤버와 연관된 staff 찾기
        Member member = memberRepository.findById(bugDetectionRequestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Staff staff = member.getManager();

        // 오늘의 방문 기록 확인
        LocalDate today = LocalDate.now();
        Visit visit;
        Boolean isVisited = visitRepository.existsByIdAndLocalDate(member.getId(), staff.getId(), today);

        // 방문 기록이 없다면 새로운 방문 기록 생성 및 저장
        if (!isVisited) {
            visit = Visit.builder().member(member).manager(staff).visitedAt(LocalDateTime.now()).build();
            visitRepository.save(visit);
        } else {
            visit = visitRepository.findByMemberIdAndStaffIdAndLocalDate(member.getId(), staff.getId(), today);
        }

        // 감지 이벤트 발생 -> staff에게 알림 전송
        SseEmitter emitter = sseEmitters.getEmitter(staff.getId());
        DetectionHistory saveDetection = DetectionHistory.builder()
                .bug(bugRepository.findByName(bugDetectionRequestDto.getBugName())
                        .orElseThrow(() -> new IllegalArgumentException("Bug not found")))
                .detectedAt(LocalDateTime.now())
                .member(member)
                .visit(null)
                .camera(cameraRepository.findById(1L).get())
                .build();

        detectionHistoryRepository.save(saveDetection);

        if (emitter != null) {
            try {
                BugDetectionAlertDto bugDetectionAlertDto = BugDetectionAlertDto.builder()
                        .name(member.getName())
                        .address(member.getAddress())
                        .recentFindTime(bugDetectionRequestDto.getRecentFindTime())
                        .build();

                emitter.send(SseEmitter.event().name("bug-detected").data(bugDetectionAlertDto));
            } catch (IOException e) {
                emitter.complete();
            }
        }
    }



}
