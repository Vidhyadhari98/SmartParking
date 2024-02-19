package com.iot.smartparkingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void switchToMapsActivity(View view) {
        TextView userNameInputView = findViewById(R.id.userNameInput);
        String userName = userNameInputView.getText().toString();
        Intent moveScreenIntent = new Intent(MainActivity.this, MapsActivity.class);
        moveScreenIntent.putExtra("userName", userName);
        startActivity(moveScreenIntent);
    }
}