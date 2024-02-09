package com.su.iot.smartparkingapi.repo;

import com.su.iot.smartparkingapi.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepo extends JpaRepository<Sensor, Long> {
}
