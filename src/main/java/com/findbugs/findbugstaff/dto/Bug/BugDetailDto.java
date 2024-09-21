package com.findbugs.findbugstaff.dto.Bug;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class BugDetailDto {

    public String appearance;

    public String inhabitation;

    public String quarantine;

}
