package com.pser.hotel.domain.hotel.domain;

import com.pser.hotel.domain.hotel.config.MapperConfig;
import com.pser.hotel.domain.member.domain.User;
import com.pser.hotel.global.config.QueryDslConfig;
import java.time.LocalTime;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({QueryDslConfig.class, MapperConfig.class})
@DisplayName("Room 엔티티 테스트")
@Slf4j
class RoomTest {
    @Autowired
    TestEntityManager em;
    Hotel hotel;

    @BeforeEach
    public void setUp() {
        User user = User.builder()
                .email("email@gmail.com")
                .password("password")
                .build();
        hotel = Hotel.builder()
                .name("업체명")
                .category(HotelCategoryEnum.HOTEL)
                .description("설명")
                .notice("공지")
                .province("서울특별시")
                .city("금천구")
                .district("가산동")
                .detailedAddress("가산디지털로1로 189")
                .latitude(100.123)
                .longitude(123.100)
                .mainImage("mainImg.url")
                .businessNumber("123456-123456")
                .certUrl("cert.url")
                .visitGuidance("가산디지털단지역 도보 5분")
                .user(user)
                .build();
        em.persist(hotel);
    }

    @Test
    @DisplayName("Room 생성 테스트")
    public void testSave() {
        //Given
        Room room = Room.builder()
                .hotel(hotel)
                .name("name")
                .price(1000)
                .checkIn(LocalTime.of(17, 0))
                .checkOut(LocalTime.of(12, 0))
                .totalRooms(10)
                .standardCapacity(2)
                .maxCapacity(5)
                .precaution("주의사항")
                .description("설명")
                .build();

        //When
        em.persist(room);

        //Then
        Assertions.assertThat(em.find(Room.class, room.getId())).isEqualTo(room);
    }

    @Test
    @DisplayName("Room 생성 에러 테스트 - 기준 인원이 최대 인원을 초과하는 경우")
    public void testSaveError() {
        //Given
        Room room = Room.builder()
                .hotel(hotel)
                .name("name")
                .price(1000)
                .checkIn(LocalTime.of(17, 0))
                .checkOut(LocalTime.of(12, 0))
                .totalRooms(10)
                .standardCapacity(7)
                .maxCapacity(5)
                .precaution("주의사항")
                .description("설명")
                .build();

        //When
        Throwable throwable = Assertions.catchThrowable(() -> {
            em.persist(room);
        });

        //Then
        Assertions.assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }
}