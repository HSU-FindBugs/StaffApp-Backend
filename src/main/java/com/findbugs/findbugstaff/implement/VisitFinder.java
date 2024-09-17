package com.findbugs.findbugstaff.implement;

import com.findbugs.findbugstaff.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class VisitFinder {

    private final VisitRepository visitRepository;

    public boolean hasStaffVisitedMember(Long memberId, Long staffId, LocalDate Date){
        return visitRepository.existsByIdAndLocalDate(memberId, staffId, Date);
    }

}
