package com.findbugs.findbugstaff.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@Getter
public class Visit extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "visit_id")
    private Long id;

    private LocalDateTime visitedAt;

    /**
     * 정기 점검, 방역 후 점검, 긴급 요청 방역
     */
    private String visitPurpose;

    private String visitComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;



}
