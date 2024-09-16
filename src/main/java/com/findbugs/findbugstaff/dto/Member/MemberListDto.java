package com.findbugs.findbugstaff.dto.Member;

import com.findbugs.findbugstaff.domain.Address;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
@Data
@SuperBuilder
public class MemberListDto {
    public String name;

    public Address address;

    public LocalDateTime recentVisit;
}
