package com.su.iot.smartparkingapi.service;

import com.su.iot.smartparkingapi.entity.Booking;
import com.su.iot.smartparkingapi.request.BookingData;

import java.util.Optional;

public interface BookingService {

    void createBooking(final BookingData bookingData);

    void releaseBooking(final Long sensorId);

    Optional<Booking> getActiveBookingFor(final Long sensorId);
}