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

    private String region_1depth;   // 지역명 1

    private String region_2depth;   // 지역명 2

    private String region_3depth;   // 지역명 3

    private String street_name;     // 도로명

    private String detail_address;  // 세부주소
}
