package com.su.iot.smartparkingapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
public class Booking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long bookingId;

    @NotNull
    @ManyToOne
    private Sensor sensor;

    @NotNull
    private LocalDate bookingDate;

    @NotNull
    private LocalDateTime bookingStartTime;

    @NotNull
    private LocalDateTime expectedBookingEndTime;

    private LocalDateTime actualBookingEndTime;

    public long getBookingId() {
        return bookingId;
    }

    public Booking setBookingId(long bookingId) {
        this.bookingId = bookingId;
        return this;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public Booking setSensor(Sensor sensor) {
        this.sensor = sensor;
        return this;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public Booking setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
        return this;
    }

    public LocalDateTime getBookingStartTime() {
        return bookingStartTime;
    }

    public Booking setBookingStartTime(LocalDateTime bookingStartTime) {
        this.bookingStartTime = bookingStartTime;
        return this;
    }

    public LocalDateTime getExpectedBookingEndTime() {
        return expectedBookingEndTime;
    }

    public Booking setExpectedBookingEndTime(LocalDateTime expectedBookingEndTime) {
        this.expectedBookingEndTime = expectedBookingEndTime;
        return this;
    }

    public LocalDateTime getActualBookingEndTime() {
        return actualBookingEndTime;
    }

    public Booking setActualBookingEndTime(LocalDateTime actualBookingEndTime) {
        this.actualBookingEndTime = actualBookingEndTime;
        return this;
    }

    public boolean isActive() {
        return expectedBookingEndTime != null && actualBookingEndTime == null;
    }
}
