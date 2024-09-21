package com.findbugs.findbugstaff.implement.Member;

import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.domain.Staff;
import com.findbugs.findbugstaff.dto.Member.MemberRegisterRequestDto;
import com.findbugs.findbugstaff.repository.MemberRepository;
import com.findbugs.findbugstaff.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
@RequiredArgsConstructor
public class MemberRegister {
    private final MemberRepository memberRepository;
    private final StaffRepository staffRepository;



    public String registerMember(MemberRegisterRequestDto memberRegisterRequestDto) {
        try {
            // StaffId로 Staff 찾기 검증 
            Staff staff = staffRepository.findById(memberRegisterRequestDto.getStaffId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 staffId입니다: " + memberRegisterRequestDto.getStaffId()));


            // Member 객체 생성
            Member registerMemberData = Member.builder()
                    .name(memberRegisterRequestDto.getName())
                    .email(memberRegisterRequestDto.getEmail())
                    .address(memberRegisterRequestDto.getAddress())
                    .phoneNumber(memberRegisterRequestDto.getPhoneNumber())
                    .membership(memberRegisterRequestDto.getMemberShip())
                    .registerAt(LocalDateTime.now())
                    .expiresAt(LocalDateTime.now().plusMonths(memberRegisterRequestDto.getMemberShip())) // 1개월 뒤 만료
                    .recentVisit(LocalDateTime.now())
                    .manager(staff)
                    .build();

            // DB에 저장
            memberRepository.save(registerMemberData);
            return "ok";
        } catch (IllegalArgumentException e) {
            return "입력 오류: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace(); // 로그에 남김
            return "알 수 없는 오류가 발생했습니다.";
        }
    }



}
