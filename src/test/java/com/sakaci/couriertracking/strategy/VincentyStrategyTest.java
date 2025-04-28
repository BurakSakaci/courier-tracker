package com.sakaci.couriertracking.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VincentyStrategyTest {
    private final VincentyStrategy strategy = new VincentyStrategy();

    @Test
    void calculateDistance_samePoint_returnsZero() {
        double distance = strategy.calculateDistance(0, 0, 0, 0);
        assertEquals(0.0, distance, 0.0001);
    }

    @Test
    void calculateDistance_knownDistance_returnsApproximate() {
        // Istanbul (41.0082, 28.9784) to Ankara (39.9334, 32.8597)
        double distance = strategy.calculateDistance(41.0082, 28.9784, 39.9334, 32.8597);
        // Actual distance ~351km, allow some margin for algorithmic difference
        assertTrue(distance > 340000 && distance < 370000);
    }

    @Test
    void calculateDistance_antipodalPoints() {
        // Opposite sides of the globe
        double distance = strategy.calculateDistance(0, 0, 0, 180);
        assertTrue(distance > 19800000 && distance < 20150000); // ~20,000km
    }
}
