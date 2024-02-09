package com.su.iot.smartparkingapi.rest;

import com.su.iot.smartparkingapi.entity.Parking;
import com.su.iot.smartparkingapi.entity.Reservation;
import com.su.iot.smartparkingapi.request.ReservationData;
import com.su.iot.smartparkingapi.response.ParkingResponseData;
import com.su.iot.smartparkingapi.response.ReservationResponseData;
import com.su.iot.smartparkingapi.response.SensorResponseData;
import com.su.iot.smartparkingapi.service.ParkingService;
import com.su.iot.smartparkingapi.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class SmartParkingRestController {

    private final ParkingService parkingService;
    private final ReservationService reservationService;

    @Autowired
    public SmartParkingRestController(ParkingService parkingService,
                                      ReservationService reservationService) {
        this.parkingService = parkingService;
        this.reservationService = reservationService;
    }

    @GetMapping("/parking-locations")
    public ResponseEntity<List<String>> getAllParkingLocations() {
        final List<String> allParkingLocations = parkingService.getAllParkingLocations();
        if (allParkingLocations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(new ArrayList<>(allParkingLocations));
    }

    @GetMapping("/parking/{locationName}")
    public ResponseEntity<ParkingResponseData> getParkingByLocation(@PathVariable("locationName") String locationName) {
        final Optional<Parking> parkingByLocation = parkingService.findParkingByLocation(locationName);

        return parkingByLocation.map(parking -> ResponseEntity.ok(map(parking)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/active/reservation/{sensorId}")
    public ResponseEntity<ReservationResponseData> getActiveReservation(@PathVariable("sensorId") Long sensorId) {
        try {
            Optional<Reservation> reservationOptional = reservationService.getActiveReservationFor(sensorId);
            if (reservationOptional.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            Reservation activeReservation = reservationOptional.get();
            return ResponseEntity.ok(map(activeReservation));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/reservation/create")
    public ResponseEntity<Object> createReservation(@RequestBody ReservationData reservationData) {
        try {
            reservationService.createReservation(reservationData);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/reservation/release/{sensorId}")
    public ResponseEntity<Object> releaseReservation(@PathVariable("sensorId") final Long sensorId) {
        try {
            reservationService.releaseReservation(sensorId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private static ParkingResponseData map(Parking parking) {
        ParkingResponseData parkingResponseData = new ParkingResponseData(parking.getParkingId(), parking.getLocation());
        parking.getSensors().stream()
                .map(sensor -> new SensorResponseData(sensor.getSensorId()))
                .forEach(parkingResponseData::add);

        return parkingResponseData;
    }

    private static ReservationResponseData map(Reservation activeReservation) {
        ReservationResponseData reservationResponseData = new ReservationResponseData();
        reservationResponseData.setReservationId(activeReservation.getReservationId());
        reservationResponseData.setParkingUserId(activeReservation.getParkingUser().getParkingUserId());
        reservationResponseData.setSensor(new SensorResponseData(activeReservation.getSensor().getSensorId()));
        reservationResponseData.setReservationDate(activeReservation.getReservationDate());
        reservationResponseData.setReservationStartTime(activeReservation.getReservationStartTime());
        reservationResponseData.setExpectedReservationEndTime(activeReservation.getExpectedReservationEndTime());
        reservationResponseData.setActualReservationEndTime(activeReservation.getActualReservationEndTime());
        return reservationResponseData;
    }
}
