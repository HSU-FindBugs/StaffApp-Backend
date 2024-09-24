package com.findbugs.findbugstaff.mapper;

import com.findbugs.findbugstaff.domain.Notification;
import com.findbugs.findbugstaff.dto.NotificationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Mapping(source = "profileUrl", target = "profileUrl")
    @Mapping(source = "title", target = "profileUrl")
    @Mapping(source = "content", target = "profileUrl")
    @Mapping(source = "createdAt", target = "time", qualifiedByName = "getRemainTime")
    NotificationDto toNotificationDto(Notification notification);


    @Named("getRemainTime")
    default String getRemainTime(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdAt, now);
        long hours = duration.toHours();

        if (hours < 24) {
            return hours + "H ago";  // 24시간 이내일 경우
        } else {
            return "24+ ago";        // 24시간이 넘었을 경우
        }
    }


    List<NotificationDto> toNotificationDtoList(List<Notification> notifications);
}
