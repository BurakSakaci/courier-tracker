package com.sakaci.couriertracking.service;

import com.sakaci.couriertracking.domain.entity.Store;

public interface StoreEntranceService {

    void processPotentialEntrance(String courierId, Store store, double distance, String timestamp);
    
}
