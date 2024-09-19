package com.findbugs.findbugstaff.dto.Bug;

import com.findbugs.findbugstaff.domain.Address;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
@Data
@SuperBuilder
public class BugDetectionAlertDto {

    public String name;

    public Address address;

    public LocalDateTime recentFindTime;
}
