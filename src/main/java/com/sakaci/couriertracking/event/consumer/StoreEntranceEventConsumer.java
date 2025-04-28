package com.sakaci.couriertracking.event.consumer;


import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.sakaci.couriertracking.event.StoreEntranceEvent;
import com.sakaci.couriertracking.event.consumer.listener.StoreEntranceEventListener;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreEntranceEventConsumer {

    private final List<StoreEntranceEventListener> listeners;

    @KafkaListener(topics = "${app.kafka.topics.store-entrances}")
    public void processStoreEntrance(StoreEntranceEvent event) {
        log.info("Processing store entrance event: {}", event);
        listeners.forEach(listener -> listener.onStoreEntrance(event));
    }
}
