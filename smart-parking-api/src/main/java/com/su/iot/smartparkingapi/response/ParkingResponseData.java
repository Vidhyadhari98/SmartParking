package com.su.iot.smartparkingapi.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ParkingResponseData implements Serializable {

    private final Long parkingId;

    private final String location;

    private final List<SensorResponseData> sensors = new ArrayList<>();

    public ParkingResponseData(Long parkingId, String location) {
        this.parkingId = parkingId;
        this.location = location;
    }

    public ParkingResponseData add(SensorResponseData sensorResponseData) {
        this.sensors.add(sensorResponseData);
        return this;
    }

    public Long getParkingId() {
        return parkingId;
    }

    public String getLocation() {
        return location;
    }

    public List<SensorResponseData> getSensors() {
        return sensors;
    }
}
