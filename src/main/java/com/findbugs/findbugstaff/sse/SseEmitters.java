package com.findbugs.findbugstaff.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ConcurrentHashMap;
@Component
@Slf4j
public class SseEmitters {

    // Staff ID를 키로 하고, 해당 ID에 연결된 단일 emitter를 값으로 갖는 맵
    private final ConcurrentHashMap<Long, SseEmitter> emittersMap = new ConcurrentHashMap<>();

    public SseEmitter add(Long staffId, SseEmitter emitter) {
        // 기존의 emitter가 있으면 제거
        SseEmitter existingEmitter = emittersMap.put(staffId, emitter);
        if (existingEmitter != null) {
            existingEmitter.complete(); // 기존 emitter를 완료하여 리소스를 정리
            log.info("Existing emitter removed for staff ID {}: {}", staffId, existingEmitter);
        }

        SseEmitter newEmitter = new SseEmitter(2 * 24 * 60 * 60 * 1000L); // 2일

        log.info("New emitter added for staff ID {}: {}", staffId, newEmitter);
        newEmitter.onCompletion(() -> {
            log.info("onCompletion callback for staff ID {}", staffId);
            emittersMap.remove(staffId); // 만료되면 리스트에서 삭제
        });
        newEmitter.onTimeout(() -> {
            log.info("onTimeout callback for staff ID {}", staffId);
            newEmitter.complete();
        });

        return newEmitter; // 새로운 emitter를 반환
    }



    // 특정 staff ID에 해당하는 emitter 반환
    public SseEmitter getEmitter(Long staffId) {
        return emittersMap.get(staffId);
    }

    // 특정 staff ID의 emitter를 제거하는 메서드 (필요 시 사용)
    public void removeEmitter(Long staffId) {
        emittersMap.remove(staffId);
    }
}
