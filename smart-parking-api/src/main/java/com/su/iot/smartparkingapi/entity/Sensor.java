package com.su.iot.smartparkingapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "sensor")
public class Sensor implements Serializable {

    @Id
    @GeneratedValue
    private Long sensorId;

    private String sensorName;

    public long getSensorId() {
        return sensorId;
    }

    public Sensor setSensorId(long sensorId) {
        this.sensorId = sensorId;
        return this;
    }

    public String getSensorName() {
        return sensorName;
    }

    public Sensor setSensorName(String sensorName) {
        this.sensorName = sensorName;
        return this;
    }
}
