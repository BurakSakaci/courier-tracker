package com.sakaci.couriertracking.service;

import java.util.List;

public interface DistanceCalculatorService {
   double calculateDistance(double lat1, double lng1, double lat2, double lng2);
   List<String> getAvailableStrategies();
   void setActiveStrategy(String strategyName);
}