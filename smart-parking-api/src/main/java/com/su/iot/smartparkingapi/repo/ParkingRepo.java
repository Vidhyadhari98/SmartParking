package com.su.iot.smartparkingapi.repo;

import com.su.iot.smartparkingapi.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingRepo extends JpaRepository<Parking, Long> {

    Optional<Parking> findParkingByLocation(final String locationName);
}
