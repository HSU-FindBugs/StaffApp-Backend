package com.findbugs.findbugstaff.implement;

import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.domain.Staff;
import com.findbugs.findbugstaff.repository.MemberRepository;
import com.findbugs.findbugstaff.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class MemberRegister {
    private final MemberRepository memberRepository;
    private final StaffRepository staffRepository;
    @Autowired
    public MemberRegister(MemberRepository memberRepository, StaffRepository staffRepository) {
        this.memberRepository = memberRepository;
        this.staffRepository = staffRepository;
    }


    public String registerMember(Member member, Long staffId){
        Optional<Staff> staff = staffRepository.findById(staffId);

        Member registerMemberData = new Member((long) 1.0,member.getName(),member.getEmail()
                ,member.getPhoneNumber(),member.getAddress(),member.getMembership(), LocalDateTime.now(),
                LocalDateTime.now(),LocalDateTime.now().plusMonths(member.getMembership()),staff.get());
        memberRepository.save(registerMemberData);
        return "ok";
    }


}
