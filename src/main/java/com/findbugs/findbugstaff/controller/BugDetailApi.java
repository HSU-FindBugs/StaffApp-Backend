package com.findbugs.findbugstaff.controller;

import com.findbugs.findbugstaff.dto.Bug.BugDetailDto;
import com.findbugs.findbugstaff.dto.Bug.BugSolutionDto;
import com.findbugs.findbugstaff.service.BugDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/bug-detail")
@RequiredArgsConstructor
public class BugDetailApi {

    private final BugDetailService bugDetailService;

    // Bug 상세 데이터를 가져오는 엔드포인트
    @GetMapping("/data")
    public ResponseEntity<BugDetailDto> getBugDetail(@RequestParam("bugName") String bugName) {
        BugDetailDto bugDetailDto = bugDetailService.getBugDataByName(bugName);
        return ResponseEntity.ok(bugDetailDto);
    }

    // Bug 솔루션 데이터를 가져오는 엔드포인트
    @GetMapping("/solution")
    public ResponseEntity<BugSolutionDto> getBugSolution(@RequestParam("bugName") String bugName) {
        BugSolutionDto bugSolutionDto = bugDetailService.getBugSolution(bugName);
        return ResponseEntity.ok(bugSolutionDto);
    }
}
