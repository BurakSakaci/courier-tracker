package com.sakaci.couriertracking.event.consumer;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.sakaci.couriertracking.domain.entity.Store;
import com.sakaci.couriertracking.event.LocationUpdateEvent;
import com.sakaci.couriertracking.service.StoreService;
import com.sakaci.couriertracking.service.CourierLocationService;
import com.sakaci.couriertracking.service.StoreEntranceService;

@Service
@RequiredArgsConstructor
public class LocationEventConsumer {
    private static final double PROXIMITY_THRESHOLD_METERS = 100.0;

    private final CourierLocationService courierLocationService;
    private final StoreService storeService;
    private final StoreEntranceService entranceService;

    @KafkaListener(topics = "${app.kafka.topics.location-updates}")
    public void processLocationUpdate(LocationUpdateEvent event) {
        // 1. Save the location
        courierLocationService.recordLocation(
            event.getCourierId(),
            event.getLat(),
            event.getLng(),
            event.getTimestamp()
        );

        // 2. Find nearby stores (distance calculation happens inside storeService)
        List<Store> nearbyStores = storeService.findNearbyStores(
            event.getLat(),
            event.getLng(),
            PROXIMITY_THRESHOLD_METERS
        );

        // 3. Process store entrances
        nearbyStores.forEach(store -> 
            entranceService.processPotentialEntrance(
                event.getCourierId(),
                store,
                PROXIMITY_THRESHOLD_METERS, // We know they're within threshold
                event.getTimestamp()
            )
        );
    }
}
