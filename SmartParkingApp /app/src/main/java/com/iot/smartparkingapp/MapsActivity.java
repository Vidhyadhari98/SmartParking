package com.iot.smartparkingapp;

import static com.iot.smartparkingapp.AppConstants.LOCATION_NAME;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iot.smartparkingapp.api.ApiService;
import com.iot.smartparkingapp.api.RetrofitClient;
import com.iot.smartparkingapp.api.response.Parking;
import com.iot.smartparkingapp.databinding.ActivityMapsBinding;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Arrays.stream(Coordinates.values())
                .forEach(coordinates -> {
                    LatLng latLng = new LatLng(coordinates.getLatitude(), coordinates.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in " + coordinates.name()));
                });

        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                        new LatLng(Coordinates.SOLNA.getLatitude(), Coordinates.SOLNA.getLongitude()),
                        12.0f)
        );

        mMap.setOnMarkerClickListener(marker -> {
            switchToParkingActivity();
            return true;
        });
    }

    public void switchToParkingActivity() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<Parking> call = apiService.getParkingByLocation(LOCATION_NAME.toLowerCase());
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Parking> call, Response<Parking> response) {
                if (response.isSuccessful()) {
                    Parking parking = response.body();
                    Intent moveScreenIntent = new Intent(MapsActivity.this, ParkingActivity.class);
                    moveScreenIntent.putExtra("parking", parking);
                    startActivity(moveScreenIntent);
                } else {
                    Log.e("API Error", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Parking> call, Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
            }
        });
    }
}