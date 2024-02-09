package com.su.iot.smartparkingapi.service;

import com.su.iot.smartparkingapi.entity.Reservation;
import com.su.iot.smartparkingapi.request.ReservationData;

import java.util.Optional;

public interface ReservationService {

    void createReservation(final ReservationData reservationData);

    void releaseReservation(final Long sensorId);

    Optional<Reservation> getActiveReservationFor(final Long sensorId);
}
