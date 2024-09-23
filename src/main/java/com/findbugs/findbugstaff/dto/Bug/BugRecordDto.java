package com.findbugs.findbugstaff.dto.Bug;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class BugRecordDto {
    public String bugName;

    public String bugDescription;

    public Long cameraId;


    public LocalDateTime detectionDateAndTime;

    public BugDetailDto bugDetailDto;

    public BugSolutionDto bugSolutionDto;

}
