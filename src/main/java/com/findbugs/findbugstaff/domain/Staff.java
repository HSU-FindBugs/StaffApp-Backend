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

    private String profileUrl;

    private String phoneNumber;

    @Embedded
    private Address territory;

    public String getStringAddress(){
        Address territory = this.getTerritory();

        StringBuilder result = new StringBuilder();

        appendIfNotEmpty(result, territory.getRegion_1depth());
        appendIfNotEmpty(result, territory.getRegion_2depth());
        appendIfNotEmpty(result, territory.getRegion_3depth());

        return result.toString().trim();
    }

    public void appendIfNotEmpty(StringBuilder builder, String value) {
        if (value != null && !value.isEmpty()) {
            if (!builder.isEmpty()) {
                builder.append(" ");
            }
            builder.append(value);
        }
    }

}
