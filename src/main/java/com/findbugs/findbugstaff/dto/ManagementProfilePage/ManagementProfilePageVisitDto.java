package com.findbugs.findbugstaff.dto.ManagementProfilePage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ManagementProfilePageVisitDto {
    public Long visitId;
    public String title;
    public String visitedAt;
    public String visitComment;
    public String detectedImgUrl;
}
