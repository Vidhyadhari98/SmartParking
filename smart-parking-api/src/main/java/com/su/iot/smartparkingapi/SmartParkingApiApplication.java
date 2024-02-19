package com.su.iot.smartparkingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.su.iot.smartparkingapi.entity")
public class SmartParkingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartParkingApiApplication.class, args);
    }

}
