package com.su.iot.smartparkingapi.listener;

import com.google.gson.Gson;
import com.su.iot.smartparkingapi.entity.Booking;
import com.su.iot.smartparkingapi.request.BookingData;
import com.su.iot.smartparkingapi.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MessageProcessor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final BookingService bookingService;

    @Autowired
    public MessageProcessor(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void processMessage(String message) {
        Gson gson = new Gson();
        final MessagePayload messagePayload = gson.fromJson(message, MessagePayload.class);
        for (Long availableSlotId : messagePayload.getAvailableSlotsId()) {
            // if there is an active reservation for this sensor
            // if yes then release reservation
            Optional<Booking> reservationOptional = bookingService.getActiveBookingFor(availableSlotId);
            if (reservationOptional.isPresent()) {
                logger.info("Booking present for sensor id {}. Will release it", availableSlotId);
                bookingService.releaseBooking(availableSlotId);
            } else {
                logger.info("Booking not present for sensor id {}.", availableSlotId);
            }
        }

        for (Long occupiedSlotId : messagePayload.getOccupiedSlotsId()) {
            // if there is no active reservation for this sensor
            // then create a reservation
            Optional<Booking> reservationOptional = bookingService.getActiveBookingFor(occupiedSlotId);
            if (reservationOptional.isEmpty()) {
                logger.info("Booking not present for sensor id {}. Will create one", occupiedSlotId);
                bookingService.createBooking(new BookingData().setSensorId(occupiedSlotId));
            } else {
                logger.info("Booking present for sensor id {}.", occupiedSlotId);
            }
        }
    }
}