package com.findbugs.findbugstaff.mapper;

import com.findbugs.findbugstaff.domain.Staff;
import com.findbugs.findbugstaff.dto.ProfileResponseDto;
import org.springframework.stereotype.Component;

@Component
public class StaffProfileMapper {

    public ProfileResponseDto toProfileResponseDto(Staff staff) {
        return ProfileResponseDto.builder()
                .name(staff.getName())
                .profileUrl(staff.getProfileUrl())
                .address(staff.getStringAddress())
                .build();
    }

}
