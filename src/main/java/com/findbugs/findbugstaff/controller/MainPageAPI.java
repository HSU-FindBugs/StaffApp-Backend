package com.findbugs.findbugstaff.controller;

import com.findbugs.findbugstaff.domain.Bug;
import com.findbugs.findbugstaff.domain.Staff;
import com.findbugs.findbugstaff.dto.MainPage.MainPageResponseDto;
import com.findbugs.findbugstaff.mapper.MainPageMapper;
import com.findbugs.findbugstaff.service.DetectedBugStatsService;
import com.findbugs.findbugstaff.service.NotificationService;
import com.findbugs.findbugstaff.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MainPageAPI {

   private final DetectedBugStatsService detectedBugStatsService;
   private final StaffService staffService;
   private final NotificationService notificationService;

   private final MainPageMapper mainPageMapper;

    @GetMapping("main/{id}")
    public ResponseEntity<MainPageResponseDto> getMainPage(@PathVariable("id") Long id) {

        Staff staff = staffService.getStaff(id);
        Bug bug = detectedBugStatsService.getMostDetectedBug();

        MainPageResponseDto responseDto = mainPageMapper.toMainPageResponseDto(
                staff,
                staffService.getMemberInCharge(id),
                bug,
                detectedBugStatsService.getDetectedBugStatsMessage(bug.getId()),
                notificationService.getNotifications(0)
        );
        
        return ResponseEntity.ok(responseDto);
     }
}
