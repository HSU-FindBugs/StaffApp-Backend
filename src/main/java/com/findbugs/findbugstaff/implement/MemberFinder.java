package com.findbugs.findbugstaff.implement;


import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberFinder {

    private final MemberRepository memberRepository;

    // 남은 멤버쉽 일정을 반환한다.
    public Long getRemainingMembershipDays(Long id){
        Member member = memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Member with id " + id + " not found"));

        LocalDate expireDate = member.getExpiresAt().toLocalDate();
        LocalDate today = LocalDate.now();

        return ChronoUnit.DAYS.between(today, expireDate);
    }

    // 사용자 객체를 반환한다.
    public Member getById(Long id){
        return memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Member with id " + id + " not found"));
    }

}
