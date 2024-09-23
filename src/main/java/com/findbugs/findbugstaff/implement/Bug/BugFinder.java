package com.findbugs.findbugstaff.implement.Bug;

import com.findbugs.findbugstaff.domain.Bug;
import com.findbugs.findbugstaff.repository.BugRepository;
import com.findbugs.findbugstaff.repository.CameraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BugFinder {
    private final BugRepository bugRepository;

    public Bug getBugInfo(Long bugId){
        Optional<Bug> bug = bugRepository.findById(bugId);
        if(bug.isPresent()){
            return bug.get();

        }
        else{
            return null;
        }
    }

}
