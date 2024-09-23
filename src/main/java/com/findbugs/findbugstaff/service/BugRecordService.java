package com.findbugs.findbugstaff.service;

import com.findbugs.findbugstaff.dto.Bug.BugRecordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BugRecordService {
    private final BugDetailService bugDetailService;



}
