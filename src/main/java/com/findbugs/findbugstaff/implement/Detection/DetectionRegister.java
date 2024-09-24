package com.findbugs.findbugstaff.implement.Detection;

import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.domain.Staff;
import com.findbugs.findbugstaff.dto.Bug.BugDetectionAlertDto;
import com.findbugs.findbugstaff.dto.Bug.BugDetectionRequestDto;
import com.findbugs.findbugstaff.repository.MemberRepository;
import com.findbugs.findbugstaff.sse.SseEmitters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DetectionRegister {
    private final SseEmitters sseEmitters;
    private final MemberRepository memberRepository;

    public void bugDetection(BugDetectionRequestDto bugDetectionRequestDto) {
        System.out.println("MemberId: " + bugDetectionRequestDto.getMemberId());
        System.out.println("RecentFindTime: " + bugDetectionRequestDto.getRecentFindTime());


        // 요청 DTO 검증
        if (bugDetectionRequestDto.getMemberId() == null || bugDetectionRequestDto.getRecentFindTime() == null) {
            throw new IllegalArgumentException("잘못된 감지 요청 데이터");
        }

        // 버그 감지와 관련된 멤버 찾기
        Member member = memberRepository.findById(bugDetectionRequestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없습니다"));

        Staff staff = member.getManager();
        if (staff == null) {
            System.out.println("멤버에 해당하는 스태프가 존재하지 않습니다.");
            return;
        }

        // 감지 이벤트 발생 및 직원에게 알림 전송
        SseEmitter emitter = sseEmitters.getEmitter(staff.getId());
        if (emitter != null) {
            try {
                BugDetectionAlertDto bugDetectionAlertDto = BugDetectionAlertDto.builder()
                        .name(member.getName())
                        .address(member.getAddress())
                        .recentFindTime(bugDetectionRequestDto.getRecentFindTime().toString())
                        .build();
                System.out.println("sse이벤트 정상 발생"+bugDetectionAlertDto.toString());
                emitter.send(SseEmitter.event().name("bug-detected").data(bugDetectionAlertDto));
            } catch (IOException e) {
                // 예외 로그 남기기
                System.err.println("SSE 전송 오류: " + e.getMessage());
                emitter.complete(); // 문제가 발생할 경우 emitter를 완료
            }
        }
    }
}

