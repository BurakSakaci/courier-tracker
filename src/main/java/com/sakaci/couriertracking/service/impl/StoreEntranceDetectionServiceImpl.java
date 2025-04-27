package com.sakaci.couriertracking.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sakaci.couriertracking.domain.entity.Store;
import com.sakaci.couriertracking.domain.entity.StoreEntrance;
import com.sakaci.couriertracking.domain.repository.StoreEntranceRepository;
import com.sakaci.couriertracking.event.StoreEntranceEvent;
import com.sakaci.couriertracking.event.producer.StoreEntranceEventProducer;
import com.sakaci.couriertracking.service.StoreEntranceDetectionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreEntranceDetectionServiceImpl implements StoreEntranceDetectionService {

    private final StoreEntranceEventProducer eventProducer;
    private final StoreEntranceRepository storeEntranceRepository;

    @Override
    public void processPotentialEntrance(String courierId, Store store, 
            double distance, Instant timestamp) {
        
        Optional<StoreEntrance> lastEntrance = storeEntranceRepository
            .findLastByCourierIdAndStoreId(courierId, store.getId());
            
        if (lastEntrance.isEmpty() || 
        Duration.between(lastEntrance.get().getEntranceTime(), timestamp).toMinutes() >= 1) {
            
            StoreEntranceEvent event = new StoreEntranceEvent(
                courierId,
                store.getId(),
                store.getName(),
                distance,
                timestamp.toString()
            );
            
            eventProducer.sendStoreEntranceEvent(event);
        }
    }
}