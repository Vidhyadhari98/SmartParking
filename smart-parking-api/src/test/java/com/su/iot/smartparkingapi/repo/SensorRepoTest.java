package com.su.iot.smartparkingapi.repo;

import com.su.iot.smartparkingapi.entity.Sensor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SensorRepoTest {

    @Autowired
    private SensorRepo sensorRepo;

    @Test
    void save() {
        Sensor sensor = new Sensor().setSensorName("sensor-1");

        final Sensor savedSensor = sensorRepo.save(sensor);

        final Optional<Sensor> foundSensor = sensorRepo.findById(savedSensor.getSensorId());

        assertThat(foundSensor).isNotEmpty();
    }

}