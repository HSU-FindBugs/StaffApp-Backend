package com.findbugs.findbugstaff.service;

import com.findbugs.findbugstaff.domain.Notification;
import com.findbugs.findbugstaff.implement.NotificationFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class NotificationServiceTest {

    @Mock
    NotificationFinder notificationFinder;

    @InjectMocks
    NotificationService notificationService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("최신_공지사항_조회_서비스")
    void givenStartIndex_whenFindRecentNotification_thenReturnList(){
        //given
        when(notificationFinder.findRecentNotifications(0)).thenReturn(new ArrayList<Notification>(Collections.nCopies(10, null)));

        //when
        List<Notification> notifications = notificationService.getNotifications(0);

        //then
        assertThat(notifications.size()).isEqualTo(10L);
    }

}