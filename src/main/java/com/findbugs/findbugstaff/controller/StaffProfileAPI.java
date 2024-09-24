package com.findbugs.findbugstaff.controller;

import com.findbugs.findbugstaff.controller.swagger.StaffProfileSwaggerInfo;
import com.findbugs.findbugstaff.dto.ProfileResponseDto;
import com.findbugs.findbugstaff.implement.StaffFinder;
import com.findbugs.findbugstaff.implement.StaffReader;
import com.findbugs.findbugstaff.mapper.StaffProfileMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StaffProfileAPI implements StaffProfileSwaggerInfo {

    private final StaffReader staffReader;
    private final StaffProfileMapper staffProfileMapper;

    /**
     * 직원 프로필 조회
     * @param staffId 직원 아이디
     * @return ProfileResponseDto 직원 아이디 반환
     */
    @Override
    @GetMapping("profile/{staff_id}")
    public ResponseEntity<ProfileResponseDto> getMainPage(
            @PathVariable("staff_id") Long staffId
    ) {
        return ResponseEntity.ok(staffProfileMapper.toProfileResponseDto(staffReader.getById(staffId)));
    }

}

