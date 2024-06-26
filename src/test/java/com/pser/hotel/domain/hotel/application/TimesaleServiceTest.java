package com.pser.hotel.domain.hotel.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.pser.hotel.domain.hotel.dao.HotelDao;
import com.pser.hotel.domain.hotel.dao.RoomDao;
import com.pser.hotel.domain.hotel.dao.TimesaleDao;
import com.pser.hotel.domain.hotel.domain.Hotel;
import com.pser.hotel.domain.hotel.domain.Room;
import com.pser.hotel.domain.hotel.domain.TimeSale;
import com.pser.hotel.domain.hotel.dto.response.HotelSummaryResponse;
import com.pser.hotel.domain.hotel.dto.request.TimesaleCreateRequest;
import com.pser.hotel.domain.hotel.dto.mapper.TimesaleMapper;
import com.pser.hotel.domain.hotel.util.Utils;
import com.pser.hotel.domain.member.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.web.servlet.MockMvc;


@ExtendWith(MockitoExtension.class)
public class TimesaleServiceTest {
    @Mock
    TimesaleDao timesaleDao;
    @Mock
    RoomDao roomDao;
    @Mock
    HotelDao hotelDao;
    @Mock
    TimesaleMapper timesaleMapper;
    @InjectMocks
    TimesaleService timesaleService;
    MockMvc mockMvc;
    User user;
    Hotel hotel;
    Room room;
    TimeSale timesale;
    TimesaleCreateRequest timesaleCreateRequest;
    Pageable pageable;

    @BeforeEach
    public void setUp() {
        pageable = createPageable();
        user = Utils.createUser();
        hotel = Utils.createHotel(user);
        room = Utils.createRoom(hotel);
        timesale = Utils.createTimesale(room);
        timesaleCreateRequest = createTimesaleCreateRequest(timesale);
    }

    @Test
    @DisplayName("타임특가 등록 Service 테스트")
    public void saveTimesaleDataTest() throws Exception {
        //given
        Optional<Room> optionalRoom = Optional.of(room);

        when(roomDao.findById(any())).thenReturn(optionalRoom);
        when(timesaleMapper.changeToTimesale(timesaleCreateRequest, room)).thenReturn(timesale);
        when(timesaleDao.save(timesale)).thenReturn(timesale);

        //when
        Long timesaleId = timesaleService.saveTimesaleData(timesaleCreateRequest);

        //then
        Assertions.assertThat(timesaleId).isEqualTo(timesale.getId());
    }

    @Test
    @DisplayName("타임특가 삭제 Service 테스트")
    public void deleteTimesaleDataTest() throws Exception {
        //given
        Optional<TimeSale> optionalTimeSale = Optional.of(timesale);

        when(timesaleDao.findById(any())).thenReturn(optionalTimeSale);

        //when
        timesaleService.deleteTimesaleData(timesale.getId());

        //then
        verify(timesaleDao, Mockito.times(1)).delete(timesale);
    }

    @Test
    @DisplayName("타임특가 숙소 전체 조회 Service 테스트")
    public void getAllTimesaleHotelDataTest() throws Exception {
        //given
        List<Hotel> hotels = Utils.createHotels(user, 10);
        List<HotelSummaryResponse> list = createTimesaleHotelResponseList(hotels);
        Slice<HotelSummaryResponse> pageTimesaleHotelData = new SliceImpl<>(list, pageable, true);

        when(timesaleDao.findNowTimesaleHotel(any(Pageable.class))).thenReturn(pageTimesaleHotelData);

        //when
        Slice<HotelSummaryResponse> sliceData = timesaleService.getAllTimesaleHotelData(pageable);

        //then
        Assertions.assertThat(sliceData.getContent().size()).isEqualTo(10);
    }

    private TimesaleCreateRequest createTimesaleCreateRequest(TimeSale timesale) {
        return TimesaleCreateRequest.builder()
                .roomId(1)
                .price(timesale.getPrice())
                .startAt(timesale.getStartAt())
                .endAt(timesale.getEndAt())
                .build();
    }

    private Pageable createPageable() {
        return PageRequest.of(0, 10);
    }

    private List<HotelSummaryResponse> createTimesaleHotelResponseList(List<Hotel> hotels) {
        List<HotelSummaryResponse> list = new ArrayList<>();
        for (Hotel ele : hotels) {
            double average = Utils.createAverageRating();
            int salePrice = Utils.createSalePrice();
            int previousPirce = salePrice + 5000;
            HotelSummaryResponse hotelResponse = createHotelResponse(ele, average, salePrice, previousPirce);
            list.add(hotelResponse);
        }
        return list;
    }

    private HotelSummaryResponse createHotelResponse(Hotel hotel, double average, int salePrice, int previousPrice) {
        return HotelSummaryResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .category(hotel.getCategory())
                .description(hotel.getDescription())
                .salePrice(salePrice)
                .previousPrice(previousPrice)
                .gradeAverage(average)
                .build();
    }
}
