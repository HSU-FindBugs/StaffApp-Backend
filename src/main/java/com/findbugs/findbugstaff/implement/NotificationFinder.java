package com.findbugs.findbugstaff.implement;

import com.findbugs.findbugstaff.domain.Notification;
import com.findbugs.findbugstaff.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationFinder {

    private final NotificationRepository notificationRepository;

    /**
     *  최근 10개 공지사항 조회
     * @param startIndex 시작인덱스 (ex 10 -> 10부터 19 페이지 반환)
     */
    public List<Notification> findRecentNotifications(int startIndex) {
        Pageable pageable = PageRequest.of(startIndex, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        return notificationRepository.findRecentNotifications(pageable).getContent();
    }

}
