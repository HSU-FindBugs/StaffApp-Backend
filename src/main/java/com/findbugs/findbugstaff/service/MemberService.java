package com.findbugs.findbugstaff.service;

import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.dto.Member.MemberDto;
import com.findbugs.findbugstaff.dto.Member.MemberListDto;
import com.findbugs.findbugstaff.dto.Member.MemberRegisterRequestDto;
import com.findbugs.findbugstaff.dto.Member.MemberUpdateRequestDto;
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

    // MemberDto -> MemberListDto로 수정 24-09-15 18:55
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

    public MemberDto getMemberById(Long memberId) {
        Optional<Member> memberOptional = memberSearcher.memberSearcher(memberId);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            return convertToMemberDto(member);
        }
        throw new EntityNotFoundException("와 엔티티 따잇하는 재미" + memberId);
    }

    public void updateMember(MemberUpdateRequestDto memberUpdateRequestDto){
        memberUpdater.memberUpdate(memberUpdateRequestDto);
    }

}
