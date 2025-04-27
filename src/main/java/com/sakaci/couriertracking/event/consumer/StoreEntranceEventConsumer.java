package com.sakaci.couriertracking.event.consumer;


import java.time.Instant;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.sakaci.couriertracking.domain.entity.StoreEntrance;
import com.sakaci.couriertracking.domain.repository.StoreEntranceRepository;
import com.sakaci.couriertracking.event.StoreEntranceEvent;

@Service
@RequiredArgsConstructor
public class StoreEntranceEventConsumer {

    private final StoreEntranceRepository entranceRepository;

    @KafkaListener(topics = "${app.kafka.topics.store-entrances}")
    public void processStoreEntrance(StoreEntranceEvent event) {
        // Save to database
        StoreEntrance entrance = new StoreEntrance();
        entrance.setCourierId(event.getCourierId());
        entrance.setStoreId(event.getStoreId());
        entrance.setEntranceTime(Instant.parse(event.getTimestamp()));
        entrance.setDistanceMeters(event.getDistance());
        
        entranceRepository.save(entrance);
        
        // Could also add additional processing here (notifications, etc.)
    }
}
