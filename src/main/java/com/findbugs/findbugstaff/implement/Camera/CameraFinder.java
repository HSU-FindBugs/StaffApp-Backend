package com.findbugs.findbugstaff.implement.Camera;

import com.findbugs.findbugstaff.domain.Camera;
import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.repository.CameraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CameraFinder {

    private final CameraRepository cameraRepository;

    public boolean existsByNameOrSerialId(String name, String serialId) {
        return cameraRepository.existsByNameOrSerialId(name, serialId);
    }

    public List<Camera> findAllByMember(Member member) {
        return cameraRepository.findAllByMember(member);
    }

    public Camera findByCameraSerialNum(String cameraSerialNumber){
        return cameraRepository.findBySerialId(cameraSerialNumber);
    }


}
