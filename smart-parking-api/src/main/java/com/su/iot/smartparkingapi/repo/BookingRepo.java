package com.su.iot.smartparkingapi.repo;

import com.su.iot.smartparkingapi.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepo extends JpaRepository<Booking, Long> {
    Optional<Booking> findBookingBySensorSensorIdAndActualBookingEndTimeNull(final Long sensorId);
}