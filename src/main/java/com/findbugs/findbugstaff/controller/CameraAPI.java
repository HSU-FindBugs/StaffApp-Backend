package com.findbugs.findbugstaff.controller;


import com.findbugs.findbugstaff.controller.swagger.CameraSwaggerInfo;
import com.findbugs.findbugstaff.domain.Camera;
import com.findbugs.findbugstaff.dto.camera.CameraListResponseDto;
import com.findbugs.findbugstaff.dto.camera.CameraRegisterRequestDto;
import com.findbugs.findbugstaff.dto.camera.CameraSaveResponseDto;
import com.findbugs.findbugstaff.mapper.camera.CameraMapper;
import com.findbugs.findbugstaff.service.CameraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CameraAPI implements CameraSwaggerInfo {

    private final CameraMapper cameraMapper;
    private final CameraService cameraService;

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
        if(cameraService.register(cameraRegisterRequestDto)){
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    CameraSaveResponseDto.builder()
                            .isSaved(true)
                            .build()
            );
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    CameraSaveResponseDto.builder()
                            .isSaved(false)
                            .build()
            );
        }
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
        List<Camera> cameraList = cameraService.findAll(memberId);
        CameraListResponseDto responseDto = cameraMapper.toCameraListResponseDto(cameraList);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
