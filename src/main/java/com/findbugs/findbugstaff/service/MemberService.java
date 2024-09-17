package com.findbugs.findbugstaff.service;

import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.dto.Member.MemberRegisterRequestDto;
import com.findbugs.findbugstaff.dto.Member.MemberUpdateRequestDto;
import com.findbugs.findbugstaff.implement.Member.MemberRegister;
import com.findbugs.findbugstaff.implement.Member.MemberSearcher;
import com.findbugs.findbugstaff.implement.Member.MemberUpdater;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberSearcher memberSearcher;
    private final MemberRegister memberRegister;
    private final MemberUpdater memberUpdater;

    public List<Member> getTenMember(int page) {
        return memberSearcher.getAllMembers(page);
    }

    public List<Member> searchMemberData(String name, Long staffId) {
        return memberSearcher.searchMemberData(name, staffId);
    }

    public List<String> recentSearchData(Long staffId) {
        return memberSearcher.getRecentSearchData(staffId);
    }

    public void registerMember(MemberRegisterRequestDto memberRegisterRequestDto) {
        memberRegister.registerMember(memberRegisterRequestDto);
    }

    public Member getMemberById(Long memberId) {
        Optional<Member> getSinglemember = memberSearcher.memberSearcher(memberId);
        return getSinglemember.orElseThrow(() -> new EntityNotFoundException("해당 id 멤버는 존재하지 않습니다. " + memberId));
    }

    public void updateMember(MemberUpdateRequestDto memberUpdateRequestDto) {
        memberUpdater.memberUpdate(memberUpdateRequestDto);
    }

    // 해충 기록 표기



}
