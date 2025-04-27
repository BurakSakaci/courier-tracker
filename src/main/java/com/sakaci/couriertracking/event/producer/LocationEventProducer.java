package com.sakaci.couriertracking.event.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.sakaci.couriertracking.event.LocationUpdateEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${app.kafka.topics.location-updates}")
    private String locationUpdatesTopic;

    public void sendLocationUpdate(LocationUpdateEvent event) {
        kafkaTemplate.send(locationUpdatesTopic, event.getCourierId(), event);
    }
}
