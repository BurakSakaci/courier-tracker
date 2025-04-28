package com.sakaci.couriertracking.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.sakaci.couriertracking.domain.entity.Store;
import com.sakaci.couriertracking.domain.repository.StoreRepository;
import com.sakaci.couriertracking.service.DistanceCalculatorService;
import com.sakaci.couriertracking.service.StoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final DistanceCalculatorService distanceCalculatorService;

    @Cacheable("stores")
    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public List<Store> findNearbyStores(Double lat, Double lng, double proximityThresholdMeters) {
        return getAllStores().stream()
            .filter(store -> distanceCalculatorService.calculateDistance(
                lat, lng, 
                store.getLat(), store.getLng()) <= proximityThresholdMeters)
            .collect(Collectors.toList());
    }
    
}
