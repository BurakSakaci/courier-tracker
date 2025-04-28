package com.sakaci.couriertracking.service.impl;

import com.sakaci.couriertracking.domain.entity.CourierLocation;
import com.sakaci.couriertracking.domain.repository.CourierLocationRepository;
import com.sakaci.couriertracking.service.DistanceCalculatorService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class CourierLocationServiceImplTest {
    @Mock
    private CourierLocationRepository courierLocationRepository;
    @Mock
    private DistanceCalculatorService distanceCalculatorService;
    @InjectMocks
    private CourierLocationServiceImpl courierLocationService;

    @Test
    void recordLocation_savesLocation() {
        String courierId = "courier1";
        Double lat = 10.0;
        Double lng = 20.0;
        Instant timestamp = Instant.now();

        courierLocationService.recordLocation(courierId, lat, lng, timestamp);

        verify(courierLocationRepository, times(1)).save(any(CourierLocation.class));
    }

    @Test
    void getTotalTravelDistance_notEnoughLocations_returnsZero() {
        when(courierLocationRepository.findAllByCourierIdOrderByTimestampAsc("courier1"))
                .thenReturn(Collections.singletonList(Instancio.create(CourierLocation.class)));

        Double result = courierLocationService.getTotalTravelDistance("courier1");
        assertEquals(0.0, result);
    }

    @Test
    void getTotalTravelDistance_multipleLocations_calculatesDistance() {
        CourierLocation loc1 = Instancio.create(CourierLocation.class);
        CourierLocation loc2 = Instancio.create(CourierLocation.class);
        when(courierLocationRepository.findAllByCourierIdOrderByTimestampAsc("courier1"))
                .thenReturn(Arrays.asList(loc1, loc2));
        when(distanceCalculatorService.calculateDistance(
                anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(42.0);
        Double result = courierLocationService.getTotalTravelDistance("courier1");
        assertEquals(42.0, result);
    }

    @Test
    void getLastTravelDistance_noLocation_returnsZero() {
        when(courierLocationRepository.findLatestByCourierId("courier1"))
                .thenReturn(Optional.empty());
        Double result = courierLocationService.getLastTravelDistance("courier1");
        assertEquals(0.0, result);
    }

    @Test
    void getLastTravelDistance_notEnoughLocations_returnsZero() {
        CourierLocation latest = Instancio.create(CourierLocation.class);
        when(courierLocationRepository.findLatestByCourierId("courier1"))
                .thenReturn(Optional.of(latest));
        when(courierLocationRepository.findTop2ByCourierIdOrderByTimestampDesc("courier1"))
                .thenReturn(Collections.singletonList(latest));
        Double result = courierLocationService.getLastTravelDistance("courier1");
        assertEquals(0.0, result);
    }

    @Test
    void getLastTravelDistance_twoLocations_calculatesDistance() {
        CourierLocation loc1 = Instancio.create(CourierLocation.class);
        CourierLocation loc2 = Instancio.create(CourierLocation.class);
        when(courierLocationRepository.findLatestByCourierId("courier1"))
                .thenReturn(Optional.of(loc2));
        when(courierLocationRepository.findTop2ByCourierIdOrderByTimestampDesc("courier1"))
                .thenReturn(Arrays.asList(loc2, loc1));
        when(distanceCalculatorService.calculateDistance(
                anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(99.0);
        Double result = courierLocationService.getLastTravelDistance("courier1");
        assertEquals(99.0, result);
    }
}
