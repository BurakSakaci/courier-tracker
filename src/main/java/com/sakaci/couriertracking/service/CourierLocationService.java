package com.sakaci.couriertracking.service;


public interface CourierLocationService {
    void recordLocation(String courierId, Double lat, Double lng, String timestamp);
    Double getTotalTravelDistance(String courierId);
    Double getLastTravelDistance(String courierId);
}
