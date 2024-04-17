package com.pser.hotel.domain.hotel.dto;

import com.pser.hotel.global.common.request.SearchQuery;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomSearchRequest extends SearchQuery {
    private Integer price;

    private LocalTime checkIn;

    private LocalTime checkOut;

    private Integer standardCapacity;

    private Integer maxCapacity;

    private Integer totalRooms;

    private Boolean heatingSystem;

    private Boolean tv;

    private Boolean refrigerator;

    private Boolean airConditioner;

    private Boolean washer;

    private Boolean terrace;

    private Boolean coffeeMachine;

    private Boolean internet;

    private Boolean kitchen;

    private Boolean bathtub;

    private Boolean iron;

    private Boolean pool;

    private Boolean pet;

    private Boolean inAnnex;

    @Builder
    public RoomSearchRequest(String keyword, LocalDateTime createdAfter, LocalDateTime createdBefore,
                             LocalDateTime updatedAfter, LocalDateTime updatedBefore, Integer price,
                             LocalTime checkIn, LocalTime checkOut, Integer standardCapacity, Integer maxCapacity,
                             Integer totalRooms, Boolean heatingSystem, Boolean tv, Boolean refrigerator,
                             Boolean airConditioner, Boolean washer, Boolean terrace, Boolean coffeeMachine,
                             Boolean internet, Boolean kitchen, Boolean bathtub, Boolean iron, Boolean pool,
                             Boolean pet,
                             Boolean inAnnex) {
        super(keyword, createdAfter, createdBefore, updatedAfter, updatedBefore);
        this.price = price;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.standardCapacity = standardCapacity;
        this.maxCapacity = maxCapacity;
        this.totalRooms = totalRooms;
        this.heatingSystem = heatingSystem;
        this.tv = tv;
        this.refrigerator = refrigerator;
        this.airConditioner = airConditioner;
        this.washer = washer;
        this.terrace = terrace;
        this.coffeeMachine = coffeeMachine;
        this.internet = internet;
        this.kitchen = kitchen;
        this.bathtub = bathtub;
        this.iron = iron;
        this.pool = pool;
        this.pet = pet;
        this.inAnnex = inAnnex;
    }
}