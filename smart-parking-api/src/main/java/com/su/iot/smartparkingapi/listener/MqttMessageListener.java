package com.su.iot.smartparkingapi.listener;

import com.su.iot.smartparkingapi.config.Mqtt;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqttMessageListener {
    private static final Logger logger = LoggerFactory.getLogger(MqttMessageListener.class);

    private final MessageProcessor messageProcessor;

    @Autowired
    public MqttMessageListener(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    public void listenToTopic(String topicName) {
        logger.info("Listening to topic {}", topicName);
        IMqttClient instance = Mqtt.getInstance();
        subscribeToTopic(instance, topicName);
        instance.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                logger.error("Connection lost: ", throwable);
            }

            @Override
            public void messageArrived(String topicName, MqttMessage mqttMessage) {
                logger.info("Message received --->>>>> {}", new String(mqttMessage.getPayload()));
                messageProcessor.processMessage(new String(mqttMessage.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

    private void subscribeToTopic(IMqttClient mqttClient, String topic) {
        try {
            mqttClient.subscribe(topic, 1);
        } catch (Exception e) {
            logger.error("Error subscribing to topic: ", e);
        }
    }
}
