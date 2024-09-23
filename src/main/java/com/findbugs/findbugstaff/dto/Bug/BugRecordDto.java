package com.findbugs.findbugstaff.dto.Bug;

import com.findbugs.findbugstaff.dto.camera.CameraDto;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class BugRecordDto {
    public CameraDto cameraDto;
    public BugDetailDto bugDetailDto;
    public BugSolutionDto bugSolutionDto;
}
