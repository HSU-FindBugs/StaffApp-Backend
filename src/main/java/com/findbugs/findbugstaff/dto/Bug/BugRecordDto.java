package com.findbugs.findbugstaff.dto.Bug;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@SuperBuilder
public class BugRecordDto {
    public String bugName;

    public String bugDescription;

    public String bugUrl;

    public Long cameraId;

    public LocalDate bugFindDate;

    public LocalTime bugFindTime;
    public BugDetailDto bugDetailDto;

    public BugSolutionDto bugSolutionDto;

}
