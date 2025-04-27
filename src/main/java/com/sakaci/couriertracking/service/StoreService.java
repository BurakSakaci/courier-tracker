package com.sakaci.couriertracking.service;

import java.util.List;

import com.sakaci.couriertracking.domain.entity.Store;

public interface StoreService {

    List<Store> findNearbyStores(Double lat, Double lng, double proximityThresholdMeters);
    
}
