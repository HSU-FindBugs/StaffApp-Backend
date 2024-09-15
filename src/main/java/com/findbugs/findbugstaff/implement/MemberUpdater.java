package com.findbugs.findbugstaff.implement;

import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.domain.Staff;
import com.findbugs.findbugstaff.repository.MemberRepository;
import com.findbugs.findbugstaff.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class MemberUpdater {

    private final MemberRepository memberRepository;
    private final StaffRepository staffRepository;

    @Autowired
    public MemberUpdater(MemberRepository memberRepository, StaffRepository staffRepository) {
        this.memberRepository = memberRepository;
        this.staffRepository = staffRepository;
    }


    public void memberUpdate(Long staffId,Long memberId,Member updateInfo){
        Optional<Staff> staff = staffRepository.findById(staffId);

        Optional<Member> checkMember = memberRepository.findById(memberId);
        if(checkMember.isPresent() && checkMember.get().getManager().equals(staff.get())){
            Member updateMember = checkMember.get();
            updateMember.updateAddress(updateInfo.getAddress());
            updateMember.updateName(updateInfo.getName());
            updateMember.updatePhoneNumber(updateInfo.getPhoneNumber());
        }
    }


}
