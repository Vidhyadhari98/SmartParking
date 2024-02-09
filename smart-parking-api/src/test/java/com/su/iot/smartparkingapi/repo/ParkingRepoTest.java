package com.su.iot.smartparkingapi.repo;

import com.su.iot.smartparkingapi.entity.Parking;
import com.su.iot.smartparkingapi.entity.Sensor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ParkingRepoTest {

    @Autowired
    private SensorRepo sensorRepo;
    @Autowired
    private ParkingRepo parkingRepo;

    @Test
    void save() {
        Parking parking = new Parking();
        parking.setLocation("kista");
        parking.setSensors(List.of(sensorRepo.save(new Sensor())));

        final Parking savedParking = parkingRepo.save(parking);

        final Optional<Parking> foundParking = parkingRepo.findById(savedParking.getParkingId());

        assertThat(foundParking).isNotEmpty();
    }

    @Test
    void getParkingByLocation() {
        createParking("kista");
        final Parking expectedParking = createParking("solna");

        final Optional<Parking> parkingOptional = parkingRepo.findParkingByLocation("solna");

        assertThat(parkingOptional).isNotEmpty().contains(expectedParking);
    }

    @Test
    void getParkingByLocation_notFound() {
        createParking("kista");
        createParking("solna");

        final Optional<Parking> parkingOptional = parkingRepo.findParkingByLocation("stockholm");

        assertThat(parkingOptional).isEmpty();
    }

    private Parking createParking(final String locationName) {
        Parking parking = new Parking();
        parking.setLocation(locationName);
        parking.setSensors(List.of(sensorRepo.save(new Sensor())));

        return parkingRepo.save(parking);
    }

}
