package com.findbugs.findbugstaff.dto.Bug;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class BugDetectionRequestDto {
    private Long memberId;
    private LocalDateTime recentFindTime;

    @JsonCreator
    public BugDetectionRequestDto(
            @JsonProperty("memberId") Long memberId,
            @JsonProperty("recentFindTime") LocalDateTime recentFindTime) {
        this.memberId = memberId;
        this.recentFindTime = recentFindTime;
    }
}
