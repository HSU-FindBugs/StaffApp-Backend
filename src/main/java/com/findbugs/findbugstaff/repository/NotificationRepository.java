package com.findbugs.findbugstaff.repository;


import com.findbugs.findbugstaff.domain.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {


    /**
     *  최신 10개 공지사항 조회
     *  PageRequest pageRequest = PageRequest.of(n, n + 10, Sort.by(Sort.Direction.DESC, "createAt"))
     */
    @Query("select n from Notification n")
    Slice<Notification> findRecentNotifications(Pageable pageable);

}
