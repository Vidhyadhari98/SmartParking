package com.su.iot.smartparkingapi.service;

import com.su.iot.smartparkingapi.entity.Parking;

import java.util.List;
import java.util.Optional;

public interface ParkingService {

    Optional<Parking> findParkingByLocation(final String locationName);

    List<String> getAllParkingLocations();
}
