package com.su.iot.smartparkingapi.service;

import com.su.iot.smartparkingapi.entity.Parking;
import com.su.iot.smartparkingapi.repo.ParkingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParkingServiceImpl implements ParkingService {

    private final ParkingRepo parkingRepo;

    @Autowired
    public ParkingServiceImpl(ParkingRepo parkingRepo) {
        this.parkingRepo = parkingRepo;
    }

    @Override
    public Optional<Parking> findParkingByLocation(String locationName) {
        return parkingRepo.findParkingByLocation(locationName);
    }

    @Override
    public List<String> getAllParkingLocations() {
        return parkingRepo.findAll().stream()
                .map(Parking::getLocation)
                .collect(Collectors.toList());
    }
}
