package com.findbugs.findbugstaff.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class Bug {

    @Id
    @GeneratedValue
    @Column(name = "bug_id")
    private Long id;

    private String name;

    private String description;

    /*
    벌레 상세 정보는 NoSQL을 통해 관리
     */
}
