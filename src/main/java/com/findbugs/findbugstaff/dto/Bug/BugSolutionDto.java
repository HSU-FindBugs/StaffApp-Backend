package com.findbugs.findbugstaff.dto.Bug;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class BugSolutionDto {
    public String firstSolution;
    public String secondSolution;
    public String thirdSolution;
    public String fourSolution;

}
