package com.iot.smartparkingapp.api.response;

import java.io.Serializable;

public class Sensor implements Serializable {

    private final Long sensorId;

    private boolean occupied;

    public Sensor(Long sensorId) {
        this.sensorId = sensorId;
    }

    public long getSensorId() {
        return sensorId;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
