package com.findbugs.findbugstaff.implement;


import com.findbugs.findbugstaff.repository.StaffRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StaffFinder {

    private final StaffRepository staffRepository;

    public Long getMembersManagedByStaff(Long id){

        if(!staffRepository.existsById(id)){
            throw new EntityNotFoundException("Staff with id " + id + " not found");
        }

        return staffRepository.countByManager(id);
    }

}
