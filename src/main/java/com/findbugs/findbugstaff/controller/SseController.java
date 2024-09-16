package com.findbugs.findbugstaff.controller;

import com.findbugs.findbugstaff.sse.SseEmitters;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/detection-events")
public class SseController {
    private final SseEmitters sseEmitters;

    @GetMapping(value = "/connect/{staffId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect(@PathVariable Long staffId) {
        SseEmitter emitter = new SseEmitter();
        sseEmitters.add(staffId, emitter); // staffId와 함께 emitter 추가

        try {
            emitter.send(SseEmitter.event()
                    .name("스태프")
                    .data("연결!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(emitter);
    }
}
