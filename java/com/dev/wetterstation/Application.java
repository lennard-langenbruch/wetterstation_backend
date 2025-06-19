package com.dev.wetterstation;

import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@SpringBootApplication
@RestController
public class Application {

    @Value("${mqtt.broker}")
    private String broker;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;
    
    @Autowired
    private EventRepository repository;
    	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    /** Verbindung zum MQTT Broker > "Subscribe" Sensordaten Topic > Speichern der Sensordaten zu H2 Datenbank bei Event */
    @PostConstruct
    public void mqtt() {
        try {
            String url = "ssl://"+broker;
            String clientId = "client";
            String topic = "esp32/json";

            MqttClient client = new MqttClient(url, clientId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setCleanSession(true);

            client.connect(options);
            System.out.println("Connected to MQTT broker: " + url);

            client.subscribe(topic, (t, message) -> {
                String payload = new String(message.getPayload());
                System.out.println("Received on " + t + ": " + payload);

                
                System.out.println("test");
                try {
                	
                EventDTO dto = new ObjectMapper().readValue(payload, EventDTO.class);
                
                Event event = new Event();
                event.setCelsius(dto.temperature);
                event.setHumidity(dto.humidity);
                event.setRecorded(dto.timestamp);
                event.setReceived(LocalDateTime.now());
                
                repository.save(event);
                
                } catch(Exception ex) { System.out.print(ex.getMessage()); };
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
