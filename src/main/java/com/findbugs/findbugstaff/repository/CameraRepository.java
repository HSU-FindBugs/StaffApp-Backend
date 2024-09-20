package com.findbugs.findbugstaff.repository;

import com.findbugs.findbugstaff.domain.Camera;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CameraRepository extends JpaRepository<Camera, Long> {

    boolean existsByNameOrSerialId(String name, String serialId);
}
