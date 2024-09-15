package com.findbugs.findbugstaff.dto;

import com.findbugs.findbugstaff.domain.Address;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDto {
    public Long id;
    public String displayName;
    public Address address;
    public LocalDateTime recentVisit;
    public String phoneNumber;
}
