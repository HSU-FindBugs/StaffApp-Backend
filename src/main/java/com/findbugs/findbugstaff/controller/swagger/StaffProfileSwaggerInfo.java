package com.findbugs.findbugstaff.controller.swagger;

import com.findbugs.findbugstaff.dto.ProfileResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "프로필", description = "프로필 확인 API")
public interface StaffProfileSwaggerInfo {

    @Operation(summary = "직원 프로필 조회", description = "특정 매니저 ID로 직원 정보를 조회합니다.")
    @GetMapping("profile/{staff_id}")
    ResponseEntity<ProfileResponseDto> getMainPage(@PathVariable("staff_id") Long staffId);

}