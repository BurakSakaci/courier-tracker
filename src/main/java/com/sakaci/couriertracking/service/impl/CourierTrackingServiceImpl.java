package com.sakaci.couriertracking.service.impl;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.sakaci.couriertracking.domain.entity.CourierLocation;
import com.sakaci.couriertracking.domain.repository.CourierLocationRepository;
import com.sakaci.couriertracking.service.CourierTrackingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourierTrackingServiceImpl implements CourierTrackingService {
    
    private final CourierLocationRepository courierLocationRepository;

    @Override
    public void recordLocation(String courierId, Double lat, Double lng, String timestamp) {
        CourierLocation courierLocation = new CourierLocation();
        courierLocation.setCourierId(courierId);
        courierLocation.setLat(lat);
        courierLocation.setLng(lng);
        courierLocation.setTimestamp(Instant.parse(timestamp));
        courierLocationRepository.save(courierLocation);
        log.info("Recorded location for courier: {}, at: {}", courierId, timestamp);
    }
}
