package com.findbugs.findbugstaff.mapper.ManagementProfilePage;

import com.findbugs.findbugstaff.domain.DetectionHistory;
import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.domain.Visit;
import com.findbugs.findbugstaff.dto.ManagementProfilePage.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ManagementProfilePageMapper {

    public ManagementProfileResponseDto toManagementProfileResponseDto(
            Member member, String remainingDays, String visitStatus, DetectionHistory recentVisitHistory
    ){

        ManagementProfilePageMemberDto memberDto = toManagementProfilePageMemberDto(member, visitStatus, remainingDays);
        ManagementProfilePageVisitDto visitDto = toManagementPageVisitDto(recentVisitHistory);

        return ManagementProfileResponseDto.builder()
                .managementProfilePageVisitDto(visitDto)
                .managementProfilePageMemberDto(memberDto)
                .build();
    }

    public ManagementProfilePageMemberDto toManagementProfilePageMemberDto(
            Member member, String visitStatus, String remainingDays
    ){
        return ManagementProfilePageMemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .profileUrl(member.getProfileUrl())
                .visitStatus(visitStatus)
                .remainingDays(remainingDays)
                .memo(member.getNote())
                .build();
    }

    public ManagementProfilePageVisitDto toManagementPageVisitDto(
            DetectionHistory recentVisitHistory
    ){

        Visit visit = recentVisitHistory.getVisit();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd");

        String formattedDate = visit.getVisitedAt().format(formatter);
        String title = formattedDate + " 방문";

        return ManagementProfilePageVisitDto.builder()
                .visitId(visit.getId())
                .title(title)
                .visitComment(visit.getVisitComment())
                .visitedAt(visit.getVisitedAt().toString())
                .detectedImgUrl(recentVisitHistory.getImageUrl())
                .build();
    }


    public DetectionHistoryResponseDto toDetectionHistoryResponseDto(List<DetectionHistory> detectionHistoryList) {

        List<DetectionHistoryDto> detectionHistoryDtoList = detectionHistoryList.stream()
                .map(this::toDetectionHistoryDto)
                .collect(Collectors.toList());

        return DetectionHistoryResponseDto.builder()
                .detectionHistoryDtoList(detectionHistoryDtoList)
                .build();
    }

    public DetectionHistoryDto toDetectionHistoryDto(DetectionHistory detectionHistory){

        LocalDateTime dateTime = detectionHistory.getDetectedAt();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String date = dateTime.format(dateFormatter);
        String time = dateTime.format(timeFormatter);

        return DetectionHistoryDto.builder()
                .id(detectionHistory.getId())
                .detectionImgUrl(detectionHistory.getImageUrl())
                .name(detectionHistory.getBug().getName())
                .camera(detectionHistory.getCamera().getName())
                .date(date)
                .time(time)
                .build();
    }






}
