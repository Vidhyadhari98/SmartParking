package com.su.iot.smartparkingapi.service;

import com.su.iot.smartparkingapi.entity.Booking;
import com.su.iot.smartparkingapi.entity.Sensor;
import com.su.iot.smartparkingapi.repo.BookingRepo;
import com.su.iot.smartparkingapi.repo.SensorRepo;
import com.su.iot.smartparkingapi.request.BookingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;
    private final SensorRepo sensorRepo;

    @Autowired
    public BookingServiceImpl(BookingRepo bookingRepo,
                              SensorRepo sensorRepo) {
        this.bookingRepo = bookingRepo;
        this.sensorRepo = sensorRepo;
    }

    @Override
    @Transactional
    public void createBooking(final BookingData bookingData) {
        Booking booking = new Booking()
                .setBookingDate(LocalDate.now())
                .setBookingStartTime(LocalDateTime.now())
                .setExpectedBookingEndTime(LocalDateTime.now().plusHours(2))
                .setSensor(getSensor(bookingData));

        bookingRepo.save(booking);
    }

    @Override
    @Transactional
    public void releaseBooking(Long sensorId) {
        final Optional<Booking> reservationOptional = getActiveBookingFor(sensorId);
        if (reservationOptional.isEmpty()) {
            throw new IllegalStateException("Active reservation does not exist for sensor " + sensorId);
        }

        reservationOptional.get().setActualBookingEndTime(LocalDateTime.now());
    }

    @Override
    public Optional<Booking> getActiveBookingFor(Long sensorId) {
        return bookingRepo.findBookingBySensorSensorIdAndActualBookingEndTimeNull(sensorId);
    }

    private Sensor getSensor(BookingData bookingData) {
        return sensorRepo.findById(bookingData.getSensorId())
                .orElseThrow(() -> new IllegalArgumentException("Sensor not exists for id " + bookingData.getSensorId()));
    }
}
