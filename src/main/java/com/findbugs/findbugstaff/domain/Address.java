package com.findbugs.findbugstaff.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Embeddable
@NoArgsConstructor
@SuperBuilder
@Jacksonized
@Getter
public class Address {
    @NotEmpty
    private String region_1depth;   // 지역명 1
    @NotEmpty
    private String region_2depth;   // 지역명 2
    @NotEmpty
    private String region_3depth;   // 지역명 3
    @NotEmpty
    private String street_name;     // 도로명
    @NotEmpty
    private String detail_address;  // 세부주소
}
