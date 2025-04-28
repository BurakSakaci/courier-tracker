package com.sakaci.couriertracking.event.consumer.listener;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sakaci.couriertracking.domain.entity.Store;
import com.sakaci.couriertracking.domain.entity.StoreEntrance;
import com.sakaci.couriertracking.domain.repository.StoreEntranceRepository;
import com.sakaci.couriertracking.event.StoreEntranceEvent;
import com.sakaci.couriertracking.service.CourierLocationService;
import com.sakaci.couriertracking.service.StoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class StoreEntrancePersistenceListener implements StoreEntranceEventListener {
    
    private final StoreEntranceRepository storeEntranceRepository;
    private final StoreService storeService;
    private final CourierLocationService courierLocationService;

    private static final long REENTRY_THRESHOLD_MINUTES = 1;

    @Override
    @Transactional
    public void onStoreEntrance(StoreEntranceEvent event) {
        
        checkStoreExists(event.getStoreId(), event.getCourierId());
        
        Optional<StoreEntrance> lastEntrance = storeEntranceRepository
            .findLastByCourierIdAndStoreId(event.getCourierId(), event.getStoreId());
            
        if (lastEntrance.isEmpty() || 
            isOutsideReentryWindow(lastEntrance.get(), event.getTimestamp())) {
            
            StoreEntrance entrance = new StoreEntrance();
            entrance.setCourierId(event.getCourierId());
            entrance.setStoreId(event.getStoreId());
            entrance.setEntranceTime(event.getTimestamp());
            
            storeEntranceRepository.save(entrance);
        }
        
    }


    private boolean isOutsideReentryWindow(StoreEntrance lastEntrance, Instant newTimestamp) {
        Instant lastTime = lastEntrance.getEntranceTime();
        return Duration.between(lastTime, newTimestamp).toMinutes() >= REENTRY_THRESHOLD_MINUTES;
    }
    
    private void checkStoreExists(UUID storeId, String courierId) {
        Store store = storeService.getAllStores().stream().filter(s -> s.getId().equals(storeId))
            .findAny()
            .orElseThrow(() -> new RuntimeException("Store not found"));
        courierLocationService.recordLocation(
            courierId,
            store.getLat(),
            store.getLng(),
            Instant.now()
        );
    }
    
}
