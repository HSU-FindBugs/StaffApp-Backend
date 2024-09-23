package com.findbugs.findbugstaff.repository;

import com.findbugs.findbugstaff.domain.Camera;
import com.findbugs.findbugstaff.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface CameraRepository extends JpaRepository<Camera, Long> {

    boolean existsByNameOrSerialId(String name, String serialId);

    List<Camera> findAllByMember(Member member);

    // serial_id로 카메라를 찾는 쿼리 추가
    @Query("SELECT c FROM Camera c WHERE c.serialId = :serialId")
    Camera findBySerialId(@Param("serialId") String serialId);
}
