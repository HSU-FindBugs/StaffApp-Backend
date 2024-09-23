package com.findbugs.findbugstaff.mapper;

import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.dto.Member.ManagementPageMemberDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "recentVisit", target = "recentVisit")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "membership", target = "membership")
    ManagementPageMemberDto toMemberDto(Member member);

    // 필요하다면, DTO에서 Entity로 변환하는 메서드도 추가 가능
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "recentVisit", target = "recentVisit")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    Member toMember(ManagementPageMemberDto memberDto);
}
