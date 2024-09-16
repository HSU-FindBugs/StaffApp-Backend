package com.findbugs.findbugstaff.service;

import com.findbugs.findbugstaff.domain.Notification;
import com.findbugs.findbugstaff.implement.NotificationFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationFinder notificationFinder;

    public List<Notification> getNotifications(int startIndex) {
        return notificationFinder.findRecentNotifications(startIndex);
    }
}
