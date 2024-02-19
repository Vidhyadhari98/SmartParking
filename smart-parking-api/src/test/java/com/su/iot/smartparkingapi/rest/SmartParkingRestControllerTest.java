package com.su.iot.smartparkingapi.rest;

import com.su.iot.smartparkingapi.entity.Parking;
import com.su.iot.smartparkingapi.entity.Sensor;
import com.su.iot.smartparkingapi.repo.ParkingRepo;
import com.su.iot.smartparkingapi.repo.SensorRepo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SmartParkingRestControllerTest {

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private SensorRepo sensorRepo;
    @Autowired
    private ParkingRepo parkingRepo;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Order(1)
    @Test
    void getParkingByLocation() {
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            final Sensor savedSensor = sensorRepo.save(new Sensor());
            Parking parking = new Parking();
            parking.setLocation("test-location");
            parking.setSensors(List.of(savedSensor));
            parkingRepo.save(parking);
        });

        get("/parking/test-location")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body("parkingId", notNullValue())
                .body("location", equalTo("test-location"))
                .body("sensors", hasSize(1));
    }

    @Order(1)
    @Test
    void getParkingByLocation_notfound() {
        get("/parking/kista")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}