package com.findbugs.findbugstaff.service;

import com.findbugs.findbugstaff.domain.Bug;
import com.findbugs.findbugstaff.domain.DetectedBugStats;
import com.findbugs.findbugstaff.implement.DetectedBugStatsSearcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class DetectedBugStatsServiceTest {

    @Mock
    DetectedBugStatsSearcher detectedBugStatsSearcher;

    @InjectMocks
    DetectedBugStatsService detectedBugStatsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("가장_많이_탐지된_벌레의_객체를_반환하는_서비스")
    void given_whenFindMostDetectedBug_thenReturnBug(){
        //given
        Bug honeyBee = Bug.builder().name("꿀벌").build();
        when(detectedBugStatsSearcher.getMostDetectedBug()).thenReturn(honeyBee);

        //when
        Bug mostDetectedBug = detectedBugStatsService.getMostDetectedBug();

        //then
        assertThat(mostDetectedBug).isEqualTo(honeyBee);
    }

    @Test
    @DisplayName("어제_가장_많이_탐지된_벌레의_수를_반환하는_서비스")
    void givenBugId_whenFindMostDetectedBugMessage_thenReturnString(){
        //given
        DetectedBugStats mostDetectedBugInfo = DetectedBugStats.builder()
                        .id(1L)
                        .bug(Bug.builder().name("꿀벌").build())
                        .detectedCount(6L)
                        .build();

        when(detectedBugStatsSearcher.getStatsForYesterday(1L)).thenReturn(mostDetectedBugInfo);

        //when
        String msg = detectedBugStatsService.getDetectedBugStatsMessage(1L);

        //then
        assertThat(msg).isEqualTo("어제 발견된 꿀벌의 수는 총 6마리입니다!");
        verify(detectedBugStatsSearcher).getStatsForYesterday(1L);
    }



}