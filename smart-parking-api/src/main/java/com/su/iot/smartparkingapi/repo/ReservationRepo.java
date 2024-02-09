package com.su.iot.smartparkingapi.repo;

import com.su.iot.smartparkingapi.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByParkingUserParkingUserId(Long parkingUserId);

    Optional<Reservation> findReservationBySensorSensorIdAndActualReservationEndTimeNull(final Long sensorId);
}
