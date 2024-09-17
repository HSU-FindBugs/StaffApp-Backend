package com.findbugs.findbugstaff.service;

import com.findbugs.findbugstaff.domain.Staff;
import com.findbugs.findbugstaff.implement.StaffFinder;
import com.findbugs.findbugstaff.implement.StaffReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StaffService {

    private final StaffReader staffReader;
    private final StaffFinder staffFinder;

    /**
     * 직원 프로필 조회 서비스
     */
    public Staff getStaff(Long id){
        return staffReader.getById(id);
    }

    /**
     * 직원 담당 고객 수 조회 서비스
     */
    public Long getMemberInCharge(Long id){
        return staffFinder.getMemberInCharge(id);
    }
}
