package com.sakaci.couriertracking.service;


public interface CourierTrackingService {
    void recordLocation(String courierId, Double lat, Double lng, String timestamp);
}
