package com.su.iot.smartparkingapi.config;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mqtt {
    private static final Logger logger = LoggerFactory.getLogger(Mqtt.class);

    private static final String MQTT_PUBLISHER_ID = "smart-parking-api";
    private static final String MQTT_SERVER_ADDRESS = "tcp://test.mosquitto.org:1883";
    private static IMqttClient instance;

    public static IMqttClient getInstance() {
        try {
            if (instance == null) {
                instance = new MqttClient(MQTT_SERVER_ADDRESS, MQTT_PUBLISHER_ID);
            }

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            if (!instance.isConnected()) {
                logger.info("mqtt client disconnected. Will reconnect now.");
                instance.connect(options);
            }

        } catch (MqttException e) {
            logger.error("exception while creating mqtt instance ", e);
        }

        return instance;
    }

    private Mqtt() {
    }
}