package com.findbugs.findbugstaff.implement.Member;

import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.domain.Staff;
import com.findbugs.findbugstaff.repository.MemberRepository;
import com.findbugs.findbugstaff.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberDeleter {
    private final MemberRepository memberRepository;
    private final StaffRepository staffRepository;


    public void deleteMember(Long staffId,Long memberId){
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 staffId입니다: " + staffId));
        Member checkMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 memberId입니다: "+ memberId));

        if(checkMember.getManager().equals(staff)){
            memberRepository.delete(checkMember);
        }
    }


}
