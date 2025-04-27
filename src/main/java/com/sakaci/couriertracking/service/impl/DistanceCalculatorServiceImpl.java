package com.sakaci.couriertracking.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.sakaci.couriertracking.service.DistanceCalculatorService;
import com.sakaci.couriertracking.strategy.DistanceCalculationStrategy;
import com.sakaci.couriertracking.strategy.HaversineStrategy;
import com.sakaci.couriertracking.strategy.VincentyStrategy;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DistanceCalculatorServiceImpl implements DistanceCalculatorService {
    private final Map<String, DistanceCalculationStrategy> strategies = new ConcurrentHashMap<>();
    private String activeStrategy = "haversineStrategy";


    @PostConstruct
    public void init() {
        strategies.put("haversineStrategy", new HaversineStrategy());
        strategies.put("vincentyStrategy", new VincentyStrategy());
    }

    @Override
    public double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        log.info("Calculating distance using strategy: {}", activeStrategy);
        return strategies.get(activeStrategy)
               .calculateDistance(lat1, lng1, lat2, lng2);
    }
    
    @Override
    public List<String> getAvailableStrategies() {
        return new ArrayList<>(strategies.keySet());
    }

    @Override
    public void setActiveStrategy(String strategyName) {
        if (strategies.containsKey(strategyName)) {
            this.activeStrategy = strategyName;
        } else {
            throw new IllegalArgumentException("Unknown strategy: " + strategyName);
        }
    }
}
