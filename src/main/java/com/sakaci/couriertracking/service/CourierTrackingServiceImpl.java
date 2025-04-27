package com.sakaci.couriertracking.service;

import org.springframework.stereotype.Service;

@Service
public class CourierTrackingServiceImpl implements CourierTrackingService {
    @Override
    public void trackCourier(String courierId, double lat, double lng, boolean isStoreEntry) {
        // Implement the tracking logic here (e.g., save to database, trigger events, etc.)
    }
}
