package com.su.iot.smartparkingapi.response;

import java.io.Serializable;

public class SensorResponseData implements Serializable {

    private final Long sensorId;

    public SensorResponseData(Long sensorId) {
        this.sensorId = sensorId;
    }

    public long getSensorId() {
        return sensorId;
    }
}
