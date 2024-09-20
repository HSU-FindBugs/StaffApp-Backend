package com.findbugs.findbugstaff.controller;


import com.findbugs.findbugstaff.controller.swagger.CameraSwaggerInfo;
import com.findbugs.findbugstaff.domain.Camera;
import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.dto.camera.CameraListResponseDto;
import com.findbugs.findbugstaff.dto.camera.CameraRegisterRequestDto;
import com.findbugs.findbugstaff.dto.camera.CameraSaveResponseDto;
import com.findbugs.findbugstaff.implement.Camera.CameraFinder;
import com.findbugs.findbugstaff.implement.Camera.CameraRegister;
import com.findbugs.findbugstaff.implement.MemberFinder;
import com.findbugs.findbugstaff.mapper.camera.CameraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CameraAPI implements CameraSwaggerInfo {

    private final MemberFinder memberFinder;
    private final CameraRegister cameraRegister;
    private final CameraFinder cameraFinder;

    private final CameraMapper cameraMapper;

    /**
     * 카메라 등록을 위한 API
     * @param cameraRegisterRequestDto 카메라 등록 정보
     * @return CameraSaveResponseDto 카메라 등록 여부 (true/false)
     */
    @Override
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

    /**
     * 카메라 조회 - 리스트 형식 반환
     * @param memberId 사용자 ID
     * @return CameraListResponseDto 카메라 리스트 형식 반환
     */
    @Override
    @GetMapping("camera/{member_id}")
    public ResponseEntity<CameraListResponseDto> get(
            @PathVariable("member_id") Long memberId
    ){
        List<Camera> cameraList = cameraFinder.findAllByMember(memberFinder.getById(memberId));
        CameraListResponseDto responseDto = cameraMapper.toCameraListResponseDto(cameraList);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
