package com.findbugs.findbugstaff.dto.Bug;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

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
    private String bugName;

    @JsonCreator
    public BugDetectionRequestDto(
            @JsonProperty("memberId") Long memberId,
            @JsonProperty("recentFindTime") LocalDateTime recentFindTime,
            @JsonProperty("bugName") String bugName) {
        this.memberId = memberId;
        this.recentFindTime = recentFindTime;
        this.bugName = bugName;
    }
}
