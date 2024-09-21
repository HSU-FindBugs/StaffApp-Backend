package com.findbugs.findbugstaff.dto.ManagementProfilePage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@SuperBuilder
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DetectionHistoryResponseDto {
    List<DetectionHistoryDto> detectionHistoryDtoList;
}
