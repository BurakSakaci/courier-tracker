package com.sakaci.couriertracking.event.producer;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sakaci.couriertracking.event.StoreEntranceEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreEntranceEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${app.kafka.topics.store-entrances}")
    private String storeEntrancesTopic;

    public void sendStoreEntranceEvent(StoreEntranceEvent event) {
        kafkaTemplate.send(storeEntrancesTopic, event.getCourierId(), event);
    }
}
