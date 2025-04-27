package com.sakaci.couriertracking.service.impl;

import java.time.Instant;

import org.springframework.stereotype.Service;

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
    
    private final StoreEntranceRepository storeEntranceRepository;

    @Override
    public void processPotentialEntrance(String courierId, Store store, double distance, String timestamp) {
        StoreEntrance storeEntrance = new StoreEntrance();
        storeEntrance.setCourierId(courierId);
        storeEntrance.setStoreId(store.getId());
        storeEntrance.setDistanceMeters(distance);
        storeEntrance.setEntranceTime(Instant.parse(timestamp));
        storeEntranceRepository.save(storeEntrance);
        log.info("Recorded potential entrance for courier: {}, store: {}, distance: {}, timestamp: {}", courierId, store.toString(), distance, timestamp);
    }
}
