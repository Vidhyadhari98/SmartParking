package com.su.iot.smartparkingapi.response;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationResponseData implements Serializable {

    private Long reservationId;

    private Long parkingUserId;

    private SensorResponseData sensor;

    private LocalDate reservationDate;

    private LocalDateTime reservationStartTime;

    private LocalDateTime expectedReservationEndTime;

    private LocalDateTime actualReservationEndTime;

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getParkingUserId() {
        return parkingUserId;
    }

    public void setParkingUserId(Long parkingUserId) {
        this.parkingUserId = parkingUserId;
    }

    public SensorResponseData getSensor() {
        return sensor;
    }

    public void setSensor(SensorResponseData sensor) {
        this.sensor = sensor;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public LocalDateTime getReservationStartTime() {
        return reservationStartTime;
    }

    public void setReservationStartTime(LocalDateTime reservationStartTime) {
        this.reservationStartTime = reservationStartTime;
    }

    public LocalDateTime getExpectedReservationEndTime() {
        return expectedReservationEndTime;
    }

    public void setExpectedReservationEndTime(LocalDateTime expectedReservationEndTime) {
        this.expectedReservationEndTime = expectedReservationEndTime;
    }

    public LocalDateTime getActualReservationEndTime() {
        return actualReservationEndTime;
    }

    public void setActualReservationEndTime(LocalDateTime actualReservationEndTime) {
        this.actualReservationEndTime = actualReservationEndTime;
    }
}
