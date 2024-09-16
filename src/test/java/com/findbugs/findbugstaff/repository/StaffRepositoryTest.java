package com.findbugs.findbugstaff.repository;

import com.findbugs.findbugstaff.domain.Address;
import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.domain.Staff;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase
public class StaffRepositoryTest {

    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private MemberRepository memberRepository;

    private Staff staff;

    @BeforeEach
    void setUp() {
        //given
        staff = Staff.builder()
                .name("박종범")
                .profileUrl("https://avatars.githubusercontent.com/u/122260287?v=4")
                .position("컨설턴트")
                .phoneNumber("010-9999-9999")
                .territory(
                        Address.builder()
                                .region_1depth("서울시")
                                .region_2depth("동작구")
                                .region_3depth("사당동")
                                .build()
                )
                .build();

        staffRepository.save(staff);

        Member member1 = Member.builder()
                .name("송효재")
                .manager(staff)
                .build();
        memberRepository.save(member1);

        Member member2 = Member.builder()
                .name("김성민")
                .manager(staff)
                .build();
        memberRepository.save(member2);

        Member member3 = Member.builder()
                .name("곽승준")
                .manager(staff)
                .build();
        memberRepository.save(member3);

        Member member4 = Member.builder()
                .name("김우현")
                .manager(staff)
                .build();
        memberRepository.save(member4);
    }

    @Test
    @DisplayName("직원_ID로_프로필_반환")
    void givenStaff_whenFindProfileUrlById_thenReturnStaffProfileUrl(){
        //given
        Long staffId = staff.getId();

        //when
        Optional<String> profileUrl = staffRepository.findProfileById(staffId);

        //then
        assertTrue(profileUrl.isPresent());
        assertThat(profileUrl.get()).isEqualTo("https://avatars.githubusercontent.com/u/122260287?v=4");
    }

    @Test
    @DisplayName("직원_ID로_직급_반환")
    void givenStaff_whenFindPositionById_thenReturnStaffPosition(){
        //given
        Long staffId = staff.getId();

        //when
        Optional<String> position = staffRepository.findPositionById(staffId);

        //then
        assertTrue(position.isPresent());
        assertThat(position.get()).isEqualTo("컨설턴트");
    }

    @Test
    @DisplayName("직원_ID로_전화번호_반환")
    void givenStaff_whenFindPhoneNumById_thenReturnStaffPhoneNum(){
        //given
        Long staffId = staff.getId();

        //when
        Optional<String> phoneNumber = staffRepository.findPhoneById(staffId);

        //then
        assertTrue(phoneNumber.isPresent());
        assertThat(phoneNumber.get()).isEqualTo("010-9999-9999");
    }

    @Test
    @DisplayName("직원_ID로_담당_주소_반환")
    void givenStaff_whenFindAddressById_thenReturnStaffAddress(){
        //given
        Long staffId = staff.getId();

        //when
        Optional<Address> territory = staffRepository.findTerritoryById(staffId);

        //then
        assertTrue(territory.isPresent());
        assertThat(territory.get().getRegion_1depth()).isEqualTo("서울시");
        assertThat(territory.get().getRegion_2depth()).isEqualTo("동작구");
        assertThat(territory.get().getRegion_3depth()).isEqualTo("사당동");
    }

    @Test
    @DisplayName("직원_ID로_담당_고객_리스트_반환")
    void givenStaff_whenFindMemberById_thenReturnMember(){
        //given
        Long staffId = staff.getId();

        //when
        List<Member> members = staffRepository.findByManager(staff);

        //then
        assertThat(members).hasSize(4);
        assertThat(members).extracting("name")
                .containsExactlyInAnyOrder("송효재", "김성민", "곽승준", "김우현");
    }

    @Test
    @DisplayName("직원_ID로_담당_고객의_수_반환")
    void givenStaff_whenFindMemberCountById_thenReturnMemberCount(){
        //given
        Long staffId = staff.getId();

        //when
        Long memberCount = staffRepository.countByManager(staffId);

        //then
        assertThat(memberCount).isEqualTo(4L);
    }


}
