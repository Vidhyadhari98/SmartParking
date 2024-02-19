package com.iot.smartparkingapp;

public enum Coordinates {
    SOLNA(59.3689, 18.0084),
    MORBY(59.3999, 18.0503),
    SUNDBYBERG(59.3693, 17.9609);

    private final double latitude;
    private final double longitude;

    Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
