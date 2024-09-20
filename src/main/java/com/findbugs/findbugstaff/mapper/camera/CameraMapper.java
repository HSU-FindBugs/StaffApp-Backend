package com.findbugs.findbugstaff.mapper.camera;

import com.findbugs.findbugstaff.domain.Camera;
import com.findbugs.findbugstaff.dto.camera.CameraDto;
import com.findbugs.findbugstaff.dto.camera.CameraListResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CameraMapper {

    public CameraListResponseDto toCameraListResponseDto(List<Camera> cameraList) {
        List<CameraDto> cameraDtoList = cameraList.stream()
                .map(this::toCameraDto)
                .collect(Collectors.toList());

        return CameraListResponseDto.builder()
                .cameraDtoList(cameraDtoList)
                .build();
    }

    public CameraDto toCameraDto(Camera camera) {
        return CameraDto.builder()
                .id(camera.getId())
                .name(camera.getName())
                .serialId(camera.getSerialId())
                .build();
    }

}
