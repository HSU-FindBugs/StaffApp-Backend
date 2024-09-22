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
public class ManagementProfilePageMemberDto {
    public Long id;
    public String profileUrl;
    public String name;
    public String address;
    public String remainingDays;
    public String visitStatus;

    public String phoneNumber;

    public String memo;
}
