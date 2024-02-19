package com.su.iot.smartparkingapi.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "sensor")
public class Sensor implements Serializable {

    @Id
    @GeneratedValue
    private Long sensorId;

    private String sensorName;

    public Long getSensorId() {
        return sensorId;
    }

    public Sensor setSensorId(Long sensorId) {
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
