package com.findbugs.findbugstaff.service;

import com.findbugs.findbugstaff.domain.Camera;
import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.dto.camera.CameraRegisterRequestDto;
import com.findbugs.findbugstaff.implement.Camera.CameraFinder;
import com.findbugs.findbugstaff.implement.Camera.CameraRegister;
import com.findbugs.findbugstaff.implement.MemberFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CameraService {

    private final MemberFinder memberFinder;
    private final CameraFinder cameraFinder;

    private final CameraRegister cameraRegister;

    public boolean register(CameraRegisterRequestDto cameraRegisterRequestDto) {
        Member member = memberFinder.getById(cameraRegisterRequestDto.getMemberId());

        // 중복 체크
        if (cameraFinder.existsByNameOrSerialId(
                cameraRegisterRequestDto.cameraName,
                cameraRegisterRequestDto.cameraSerialId
        )) {
            return false;
        }

        Camera newCamera = cameraRegister.register(
                member,
                cameraRegisterRequestDto.cameraName,
                cameraRegisterRequestDto.cameraSerialId
        );

        return true;
    }

    public List<Camera> findAll(Long memberId){
        return cameraFinder.findAllByMember(memberFinder.getById(memberId));
    }

}
