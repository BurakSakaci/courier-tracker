package com.sakaci.couriertracking.service;

import java.time.Instant;

public interface CourierLocationService {
    void recordLocation(String courierId, Double lat, Double lng, Instant timestamp);
    Double getTotalTravelDistance(String courierId);
    Double getLastTravelDistance(String courierId);
}
