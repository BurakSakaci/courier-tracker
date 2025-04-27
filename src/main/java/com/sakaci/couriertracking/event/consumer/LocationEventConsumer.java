package com.sakaci.couriertracking.event.consumer;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.sakaci.couriertracking.domain.entity.Store;
import com.sakaci.couriertracking.event.LocationUpdateEvent;
import com.sakaci.couriertracking.service.CourierTrackingService;
import com.sakaci.couriertracking.service.DistanceCalculatorService;
import com.sakaci.couriertracking.service.StoreService;
import com.sakaci.couriertracking.service.StoreEntranceService;

@Service
@RequiredArgsConstructor
public class LocationEventConsumer {

    private final CourierTrackingService trackingService;
    private final StoreService storeService;
    private final StoreEntranceService entranceService;
    private final DistanceCalculatorService distanceCalculatorService;
    private static final double PROXIMITY_THRESHOLD_METERS = 100.0;

    @KafkaListener(topics = "${app.kafka.topics.location-updates}")
    public void processLocationUpdate(LocationUpdateEvent event) {
        // 1. Save the location
        trackingService.recordLocation(
            event.getCourierId(),
            event.getLat(),
            event.getLng(),
            event.getTimestamp()
        );

        // 2. Check for nearby stores
        List<Store> nearbyStores = storeService.findNearbyStores(
            event.getLat(),
            event.getLng(),
            PROXIMITY_THRESHOLD_METERS
        );

        // 3. Process store entrances
        nearbyStores.forEach(store -> {
            double distance = calculateDistance(
                event.getLat(), event.getLng(),
                store.getLat(), store.getLng()
            );
            
            if (distance <= PROXIMITY_THRESHOLD_METERS) {
                entranceService.processPotentialEntrance(
                    event.getCourierId(),
                    store,
                    distance,
                    event.getTimestamp()
                );
            }
        });
    }

    private double calculateDistance(Double lat, Double lng, Double lat2, Double lng2) {
        return distanceCalculatorService.calculateDistance(lat, lng, lat2, lng2);
    }
}
