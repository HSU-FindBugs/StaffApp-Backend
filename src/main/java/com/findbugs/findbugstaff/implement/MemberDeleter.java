package com.findbugs.findbugstaff.implement;

import com.findbugs.findbugstaff.repository.MemberRepository;
import com.findbugs.findbugstaff.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberDeleter {
    private final MemberRepository memberRepository;
    private final StaffRepository staffRepository;

}
