package com.sakaci.couriertracking.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sakaci.couriertracking.domain.entity.Store;
import com.sakaci.couriertracking.domain.entity.StoreEntrance;
import com.sakaci.couriertracking.domain.repository.StoreEntranceRepository;
import com.sakaci.couriertracking.service.StoreEntranceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreEntranceServiceImpl implements StoreEntranceService {
    
    private final StoreEntranceRepository entranceRepository;
    private static final long REENTRY_THRESHOLD_MINUTES = 1;

    @Transactional
    @Override
    public void processPotentialEntrance(String courierId, Store store, 
                                       double distance, String timestamp) {
        Optional<StoreEntrance> lastEntrance = entranceRepository
            .findLastByCourierIdAndStoreId(courierId, store.getId());
            
        if (lastEntrance.isEmpty() || 
            isOutsideReentryWindow(lastEntrance.get(), timestamp)) {
            
            StoreEntrance entrance = new StoreEntrance();
            entrance.setCourierId(courierId);
            entrance.setStoreId(store.getId());
            entrance.setDistanceMeters(distance);
            entrance.setEntranceTime(Instant.parse(timestamp));
            
            entranceRepository.save(entrance);
        }
    }

    private boolean isOutsideReentryWindow(StoreEntrance lastEntrance, String newTimestamp) {
        Instant lastTime = lastEntrance.getEntranceTime();
        Instant newTime = Instant.parse(newTimestamp);
        return Duration.between(lastTime, newTime).toMinutes() >= REENTRY_THRESHOLD_MINUTES;
    }
}
