package com.sakaci.couriertracking.service.impl;

import org.springframework.stereotype.Service;

import com.sakaci.couriertracking.service.DistanceCalculatorService;

@Service
public class DistanceCalculatorServiceImpl implements DistanceCalculatorService {
    // Haversine formula for distance in meters
    @Override
    public double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        final int EARTH_RADIUS = 6371000; // meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return EARTH_RADIUS * c;
    }
}
