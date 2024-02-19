package com.su.iot.smartparkingapi.entity;



import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parking")
public class Parking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
