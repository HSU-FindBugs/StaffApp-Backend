package com.findbugs.findbugstaff.implement;

import com.findbugs.findbugstaff.domain.Staff;
import com.findbugs.findbugstaff.repository.StaffRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StaffReader {

    private final StaffRepository staffRepository;

    public Staff getStaff(Long id) {
        return staffRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Staff with id " + id + " not found"));
    }

}
