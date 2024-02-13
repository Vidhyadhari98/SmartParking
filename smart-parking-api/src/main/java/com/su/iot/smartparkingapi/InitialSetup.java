package com.su.iot.smartparkingapi;

import com.su.iot.smartparkingapi.entity.Parking;
import com.su.iot.smartparkingapi.entity.Sensor;
import com.su.iot.smartparkingapi.listener.MqttMessageListener;
import com.su.iot.smartparkingapi.repo.ParkingRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class InitialSetup {

    @Bean
    CommandLineRunner initDatabase(final ParkingRepo parkingRepo,
                                   final MqttMessageListener mqttMessageListener) {
        return args -> {
            createParkingIfNotExists("solna",
                    List.of(
                            createSensorIfExists("sensor-1"),
                            createSensorIfExists("sensor-2"),
                            createSensorIfExists("sensor-3"),
                            createSensorIfExists("sensor-4")),
                    parkingRepo);

            parkingRepo.findAll()
                    .forEach(parking -> mqttMessageListener.listenToTopic("parking/" + parking.getLocation() + "/" + parking.getParkingId()));
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
}