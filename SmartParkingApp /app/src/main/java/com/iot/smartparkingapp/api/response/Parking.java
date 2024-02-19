package com.iot.smartparkingapp.api.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Parking implements Serializable {

    private final Long parkingId;

    private final String location;

    private final List<Sensor> sensors = new ArrayList<>();

    public Parking(Long parkingId, String location) {
        this.parkingId = parkingId;
        this.location = location;
    }

    public Parking add(Sensor sensor) {
        this.sensors.add(sensor);
        return this;
    }

    public Long getParkingId() {
        return parkingId;
    }

    public String getLocation() {
        return location;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }
}
