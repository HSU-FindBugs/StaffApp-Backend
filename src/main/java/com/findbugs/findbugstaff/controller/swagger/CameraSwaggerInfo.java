package com.findbugs.findbugstaff.controller.swagger;

import com.findbugs.findbugstaff.dto.camera.CameraListResponseDto;
import com.findbugs.findbugstaff.dto.camera.CameraRegisterRequestDto;
import com.findbugs.findbugstaff.dto.camera.CameraSaveResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "카메라 관리", description = "카메라 등록 및 조회 API")
public interface CameraSwaggerInfo {

    @Operation(summary = "카메라 등록", description = "카메라 정보를 등록합니다.")
    @PostMapping("camera")
    ResponseEntity<CameraSaveResponseDto> register(
            @ModelAttribute CameraRegisterRequestDto cameraRegisterRequestDto
    );

    @Operation(summary = "카메라 조회", description = "사용자 ID로 해당 사용자의 모든 카메라 정보를 조회합니다.")
    @GetMapping("camera/{member_id}")
    ResponseEntity<CameraListResponseDto> get(
            @PathVariable("member_id") Long memberId
    );
}