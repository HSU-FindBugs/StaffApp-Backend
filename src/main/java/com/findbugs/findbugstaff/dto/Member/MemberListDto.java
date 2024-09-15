package com.findbugs.findbugstaff.dto.Member;

import com.findbugs.findbugstaff.domain.Address;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class MemberListDto {
    public String name;

    public Address address;

    public LocalDateTime recentVisit;
}
