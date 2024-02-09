package com.su.iot.smartparkingapi.repo;

import com.su.iot.smartparkingapi.entity.ParkingUser;
import com.su.iot.smartparkingapi.entity.Reservation;
import com.su.iot.smartparkingapi.entity.Sensor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ReservationRepoTest {

    @Autowired
    private SensorRepo sensorRepo;
    @Autowired
    private ReservationRepo reservationRepo;
    @Autowired
    private ParkingUserRepo parkingUserRepo;

    @Test
    void save() {
        final Reservation reservation = createReservation("user-name");

        final Reservation saveReservation = reservationRepo.save(reservation);

        final Optional<Reservation> reservationOptional = reservationRepo.findById(saveReservation.getReservationId());

        assertThat(reservationOptional).isNotEmpty();
    }

    @Test
    void findAllByParkingUserParkingUserId() {
        final Reservation firstReservation = createReservation("first-user");
        reservationRepo.save(firstReservation);
        final Reservation secondReservation = createReservation("second-user");
        reservationRepo.save(secondReservation);

        final List<Reservation> reservations = reservationRepo.findAllByParkingUserParkingUserId(
                secondReservation.getParkingUser().getParkingUserId());

        assertThat(reservations).asList().hasSize(1).contains(secondReservation);
    }

    @Test
    void findAllByParkingUserParkingUserId_notFound() {
        final long nonExistentParkingUserId = 500L;
        final Reservation firstReservation = createReservation("first-user");
        reservationRepo.save(firstReservation);
        final Reservation secondReservation = createReservation("second-user");
        reservationRepo.save(secondReservation);

        final List<Reservation> reservations = reservationRepo.findAllByParkingUserParkingUserId(nonExistentParkingUserId);

        assertThat(reservations).asList().isEmpty();
    }

    @Test
    void findActiveReservationOnSensorByParkingUser() {
        final Reservation firstReservation = createReservation("first-user");
        firstReservation.setActualReservationEndTime(null);
        reservationRepo.save(firstReservation);
        final Reservation secondReservation = createReservation("second-user");
        reservationRepo.save(secondReservation);

        final Optional<Reservation> reservation = reservationRepo.findReservationByParkingUserParkingUserIdAndSensorSensorIdAndActualReservationEndTimeNull(
                firstReservation.getParkingUser().getParkingUserId(), firstReservation.getSensor().getSensorId()
        );

        assertThat(reservation).contains(firstReservation);
    }

    @Test
    void findActiveReservationOnSensorByParkingUser_notFound() {
        final Reservation firstReservation = createReservation("first-user");
        reservationRepo.save(firstReservation);
        final Reservation secondReservation = createReservation("second-user");
        reservationRepo.save(secondReservation);

        final Optional<Reservation> reservation = reservationRepo.findReservationByParkingUserParkingUserIdAndSensorSensorIdAndActualReservationEndTimeNull(
                firstReservation.getParkingUser().getParkingUserId(), firstReservation.getSensor().getSensorId()
        );

        assertThat(reservation).isEmpty();
    }

    private Reservation createReservation(String userName) {
        Reservation reservation = new Reservation();
        reservation.setParkingUser(createParkingUser(userName));
        reservation.setSensor(sensorRepo.save(new Sensor()));
        reservation.setReservationDate(LocalDate.now());
        reservation.setReservationStartTime(LocalDateTime.now());
        reservation.setExpectedReservationEndTime(LocalDateTime.now().plusHours(2));
        reservation.setActualReservationEndTime(LocalDateTime.now().plusHours(2));
        return reservation;
    }

    private ParkingUser createParkingUser(final String userName) {
        ParkingUser parkingUser = new ParkingUser();
        parkingUser.setUserName(userName);
        parkingUser.setActive(true);
        return parkingUserRepo.save(parkingUser);
    }
}
