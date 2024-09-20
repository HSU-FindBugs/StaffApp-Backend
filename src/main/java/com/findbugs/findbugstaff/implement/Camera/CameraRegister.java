package com.findbugs.findbugstaff.implement.Camera;

import com.findbugs.findbugstaff.domain.Camera;
import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.repository.CameraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CameraRegister {

    private final CameraRepository cameraRepository;

    public Camera register(Member member, String cameraName, String cameraSerialId){
        Camera newCamera = Camera.builder()
                .member(member)
                .name(cameraName)
                .serialId(cameraSerialId)
                .build();

        cameraRepository.save(newCamera);
        return newCamera;
    }
}
