package com.findbugs.findbugstaff.service;

import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.dto.Member.MemberDto;
import com.findbugs.findbugstaff.dto.Member.MemberListDto;
import com.findbugs.findbugstaff.dto.Member.MemberRegisterRequestDto;
import com.findbugs.findbugstaff.dto.Member.MemberUpdateRequestDto;
import com.findbugs.findbugstaff.implement.MemberFinder;
import com.findbugs.findbugstaff.implement.MemberRegister;
import com.findbugs.findbugstaff.implement.MemberSearcher;
import com.findbugs.findbugstaff.implement.MemberUpdater;
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


    private MemberListDto convertToMemberListDto(Member member) {
        return MemberListDto.builder()
                .name(member.getName()).recentVisit(member.getRecentVisit())
                .address(member.getAddress()).build();
    }

    private MemberDto convertToMemberDto(Member member) {
        return MemberDto.builder()
                .id(member.getId()).address(member.getAddress())
                .displayName(member.getName()).phoneNumber(member.getPhoneNumber())
                .recentVisit(member.getRecentVisit()).build();
    }


    public List<MemberListDto> getAllMembers(int page){
        return memberSearcher.getAllMembers(page).stream()
                .map(this::convertToMemberListDto)
                .toList();
    }

    public List<MemberDto> searchMemberData(String name,Long staffId){
        return memberSearcher.searchMemberData(name,staffId).stream()
                .map(this::convertToMemberDto)
                .toList();
    }
    // 스태프로부터 멤버를 등록
    public void registerMember(MemberRegisterRequestDto memberRegisterRequestDto){
        memberRegister.registerMember(memberRegisterRequestDto);
    }

    //
    public MemberDto getMemberById(Long memberId) {
        Optional<Member> memberOptional = memberSearcher.findById(memberId);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            return convertToMemberDto(member);
        }
        throw new EntityNotFoundException("와 엔티티 따잇하는 재미" + memberId);
    }

    public void updateMember(MemberUpdateRequestDto memberUpdateRequestDto){
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
    public void setNote(Long memberId, String updateNote){
        memberUpdater.noteUpdate(memberId, updateNote);
    }

}
