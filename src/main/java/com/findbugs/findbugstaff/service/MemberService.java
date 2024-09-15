package com.findbugs.findbugstaff.service;

import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.domain.Staff;
import com.findbugs.findbugstaff.dto.MemberDto;
import com.findbugs.findbugstaff.implement.MemberRegister;
import com.findbugs.findbugstaff.implement.MemberSearcher;
import com.findbugs.findbugstaff.implement.MemberUpdater;
import com.findbugs.findbugstaff.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class MemberService {

    private final MemberSearcher memberSearcher;
    private final MemberRegister memberRegister;

    private final MemberUpdater memberUpdater;
    @Autowired
    public MemberService(MemberSearcher memberSearcher, MemberRegister memberRegister, MemberUpdater memberUpdater) {
        this.memberSearcher = memberSearcher;
        this.memberRegister = memberRegister;
        this.memberUpdater = memberUpdater;
    }

    private MemberDto convertToMemberDto(Member member) {
        MemberDto dto = new MemberDto();
        dto.setId(member.getId());
        dto.setDisplayName(member.getName());
        dto.setAddress(member.getAddress());
        dto.setRecentVisit(member.getRecentVisit());
        dto.setPhoneNumber(member.getPhoneNumber());
        return dto;
    }

    public List<MemberDto> getAllMembers(int page,int size){
        return memberSearcher.getAllMembers(page, size).stream()
                .map(this::convertToMemberDto)
                .toList();
    }

    public List<MemberDto> searchMemberData(String name,Long staffId){
        return memberSearcher.searchMemberData(name,staffId).stream()
                .map(this::convertToMemberDto)
                .toList();
    }
    // 스태프로부터 멤버를 등록
    public void registerMember(Member member, Long staffId){
        memberRegister.registerMember(member,staffId);
    }

    public MemberDto getMemberById(Long memberId) {
        Optional<Member> memberOptional = memberSearcher.memberSearcher(memberId);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            return convertToMemberDto(member);
        }
        throw new EntityNotFoundException("와 엔티티 따잇하는 재미" + memberId);
    }

    public void updateMember(Long staffId,Long memberId,Member updateInfo){
        memberUpdater.memberUpdate(staffId,memberId,updateInfo);
    }

}
