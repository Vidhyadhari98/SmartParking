package com.su.iot.smartparkingapi.service;

import com.su.iot.smartparkingapi.entity.ParkingUser;
import com.su.iot.smartparkingapi.entity.Reservation;
import com.su.iot.smartparkingapi.entity.Sensor;
import com.su.iot.smartparkingapi.repo.ParkingUserRepo;
import com.su.iot.smartparkingapi.repo.ReservationRepo;
import com.su.iot.smartparkingapi.repo.SensorRepo;
import com.su.iot.smartparkingapi.request.ReservationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepo reservationRepo;
    private final ParkingUserRepo parkingUserRepo;
    private final SensorRepo sensorRepo;

    @Autowired
    public ReservationServiceImpl(ReservationRepo reservationRepo,
                                  ParkingUserRepo parkingUserRepo,
                                  SensorRepo sensorRepo) {
        this.reservationRepo = reservationRepo;
        this.parkingUserRepo = parkingUserRepo;
        this.sensorRepo = sensorRepo;
    }

    @Override
    @Transactional
    public void createReservation(final ReservationData reservationData) {
        final boolean activeReservationExists = reservationRepo.findAllByParkingUserParkingUserId(reservationData.getUserId()).stream()
                .anyMatch(Reservation::isActive);
        if (activeReservationExists) {
            throw new IllegalStateException("Active reservation already exists for user " + reservationData.getUserId());
        }

        Reservation reservation = new Reservation()
                .setParkingUser(getParkingUser(reservationData))
                .setReservationDate(LocalDate.now())
                .setReservationStartTime(LocalDateTime.now())
                .setExpectedReservationEndTime(LocalDateTime.now().plusHours(2))
                .setSensor(getSensor(reservationData));

        reservationRepo.save(reservation);
    }

    @Override
    @Transactional
    public void releaseReservation(Long sensorId) {
        final Optional<Reservation> reservationOptional = getActiveReservationFor(sensorId);
        if (reservationOptional.isEmpty()) {
            throw new IllegalStateException("Active reservation does not exist for sensor " + sensorId);
        }

        reservationOptional.get().setActualReservationEndTime(LocalDateTime.now());
    }

    @Override
    public Optional<Reservation> getActiveReservationFor(Long sensorId) {
        return reservationRepo.findReservationBySensorSensorIdAndActualReservationEndTimeNull(sensorId);
    }

    private Sensor getSensor(ReservationData reservationData) {
        return sensorRepo.findById(reservationData.getSensorId())
                .orElseThrow(() -> new IllegalArgumentException("Sensor not exists for id " + reservationData.getSensorId()));
    }

    private ParkingUser getParkingUser(ReservationData reservationData) {
        return parkingUserRepo.findById(reservationData.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not exists for id " + reservationData.getUserId()));
    }
}
