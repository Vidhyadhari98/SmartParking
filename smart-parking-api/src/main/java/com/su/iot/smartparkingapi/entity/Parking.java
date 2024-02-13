package com.su.iot.smartparkingapi.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parking")
public class Parking implements Serializable {

    @Id
    @GeneratedValue
    private Long parkingId;

    @NotNull
    private String location;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Sensor> sensors = new ArrayList<>();

    public Long getParkingId() {
        return parkingId;
    }

    public Parking setParkingId(Long parkingId) {
        this.parkingId = parkingId;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Parking setLocation(String location) {
        this.location = location;
        return this;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public Parking setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
        return this;
    }
}
