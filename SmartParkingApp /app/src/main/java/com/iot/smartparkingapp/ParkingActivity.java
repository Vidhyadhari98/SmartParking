package com.iot.smartparkingapp;

import static com.iot.smartparkingapp.AppConstants.BROKER_URL;
import static com.iot.smartparkingapp.AppConstants.CLIENT_ID;
import static com.iot.smartparkingapp.AppConstants.LOCATION_NAME;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.iot.smartparkingapp.api.response.Parking;
import com.iot.smartparkingapp.api.response.Sensor;
import com.iot.smartparkingapp.view.ParkingView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.json.JSONArray;
import org.json.JSONObject;


public class ParkingActivity extends AppCompatActivity {

    private ParkingView parkingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);

        Parking parking = getIntent().getSerializableExtra("parking", Parking.class);

        TextView topText = findViewById(R.id.selectParkingHeaderText);
        topText.setText(LOCATION_NAME);

        parkingView = findViewById(R.id.parkingView);
        parkingView.setParking(parking);

        connect(parking);
    }

    public void connect(Parking parking) {
        try (MqttClient mqttClient = new MqttClient(BROKER_URL, CLIENT_ID, null)) {
            mqttClient.connect(getMqttConnectOptions());
            mqttClient.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable cause) {
                    Log.e(getClass().getName(), "Connection lost: ", cause);
                }

                @Override
                public void messageArrived(String topic, org.eclipse.paho.client.mqttv3.MqttMessage message) throws Exception {
                    Log.d(getClass().getName(), "Message received: " + new String(message.getPayload()));

                    parking.getSensors().forEach(sensor -> sensor.setOccupied(false));
                    JSONObject event = new JSONObject(message.toString());
                    /*JSONArray availableSlotsId = event.getJSONArray("availableSlotsId");
                    for (int i = 0; i < availableSlotsId.length(); i++) {
                        int sensorId = (int) availableSlotsId.get(i);
                        Sensor currentSensor = getSensor(sensorId, parking);
                        currentSensor.setOccupied(false);
                    }*/

                    JSONArray occupiedSlotsId = event.getJSONArray("occupiedSlotsId");
                    for (int i = 0; i < occupiedSlotsId.length(); i++) {
                        int sensorId = (int) occupiedSlotsId.get(i);
                        Sensor currentSensor = getSensor(sensorId, parking);
                        currentSensor.setOccupied(true);
                    }

                    parkingView.invalidate();
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // Not used in this example
                }
            });

            subscribeToTopic(mqttClient, "parking/solna/" + parking.getParkingId());

        } catch (Exception e) {
            Log.e(getClass().getName(), "Error connecting to MQTT broker: ", e);
        }
    }

    private static Sensor getSensor(long sensorId, Parking parking) {
        return parking.getSensors().stream()
                .filter(sensor -> sensor.getSensorId() == sensorId)
                .findFirst()
                .orElseThrow();
    }

    private static MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        return options;
    }

    private void subscribeToTopic(MqttClient mqttClient, String topic) {
        try {
            mqttClient.subscribe(topic, 1);
            Log.d(getClass().getName(), "Subscribed to topic: " + topic);
        } catch (Exception e) {
            Log.e(getClass().getName(), "Error subscribing to topic: ", e);
        }
    }
}