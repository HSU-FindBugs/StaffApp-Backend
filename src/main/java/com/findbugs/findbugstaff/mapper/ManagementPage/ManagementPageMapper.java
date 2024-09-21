package com.findbugs.findbugstaff.mapper.ManagementPage;

import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.dto.Member.ManagementPageMemberDto;
import com.findbugs.findbugstaff.dto.Member.ManagementPageRecentSearchResponseDto;
import com.findbugs.findbugstaff.dto.Member.ManagementPageResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Component
public class ManagementPageMapper {

    public ManagementPageResponseDto toManagementPageResponseDto(List<Member> memberList){

        boolean isSearched = !memberList.isEmpty();

        CopyOnWriteArrayList<ManagementPageMemberDto> memberDtoList = memberList.stream()
                .map(this::toManagementPageMemberDto)  // Member -> ManagementPageMemberDto 변환
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

        return ManagementPageResponseDto.builder()
                .managementPageMemberDtoList(memberDtoList)
                .isSearched(isSearched)
                .build();
    }

    public ManagementPageMemberDto toManagementPageMemberDto(Member member){

        return ManagementPageMemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .address(member.getAddress())
                .phoneNumber(member.getPhoneNumber())
                .recentVisit(member.getRecentVisit())
                .build();
    }

    public ManagementPageRecentSearchResponseDto toManagementPageRecentSearchResponseDto(List<String> memberList){
        return ManagementPageRecentSearchResponseDto.builder()
                .recentSearchResults(memberList)
                .build();
    }


}
