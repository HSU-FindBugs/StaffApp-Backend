package com.findbugs.findbugstaff.mapper;

import com.findbugs.findbugstaff.domain.Address;
import com.findbugs.findbugstaff.domain.Bug;
import com.findbugs.findbugstaff.domain.Notification;
import com.findbugs.findbugstaff.domain.Staff;
import com.findbugs.findbugstaff.dto.MainPage.MainPageResponseDto;
import com.findbugs.findbugstaff.dto.NotificationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = NotificationMapper.class)
public interface MainPageMapper {

    MainPageMapper INSTANCE = Mappers.getMapper(MainPageMapper.class);

    @Mapping(source = "staff.profileUrl", target = "staffProfileUrl")
    @Mapping(source = "staff", target = "staffName", qualifiedByName = "concatNameAndPosition")
    @Mapping(source = "staff", target = "staffTerritory", qualifiedByName = "concatRegion")
    @Mapping(source = "countManagedMember", target = "countMemberManagedByStaff")
    @Mapping(source = "bug.name", target = "bugName")
    @Mapping(source = "bug.description", target = "bugDescription")
    @Mapping(source = "countDetectedBugs", target = "bugStats")
    @Mapping(source = "notifications", target = "notificationDtoList")
    @Mapping(source = "bug.bugImageUrl", target = "bugProfileImg")
    MainPageResponseDto toMainPageResponseDto(
            Staff staff,
            Long countManagedMember,    // ex) 총 37명을 관리하고 있습니다
            Bug bug,
            String countDetectedBugs,   // ex) 어제 찾아벌레는 72마리의 바퀴벌레가 감지되었습니다.
            List<Notification> notifications
    );

    @Named("concatNameAndPosition")
    default String concatNameAndPosition(Staff staff){
        return staff.getName() + " " + staff.getPosition();
    }

    @Named("concatRegion")
    default String concatRegion(Staff staff){
        Address territory = staff.getTerritory();

        StringBuilder result = new StringBuilder();

        appendIfNotEmpty(result, territory.getRegion_1depth());
        appendIfNotEmpty(result, territory.getRegion_2depth());
        appendIfNotEmpty(result, territory.getRegion_3depth());

        return result.toString().trim();
    }

    default void appendIfNotEmpty(StringBuilder builder, String value) {
        if (value != null && !value.isEmpty()) {
            if (!builder.isEmpty()) {
                builder.append(" ");
            }
            builder.append(value);
        }
    }
}
