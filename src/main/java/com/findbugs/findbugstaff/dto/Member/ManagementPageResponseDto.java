package com.findbugs.findbugstaff.dto.Member;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.concurrent.CopyOnWriteArrayList;

@Data
@SuperBuilder
public class ManagementPageResponseDto {
    public CopyOnWriteArrayList<ManagementPageMemberDto> managementPageMemberDtoList;
}
