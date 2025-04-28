package com.sakaci.couriertracking.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sakaci.couriertracking.domain.entity.CourierLocation;
import com.sakaci.couriertracking.domain.repository.CourierLocationRepository;
import com.sakaci.couriertracking.service.CourierLocationService;
import com.sakaci.couriertracking.service.DistanceCalculatorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourierLocationServiceImpl implements CourierLocationService {
    
    private final CourierLocationRepository courierLocationRepository;
    private final DistanceCalculatorService distanceCalculatorService;

    @Override
    @Transactional
    public void recordLocation(String courierId, Double lat, Double lng, Instant timestamp) {
        CourierLocation courierLocation = new CourierLocation();
        courierLocation.setCourierId(courierId);
        courierLocation.setLat(lat);
        courierLocation.setLng(lng);
        courierLocation.setTimestamp(timestamp);
        courierLocationRepository.save(courierLocation);
        log.info("Recorded location for courier: {}, at: {}", courierId, timestamp);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalTravelDistance(String courierId) {
        List<CourierLocation> locations = courierLocationRepository
            .findAllByCourierIdOrderByTimestampAsc(courierId);
            
        if (locations.size() < 2) {
            log.info("Not enough locations to calculate distance for courier: {}", courierId);
            return 0.0;
        }
        
        double totalDistance = 0.0;
        CourierLocation previousLocation = locations.get(0);
        
        for (int i = 1; i < locations.size(); i++) {
            CourierLocation currentLocation = locations.get(i);
            totalDistance += distanceCalculatorService.calculateDistance(
                previousLocation.getLat(),
                previousLocation.getLng(),
                currentLocation.getLat(),
                currentLocation.getLng()
            );
            previousLocation = currentLocation;
        }
        
        return totalDistance;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Double getLastTravelDistance(String courierId) {
        Optional<CourierLocation> latestLocation = courierLocationRepository.findLatestByCourierId(courierId);
        if (latestLocation.isEmpty()) {
            log.info("No location found for courier: {}", courierId);
            return 0.0;
        }
        
        // Get the location before the latest one
        List<CourierLocation> lastTwoLocations = courierLocationRepository
            .findTop2ByCourierIdOrderByTimestampDesc(courierId);
            
        if (lastTwoLocations.size() < 2) {
            log.info("Not enough locations to calculate distance for courier: {}", courierId);
            return 0.0;
        }
        
        return distanceCalculatorService.calculateDistance(
            lastTwoLocations.get(1).getLat(),
            lastTwoLocations.get(1).getLng(),
            lastTwoLocations.get(0).getLat(),
            lastTwoLocations.get(0).getLng()
        );
    }

}
