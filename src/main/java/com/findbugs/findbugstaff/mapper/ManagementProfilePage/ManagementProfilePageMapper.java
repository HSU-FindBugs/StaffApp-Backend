package com.findbugs.findbugstaff.mapper.ManagementProfilePage;

import com.findbugs.findbugstaff.domain.DetectionHistory;
import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.domain.Visit;
import com.findbugs.findbugstaff.dto.ManagementProfilePage.ManagementProfilePageMemberDto;
import com.findbugs.findbugstaff.dto.ManagementProfilePage.ManagementProfilePageVisitDto;
import com.findbugs.findbugstaff.dto.ManagementProfilePage.ManagementProfileResponseDto;
import org.springframework.stereotype.Component;

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
                .profileUrl(member.getProfileUrl())
                .visitStatus(visitStatus)
                .remainingDays(remainingDays)
                .build();
    }

    public ManagementProfilePageVisitDto toManagementPageVisitDto(
            DetectionHistory recentVisitHistory
    ){
        Visit visit = recentVisitHistory.getVisit();
        return ManagementProfilePageVisitDto.builder()
                .visitId(visit.getId())
                .visitComment(visit.getVisitComment())
                .visitedAt(visit.getVisitedAt().toString())
                .detectedImgUrl(recentVisitHistory.getImageUrl())
                .build();
    }
}
