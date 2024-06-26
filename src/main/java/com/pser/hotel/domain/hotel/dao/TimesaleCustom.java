package com.pser.hotel.domain.hotel.dao;

import com.pser.hotel.domain.hotel.domain.Hotel;
import com.pser.hotel.domain.hotel.dto.response.HotelSummaryResponse;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface TimesaleCustom {
    Optional<Hotel> findHotelByRoomId(Long roomId);

    Optional<Hotel> findHotelByTimesaleId(Long timesaleId);

    Slice<HotelSummaryResponse> findNowTimesaleHotel(Pageable pageable);
}
