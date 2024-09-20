package com.findbugs.findbugstaff.repository;

import com.findbugs.findbugstaff.domain.Camera;
import com.findbugs.findbugstaff.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CameraRepository extends JpaRepository<Camera, Long> {

    boolean existsByNameOrSerialId(String name, String serialId);

    List<Camera> findAllByMember(Member member);
}
