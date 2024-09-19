package com.findbugs.findbugstaff.domain;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@Getter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String profileUrl;
    private String name;
    private String email;
    private String phoneNumber;
    private String note;

    @Embedded
    private Address address;
    
    // 멤버쉽은 x개월 단위
    private Long membership;

    // 방문 일자 업데이트
    private LocalDateTime recentVisit;

    private LocalDateTime registerAt;

    private LocalDateTime expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff manager;

    // 이름 업데이트 메서드
    public void updateName(String newName) {
        this.name = newName;
    }

    // 전화번호 업데이트 메서드
    public void updatePhoneNumber(String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }

    // 주소 업데이트 메서드
    public void updateAddress(Address newAddress) {
        this.address = newAddress;
    }

    public void updateNote(String newNote){
        this.note = newNote;
    }

}
