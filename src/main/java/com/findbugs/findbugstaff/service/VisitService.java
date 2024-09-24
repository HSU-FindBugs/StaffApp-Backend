package com.findbugs.findbugstaff.service;

import com.findbugs.findbugstaff.implement.DetectionHistoryFinder;
import com.findbugs.findbugstaff.implement.VisitFinder;
import com.findbugs.findbugstaff.implement.VisitRegister;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitService {

    private final VisitFinder visitFinder;
    private final VisitRegister visitRegister;
    private final DetectionHistoryFinder detectionHistoryFinder;

    // 해당 스태프가 해당 사용자에게 방문한 적이 있는 지 체크한다.
    public String checkStaffVisitedMember(Long memberId, Long staffId){
        if(detectionHistoryFinder.existsUnVisited(memberId)){
            return "방문 필요";
        }else{
            return "방문 완료";
        }
    }


    // 스태프가 사용자 방역 방문 시 로직
    @Transactional
    public void staffVisitMember(Long memberId, Long staffId) {
        visitRegister.register(staffId, memberId);
    }
}