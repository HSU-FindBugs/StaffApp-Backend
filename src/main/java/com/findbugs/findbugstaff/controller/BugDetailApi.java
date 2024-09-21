package com.findbugs.findbugstaff.controller;

import com.findbugs.findbugstaff.dto.Bug.BugDetailDto;
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

    @GetMapping("/get")
    public ResponseEntity<BugDetailDto> nosqlTest(@RequestParam("bugName") String bugName) throws ExecutionException, InterruptedException {

        return ResponseEntity.ok(bugDetailService.getBugDataByName(bugName));
    }

}
