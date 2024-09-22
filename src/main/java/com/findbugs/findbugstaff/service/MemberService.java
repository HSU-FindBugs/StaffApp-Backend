package com.findbugs.findbugstaff.service;

import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.dto.Member.MemberRegisterRequestDto;
import com.findbugs.findbugstaff.dto.Member.MemberUpdateRequestDto;
import com.findbugs.findbugstaff.implement.Member.MemberFinder;
import com.findbugs.findbugstaff.implement.Member.MemberRegister;
import com.findbugs.findbugstaff.implement.Member.MemberSearcher;
import com.findbugs.findbugstaff.implement.Member.MemberUpdater;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberSearcher memberSearcher;
    private final MemberRegister memberRegister;
    private final MemberUpdater memberUpdater;
    private final MemberFinder memberFinder;

    public List<Member> get10Member(int page) {
        return memberSearcher.getAllMembers(page);
    }


    // 스태프 ID와 Page 정보를 기반으로 총 10명의 member 정보를 반환한다.
    public List<Member> get10ByStaffId(Long staffId, int page) {
        return memberFinder.find10ByStaffId(staffId, page);
    }

    public List<Member> searchMemberData(String name, Long staffId) {
        return memberSearcher.searchMemberData(name, staffId);
    }

    public List<String> recentSearchData(Long staffId) {
        return memberSearcher.getRecentSearchData(staffId);
    }

    @Transactional
    public void registerMember(MemberRegisterRequestDto memberRegisterRequestDto) {
        memberRegister.registerMember(memberRegisterRequestDto);
    }

    public Member getMemberById(Long memberId) {
        Optional<Member> getSinglemember = memberSearcher.memberSearcher(memberId);
        return getSinglemember.orElseThrow(() -> new EntityNotFoundException("해당 id 멤버는 존재하지 않습니다. " + memberId));
    }

    @Transactional
    public void updateMember(MemberUpdateRequestDto memberUpdateRequestDto) {
        memberUpdater.memberUpdate(memberUpdateRequestDto);
    }

    // 사용자 정보를 반환하는 서비스
    public Member getById(Long id){
        return memberFinder.getById(id);
    }

    // 사용자의 남은 멤버쉽 일수를 반환하는 서비스
    public String getRemainingMembershipDays(Long id){
        return "D-" + memberFinder.getRemainingMembershipDays(id);
    }

    // 사용자 특이사항을 수정 / 저장하는 서비스
    @Transactional
    public void setNote(Long memberId, String updateNote){
        memberUpdater.noteUpdate(memberId, updateNote);
    }

}
