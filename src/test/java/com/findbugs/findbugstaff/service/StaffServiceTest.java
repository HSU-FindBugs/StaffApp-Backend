package com.findbugs.findbugstaff.service;

import com.findbugs.findbugstaff.domain.Staff;
import com.findbugs.findbugstaff.implement.StaffFinder;
import com.findbugs.findbugstaff.implement.StaffReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class StaffServiceTest {

    @Mock
    StaffFinder staffFinder;

    @Mock
    StaffReader staffReader;

    @InjectMocks
    StaffService staffService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("직원_프로필_조회_서비스")
    void givenStaffId_whenFindStaff_thenReturnStaff(){
        //given
        Staff staff = Staff.builder().name("박종범").build();
        when(staffReader.getById(1L)).thenReturn(staff);

        //when
        Staff target = staffService.getStaff(1L);

        //then
        assertThat(target).isEqualTo(staff);
    }

    @Test
    @DisplayName("직원_담당_고객_수_조회_서비스")
    void givenStaffId_whenFindCountMemberInCharge_thenReturnLong(){
        //given
        when(staffFinder.getMemberInCharge(1L)).thenReturn(7L);

        //when
        Long findMemberInChargeCount = staffService.getMemberInCharge(1L);

        //then
        assertThat(findMemberInChargeCount).isEqualTo(7L);
    }
}