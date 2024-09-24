package com.findbugs.findbugstaff.dto.MainPage;

import com.findbugs.findbugstaff.dto.NotificationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@SuperBuilder
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MainPageResponseDto {

    // 직원 프로필
    public String staffProfileUrl;
    public String staffName; // 직원 + 포지션 ex) 박종범 컨설턴트
    public String staffTerritory;
    public Long countMemberManagedByStaff;

    // 벌레 정보
    public String bugName;
    public String bugDescription;
    public String bugStats;
    public String bugProfileImg;

    // 공지 사항
    public List<NotificationDto> notificationDtoList;

}
