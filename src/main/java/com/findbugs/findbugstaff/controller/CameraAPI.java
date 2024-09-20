package com.findbugs.findbugstaff.controller;


import com.findbugs.findbugstaff.domain.Camera;
import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.dto.camera.CameraRegisterRequestDto;
import com.findbugs.findbugstaff.dto.camera.CameraSaveResponseDto;
import com.findbugs.findbugstaff.implement.Camera.CameraFinder;
import com.findbugs.findbugstaff.implement.Camera.CameraRegister;
import com.findbugs.findbugstaff.implement.MemberFinder;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CameraAPI {

    private final MemberFinder memberFinder;
    private final CameraRegister cameraRegister;
    private final CameraFinder cameraFinder;

    @PostMapping("camera")
    public ResponseEntity<CameraSaveResponseDto> register(
            @ModelAttribute CameraRegisterRequestDto cameraRegisterRequestDto
            ){
        Member member = memberFinder.getById(cameraRegisterRequestDto.getMemberId());

        // 중복 체크
        if(cameraFinder.existsByNameOrSerialId(
                cameraRegisterRequestDto.cameraName,
                cameraRegisterRequestDto.cameraSerialId
        )){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    CameraSaveResponseDto.builder()
                            .isSaved(false)
                            .build()
            );
        }

        Camera newCamera = cameraRegister.register(
                member,
                cameraRegisterRequestDto.cameraName,
                cameraRegisterRequestDto.cameraSerialId
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
                CameraSaveResponseDto.builder()
                        .isSaved(true)
                        .build()
        );
    }

    public void get(){

    }

    public void delete(){

    }


}
