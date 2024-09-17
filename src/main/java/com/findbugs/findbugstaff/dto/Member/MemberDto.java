package com.findbugs.findbugstaff.dto.Member;

import com.findbugs.findbugstaff.domain.Address;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class MemberDto {
    public Long id;
    public String name;
    public Address address;
    public LocalDateTime recentVisit;
    public String phoneNumber;
}
