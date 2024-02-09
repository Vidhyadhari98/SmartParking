package com.su.iot.smartparkingapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservationId;

    @NotNull
    @OneToOne
    private ParkingUser parkingUser;

    @NotNull
    @OneToOne
    private Sensor sensor;

    @NotNull
    private LocalDate reservationDate;

    @NotNull
    private LocalDateTime reservationStartTime;

    @NotNull
    private LocalDateTime expectedReservationEndTime;

    private LocalDateTime actualReservationEndTime;

    public long getReservationId() {
        return reservationId;
    }

    public Reservation setReservationId(long reservationId) {
        this.reservationId = reservationId;
        return this;
    }

    public ParkingUser getParkingUser() {
        return parkingUser;
    }

    public Reservation setParkingUser(ParkingUser parkingUser) {
        this.parkingUser = parkingUser;
        return this;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public Reservation setSensor(Sensor sensor) {
        this.sensor = sensor;
        return this;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public Reservation setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
        return this;
    }

    public LocalDateTime getReservationStartTime() {
        return reservationStartTime;
    }

    public Reservation setReservationStartTime(LocalDateTime reservationStartTime) {
        this.reservationStartTime = reservationStartTime;
        return this;
    }

    public LocalDateTime getExpectedReservationEndTime() {
        return expectedReservationEndTime;
    }

    public Reservation setExpectedReservationEndTime(LocalDateTime expectedReservationEndTime) {
        this.expectedReservationEndTime = expectedReservationEndTime;
        return this;
    }

    public LocalDateTime getActualReservationEndTime() {
        return actualReservationEndTime;
    }

    public Reservation setActualReservationEndTime(LocalDateTime actualReservationEndTime) {
        this.actualReservationEndTime = actualReservationEndTime;
        return this;
    }

    public boolean isActive() {
        return expectedReservationEndTime != null && actualReservationEndTime == null;
    }
}
