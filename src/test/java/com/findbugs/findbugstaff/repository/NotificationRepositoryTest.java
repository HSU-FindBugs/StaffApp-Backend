package com.findbugs.findbugstaff.repository;

import com.findbugs.findbugstaff.domain.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NotificationRepositoryTest {

    @Autowired
    NotificationRepository notificationRepository;

    @BeforeEach
    void setUp() {
        List<Notification> notifications = IntStream.range(0, 10)
                .mapToObj(i -> Notification.builder()
                        .title("공지" + (i + 1))
                        .content((i + 1) + "번째 박종범이 울부 짖었다")
                        .build())
                .collect(Collectors.toList());

        notificationRepository.saveAll(notifications);
    }

    @Test
    @DisplayName("최근_공지사항_10개_조회")
    void givenNotifications_whenFindRecentNotifications_thenReturnRecentNotifications() {
        // Given
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));

        // When
        Slice<Notification> recentNotifications = notificationRepository.findRecentNotifications(pageable);

        // Then
        assertThat(recentNotifications).isNotNull();
        assertThat(recentNotifications.getContent()).hasSize(10);
    }

}
