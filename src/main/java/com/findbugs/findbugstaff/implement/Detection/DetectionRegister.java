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
        // 멤버와 연관된 staff 찾기
        Member member = memberRepository.findById(bugDetectionRequestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Staff staff = member.getManager();

        // 감지 이벤트 발생 -> staff에게 알림 전송
        SseEmitter emitter = sseEmitters.getEmitter(staff.getId());
        if (emitter != null) {
            try {
                BugDetectionAlertDto bugDetectionAlertDto = BugDetectionAlertDto.builder().name(member.getName())
                        .address(member.getAddress()).recentFindTime(bugDetectionRequestDto.getRecentFindTime()).build();

                emitter.send(SseEmitter.event().name("bug-detected").data(bugDetectionAlertDto));
            } catch (IOException e) {
                emitter.complete();
            }
        }
    }
}
