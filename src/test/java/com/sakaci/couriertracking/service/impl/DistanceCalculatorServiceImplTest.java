package com.sakaci.couriertracking.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class DistanceCalculatorServiceImplTest {
    private DistanceCalculatorServiceImpl distanceCalculatorService;

    @BeforeEach
    void setUp() {
        distanceCalculatorService = new DistanceCalculatorServiceImpl();
        distanceCalculatorService.init();
    }

    @Test
    void calculateDistance_usesActiveStrategy() {
        double d = distanceCalculatorService.calculateDistance(10, 20, 30, 40);
        assertTrue(d > 0);
    }

    @Test
    void getAvailableStrategies_containsExpected() {
        List<String> strategies = distanceCalculatorService.getAvailableStrategies();
        assertTrue(strategies.contains("haversineStrategy"));
        assertTrue(strategies.contains("vincentyStrategy"));
    }

    @Test
    void setActiveStrategy_switchesStrategy() {
        distanceCalculatorService.setActiveStrategy("vincentyStrategy");
        assertEquals("vincentyStrategy", 
            getActiveStrategy(distanceCalculatorService));
    }

    @Test
    void setActiveStrategy_unknown_throws() {
        assertThrows(IllegalArgumentException.class, () ->
            distanceCalculatorService.setActiveStrategy("unknownStrategy"));
    }

    // Helper to access private field
    private String getActiveStrategy(DistanceCalculatorServiceImpl service) {
        try {
            var f = DistanceCalculatorServiceImpl.class.getDeclaredField("activeStrategy");
            f.setAccessible(true);
            return (String) f.get(service);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
