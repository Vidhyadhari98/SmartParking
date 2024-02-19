package com.su.iot.smartparkingapi.repo;

import com.su.iot.smartparkingapi.entity.Booking;
import com.su.iot.smartparkingapi.entity.Sensor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookingRepoTest {

    @Autowired
    private SensorRepo sensorRepo;
    @Autowired
    private BookingRepo bookingRepo;

    @Test
    void save() {
        final Booking booking = createBooking();

        final Booking saveBooking = bookingRepo.save(booking);

        final Optional<Booking> bookingOptional = bookingRepo.findById(saveBooking.getBookingId());

        assertThat(bookingOptional).isNotEmpty();
    }

    @Test
    void findActiveBookingOnSensor() {
        final Booking firstBooking = createBooking();
        firstBooking.setActualBookingEndTime(null);
        bookingRepo.save(firstBooking);
        final Booking secondBooking = createBooking();
        bookingRepo.save(secondBooking);

        final Optional<Booking> booking = bookingRepo.findBookingBySensorSensorIdAndActualBookingEndTimeNull(
               firstBooking.getSensor().getSensorId());

        assertThat(booking).contains(firstBooking);
    }

    @Test
    void findActiveBookingOnSensor_notFound() {
        final Booking firstBooking = createBooking();
        bookingRepo.save(firstBooking);
        final Booking secondBooking = createBooking();
        bookingRepo.save(secondBooking);

        final Optional<Booking> booking = bookingRepo.findBookingBySensorSensorIdAndActualBookingEndTimeNull(
                firstBooking.getSensor().getSensorId());

        assertThat(booking).isEmpty();
    }

    private Booking createBooking() {
        Booking booking = new Booking();
        booking.setSensor(sensorRepo.save(new Sensor()));
        booking.setBookingDate(LocalDate.now());
        booking.setBookingStartTime(LocalDateTime.now());
        booking.setExpectedBookingEndTime(LocalDateTime.now().plusHours(2));
        booking.setActualBookingEndTime(LocalDateTime.now().plusHours(2));
        return booking;
    }
}
