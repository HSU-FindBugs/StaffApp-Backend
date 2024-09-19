package com.findbugs.findbugstaff.dto.Member;

import com.findbugs.findbugstaff.domain.Address;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
@SuperBuilder
public class MemberListDto {
    public CopyOnWriteArrayList<MemberDto> memberLists;
}
