package com.sakaci.couriertracking.service;

//TODO: Implement distance calculator with strategy pattern
public interface DistanceCalculatorService {
   double calculateDistance(double lat1, double lng1, double lat2, double lng2);
}