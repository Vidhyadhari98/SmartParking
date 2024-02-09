package com.su.iot.smartparkingapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
@Table(name = "parking_user")
public class ParkingUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long parkingUserId;

    @NotNull
    private String userName;

    private boolean active;

    public long getParkingUserId() {
        return parkingUserId;
    }

    public ParkingUser setParkingUserId(long parkingUserId) {
        this.parkingUserId = parkingUserId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public ParkingUser setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public ParkingUser setActive(boolean active) {
        this.active = active;
        return this;
    }
}
