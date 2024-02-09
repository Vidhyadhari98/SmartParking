package com.su.iot.smartparkingapi.request;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationData implements Serializable {

    private Long userId;

    private Long sensorId;

    private LocalDate reservationDate;

    private LocalDateTime reservationStartTime;

    private LocalDateTime reservationEndTime;

    public Long getUserId() {
        return userId;
    }

    public ReservationData setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public ReservationData setSensorId(Long sensorId) {
        this.sensorId = sensorId;
        return this;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public ReservationData setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
        return this;
    }

    public LocalDateTime getReservationStartTime() {
        return reservationStartTime;
    }

    public ReservationData setReservationStartTime(LocalDateTime reservationStartTime) {
        this.reservationStartTime = reservationStartTime;
        return this;
    }

    public LocalDateTime getReservationEndTime() {
        return reservationEndTime;
    }

    public ReservationData setReservationEndTime(LocalDateTime reservationEndTime) {
        this.reservationEndTime = reservationEndTime;
        return this;
    }
}
