package com.sakaci.couriertracking.service.impl;

import java.util.List;

import com.sakaci.couriertracking.domain.entity.Store;
import com.sakaci.couriertracking.domain.repository.StoreRepository;
import com.sakaci.couriertracking.service.StoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreServiceImpl implements StoreService{

    private final StoreRepository storeRepository;

    @Override
    public List<Store> findNearbyStores(Double lat, Double lng, double proximityThresholdMeters) {
        return storeRepository.findNearbyStores(lat, lng, proximityThresholdMeters);
    }
    
}
