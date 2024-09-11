package com.findbugs.findbugstaff.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@Getter
public class Staff extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "staff_id")
    private Long id;

    private String name;

    private String position;

    private String phoneNumber;

    @Embedded
    private Address territory;

//    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "region_1depth", column=@Column(name="staff_region_1depth")),
//            @AttributeOverride(name = "region_2depth", column=@Column(name="staff_region_2depth")),
//            @AttributeOverride(name = "region_3depth", column=@Column(name="staff_region_3depth")),
//            @AttributeOverride(name = "street_name",   column=@Column(name="staff_street_name")),
//            @AttributeOverride(name = "detail",        column=@Column(name="staff_detail_address"))
//    })
//    private Address address;

}
