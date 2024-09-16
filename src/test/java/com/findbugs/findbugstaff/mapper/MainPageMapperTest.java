package com.findbugs.findbugstaff.mapper;

import com.findbugs.findbugstaff.domain.Address;
import com.findbugs.findbugstaff.domain.Bug;
import com.findbugs.findbugstaff.domain.Notification;
import com.findbugs.findbugstaff.domain.Staff;
import com.findbugs.findbugstaff.dto.MainPage.MainPageResponseDto;
import com.findbugs.findbugstaff.dto.NotificationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MainPageMapperTest {

    @Mock
    private NotificationMapper notificationMapper;

    @InjectMocks
    private MainPageMapper mainPageMapper = Mappers.getMapper(MainPageMapper.class);

    @Test
    @DisplayName("메인_페이지_DTO로_매핑해주는_매퍼")
    public void givenStaffBugAndNotification_whenMappingToMainPageDto_thenReturnMainPageDto() {
        //given
        Staff staff = Staff.builder()
                .id(1L)
                .name("박종범")
                .profileUrl("https://avatars.githubusercontent.com/u/122260287?v=4")
                .position("컨설턴트")
                .territory(
                        Address.builder()
                                .region_1depth("서울시")
                                .region_2depth("동작구")
                                .region_3depth("사당동")
                                .build()
                )
                .build();

        Bug bug = Bug.builder()
                .name("바퀴벌레")
                .description("바퀴벌레는 깊은 숲속 옹달샘을 좋아한다.")
                .build();

        Long countManagedMember = 37L;
        String countDetectedBugs = "어제 찾아벌레는 72마리의 바퀴벌레가 감지되었습니다.";
        List<Notification> notifications = new ArrayList<Notification>();
        for(int i = 0; i < 10; i++){
            notifications.add(
                    Notification.builder()
                            .title(i + 1 + "번째 공지입니다")
                            .content("김민규 넥슨 입사 시 5000만 메소 모두에게 나눠준다고 밝혀, 충격!")
                            .build()
            );
        }

        List<NotificationDto> notificationDtoList = notifications.stream()
                .map(src -> NotificationDto.builder()
                        .title(src.getTitle())
                        .content(src.getContent())
                        .profileUrl(src.getProfileUrl()) // profileUrl 필드가 있을 경우
                        .build())
                .collect(Collectors.toCollection(ArrayList::new));

        when(notificationMapper.toNotificationDtoList(notifications)).thenReturn(notificationDtoList);

        //when
        MainPageResponseDto mainPageResponseDto = mainPageMapper.toMainPageResponseDto(
                staff,
                countManagedMember,
                bug,
                countDetectedBugs,
                notifications
        );

        // then
        assertThat(mainPageResponseDto).isNotNull();
        assertThat(mainPageResponseDto.getStaffName()).isEqualTo("박종범 컨설턴트");
        assertThat(mainPageResponseDto.getStaffTerritory()).isEqualTo("서울시 동작구 사당동");
        assertThat(mainPageResponseDto.getStaffProfileUrl()).isEqualTo("https://avatars.githubusercontent.com/u/122260287?v=4");
        assertThat(mainPageResponseDto.getCountMemberManagedByStaff()).isEqualTo(37L);
        assertThat(mainPageResponseDto.getBugName()).isEqualTo("바퀴벌레");
        assertThat(mainPageResponseDto.getBugDescription()).isEqualTo("바퀴벌레는 깊은 숲속 옹달샘을 좋아한다.");
        assertThat(mainPageResponseDto.getBugStats()).isEqualTo("어제 찾아벌레는 72마리의 바퀴벌레가 감지되었습니다.");
        assertThat(mainPageResponseDto.getNotificationDtoList()).hasSize(10);

        for (int i = 0; i < 10; i++) {
            NotificationDto notificationDto = mainPageResponseDto.getNotificationDtoList().get(i);
            assertThat(notificationDto.getTitle()).isEqualTo((i + 1) + "번째 공지입니다");
            assertThat(notificationDto.getContent()).isEqualTo("김민규 넥슨 입사 시 5000만 메소 모두에게 나눠준다고 밝혀, 충격!");
        }
    }

    @Nested
    @DisplayName("주소_조합_및_TRIM을_수행하는_기능")
    class ConcatRegionTest {

        @Test
        @DisplayName("3단계_지역_정보가_모두_포함된_경우")
        public void givenFullRegion_whenMappingAddress_thenReturnComposeString() {
            // given
            Address address = Address.builder()
                    .region_1depth("경기도")
                    .region_2depth("하남시")
                    .region_3depth("덕풍동")
                    .build();
            Staff staff = Staff.builder().territory(address).build();

            // when
            String concatAddress = mainPageMapper.concatRegion(staff);

            // then
            assertThat(concatAddress).isEqualTo("경기도 하남시 덕풍동");
        }

        @Test
        @DisplayName("2단계_지역_정보만_포함된_경우")
        public void givenPartialRegion_whenMappingAddress_thenReturnComposeString() {
            // given
            Address address = Address.builder()
                    .region_1depth("경기도")
                    .region_2depth("하남시")
                    .build();
            Staff staff = Staff.builder().territory(address).build();

            // when
            String concatAddress = mainPageMapper.concatRegion(staff);

            // then
            assertThat(concatAddress).isEqualTo("경기도 하남시");
        }

        @Test
        @DisplayName("1단계_지역_정보만_포함된_경우")
        public void givenSingleRegion_whenMappingAddress_thenReturnComposeString() {
            // given
            Address address = Address.builder()
                    .region_1depth("경기도")
                    .build();
            Staff staff = Staff.builder().territory(address).build();

            // when
            String concatAddress = mainPageMapper.concatRegion(staff);

            // then
            assertThat(concatAddress).isEqualTo("경기도");
        }
    }

}