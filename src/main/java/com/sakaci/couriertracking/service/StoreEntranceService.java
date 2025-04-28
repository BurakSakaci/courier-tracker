package com.sakaci.couriertracking.service;

import java.time.Instant;

import com.sakaci.couriertracking.domain.entity.Store;

public interface StoreEntranceService {

    void processPotentialEntrance(String courierId, Store store, double distance, Instant timestamp);
    
}
