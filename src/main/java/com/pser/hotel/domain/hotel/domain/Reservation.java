package com.pser.hotel.domain.hotel.domain;

import com.pser.hotel.domain.member.domain.User;
import com.pser.hotel.domain.model.StatusHolderEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString(of = {"price", "startAt", "endAt", "reservationCapacity", "adultCapacity", "childCapacity", "status"})
public class Reservation extends StatusHolderEntity<ReservationStatusEnum> {
    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Room room;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private LocalDate startAt;

    @Column(nullable = false)
    private LocalDate endAt;

    @Column(nullable = false)
    @Min(0)
    private int reservationCapacity;

    @Column(nullable = false)
    @Min(0)
    private int adultCapacity;

    @Column(nullable = false)
    @Min(0)
    private int childCapacity;

    @Column(nullable = false)
    private ReservationStatusEnum status;


    @Builder
    public Reservation(User user, Room room, int price, LocalDate startAt, LocalDate endAt,
                       int reservationCapacity,
                       int adultCapacity, int childCapacity, ReservationStatusEnum status) {
        setUser(user);
        setRoom(room);
        this.price = price;
        this.startAt = startAt;
        this.endAt = endAt;
        this.reservationCapacity = reservationCapacity;
        this.adultCapacity = adultCapacity;
        this.childCapacity = childCapacity;
        this.status = status;
    }

    @PrePersist
    private void validate() {
        if (reservationCapacity != (adultCapacity + childCapacity)) {
            throw new IllegalArgumentException("기준 인원이 최대 인원보다 클 수 없습니다");
        }
    }

}
