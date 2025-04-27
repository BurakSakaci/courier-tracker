package com.sakaci.couriertracking.service;

import java.time.Instant;

import com.sakaci.couriertracking.domain.entity.Store;

public interface StoreEntranceDetectionService {

    void processPotentialEntrance(String courierId, Store store, double distance, Instant timestamp);

}
