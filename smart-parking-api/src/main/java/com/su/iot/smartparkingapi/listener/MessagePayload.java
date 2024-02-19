package com.su.iot.smartparkingapi.listener;

import java.io.Serializable;
import java.util.List;

public class MessagePayload implements Serializable {

    private String id;

    private int occupiedSlots;

    private List<Long> occupiedSlotsId;

    private int availableSlots;

    private List<Long> availableSlotsId;

    private int totalSlots;

    public String getId() {
        return id;
    }

    public MessagePayload setId(String id) {
        this.id = id;
        return this;
    }

    public int getOccupiedSlots() {
        return occupiedSlots;
    }

    public MessagePayload setOccupiedSlots(int occupiedSlots) {
        this.occupiedSlots = occupiedSlots;
        return this;
    }

    public List<Long> getOccupiedSlotsId() {
        return occupiedSlotsId;
    }

    public MessagePayload setOccupiedSlotsId(List<Long> occupiedSlotsId) {
        this.occupiedSlotsId = occupiedSlotsId;
        return this;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }

    public MessagePayload setAvailableSlots(int availableSlots) {
        this.availableSlots = availableSlots;
        return this;
    }

    public List<Long> getAvailableSlotsId() {
        return availableSlotsId;
    }

    public MessagePayload setAvailableSlotsId(List<Long> availableSlotsId) {
        this.availableSlotsId = availableSlotsId;
        return this;
    }

    public int getTotalSlots() {
        return totalSlots;
    }

    public MessagePayload setTotalSlots(int totalSlots) {
        this.totalSlots = totalSlots;
        return this;
    }
}
