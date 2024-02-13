package com.su.iot.smartparkingapi.rest;

import com.su.iot.smartparkingapi.entity.Parking;
import com.su.iot.smartparkingapi.response.ParkingResponseData;
import com.su.iot.smartparkingapi.response.SensorResponseData;
import com.su.iot.smartparkingapi.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class SmartParkingRestController {

    private final ParkingService parkingService;

    @Autowired
    public SmartParkingRestController(ParkingService parkingService) {
        this.parkingService = parkingService;
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

    private static ParkingResponseData map(Parking parking) {
        ParkingResponseData parkingResponseData = new ParkingResponseData(parking.getParkingId(), parking.getLocation());
        parking.getSensors().stream()
                .map(sensor -> new SensorResponseData(sensor.getSensorId()))
                .forEach(parkingResponseData::add);

        return parkingResponseData;
    }
}
