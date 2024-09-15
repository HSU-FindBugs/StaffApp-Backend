package com.findbugs.findbugstaff.mapper;

import com.findbugs.findbugstaff.domain.Notification;
import com.findbugs.findbugstaff.dto.NotificationDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    NotificationDto toNotificationDto(Notification notification);

    List<NotificationDto> toNotificationDtoList(List<Notification> notifications);
}
