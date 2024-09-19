package com.findbugs.findbugstaff.implement.Member;

import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.domain.Staff;
import com.findbugs.findbugstaff.dto.Member.MemberUpdateRequestDto;
import com.findbugs.findbugstaff.repository.MemberRepository;
import com.findbugs.findbugstaff.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberUpdater {

    private final MemberFinder memberFinder;

    private final MemberRepository memberRepository;
    private final StaffRepository staffRepository;

    public void memberUpdate(MemberUpdateRequestDto memberUpdateRequestDto){
        Staff staff = staffRepository.findById(memberUpdateRequestDto.getStaffId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 staffId입니다: " + memberUpdateRequestDto.getStaffId()));
        Member checkMember = memberRepository.findById(memberUpdateRequestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 memberId입니다: "+ memberUpdateRequestDto.getMemberId()));
        if(checkMember.getManager().equals(staff)){
            Member updateMember = checkMember;
            updateMember.updateAddress(memberUpdateRequestDto.getAddress());
            updateMember.updateName(memberUpdateRequestDto.getName());
            updateMember.updatePhoneNumber(memberUpdateRequestDto.getPhoneNumber());
        }
    }

    @Transactional
    public void noteUpdate(Long memberId, String note){
        Member member = memberFinder.getById(memberId);
        member.updateNote(note);
        memberRepository.save(member);
    }


}
