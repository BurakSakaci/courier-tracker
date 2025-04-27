package com.sakaci.couriertracking.service;


public interface CourierTrackingService {
    void trackCourier(String courierId, double lat, double lng, boolean isStoreEntry);
}
