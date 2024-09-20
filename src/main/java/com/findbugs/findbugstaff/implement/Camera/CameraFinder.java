package com.findbugs.findbugstaff.implement.Camera;

import com.findbugs.findbugstaff.repository.CameraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CameraFinder {

    private final CameraRepository cameraRepository;

    public boolean existsByNameOrSerialId(String name, String serialId) {
        return cameraRepository.existsByNameOrSerialId(name, serialId);
    }
}
