package com.iot.smartparkingapp.api;

import com.iot.smartparkingapp.api.response.Parking;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("/parking/{locationName}")
    Call<Parking> getParkingByLocation(@Path("locationName") String locationName);
}
