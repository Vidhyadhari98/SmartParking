package com.su.iot.smartparkingapi;

import com.su.iot.smartparkingapi.entity.Parking;
import com.su.iot.smartparkingapi.entity.ParkingUser;
import com.su.iot.smartparkingapi.entity.Sensor;
import com.su.iot.smartparkingapi.repo.ParkingRepo;
import com.su.iot.smartparkingapi.repo.ParkingUserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class SetupInitialData {

    @Bean
    CommandLineRunner initDatabase(final ParkingUserRepo parkingUserRepo,
                                   final ParkingRepo parkingRepo) {
        return args -> {
            createParkingUserIfNotExists("test-user-1", parkingUserRepo);
            createParkingUserIfNotExists("test-user-2", parkingUserRepo);

            createParkingIfNotExists("solna",
                    List.of(
                            createSensorIfExists("sensor-1"),
                            createSensorIfExists("sensor-2"),
                            createSensorIfExists("sensor-3"),
                            createSensorIfExists("sensor-4")),
                    parkingRepo);
        };
    }

    private static void createParkingIfNotExists(String locationName,
                                                 List<Sensor> sensors,
                                                 ParkingRepo parkingRepo) {
        Optional<Parking> parkingLocationOptional = parkingRepo.findParkingByLocation(locationName);
        if (parkingLocationOptional.isEmpty()) {
            Parking parking = new Parking()
                    .setLocation(locationName)
                    .setSensors(sensors);
            parkingRepo.save(parking);
        }
    }

    private static Sensor createSensorIfExists(String sensorName) {
        return new Sensor().setSensorName(sensorName);
    }

    private static void createParkingUserIfNotExists(String userName,
                                                     ParkingUserRepo parkingUserRepo) {
        Optional<ParkingUser> parkingUserOptional = parkingUserRepo.findParkingUserByUserName(userName);
        if (parkingUserOptional.isEmpty()) {
            ParkingUser firstParkingUser = new ParkingUser()
                    .setUserName(userName)
                    .setActive(true);
            parkingUserRepo.save(firstParkingUser);
        }
    }
}
