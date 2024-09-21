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
public class DetectionHistoryDto {

    Long id;
    String detectionImgUrl;
    String camera;
    String name;
    String date;
    String time;
}
