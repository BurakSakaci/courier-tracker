package com.sakaci.couriertracking.observer;

import org.springframework.stereotype.Component;

import com.sakaci.couriertracking.domain.entity.CourierLocation;
import com.sakaci.couriertracking.domain.entity.Store;
import com.sakaci.couriertracking.domain.repository.InMemoryCourierLocationRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CourierStoreProximityObserver {
    // Logic to observe courier proximity to stores
    private final InMemoryCourierLocationRepository courierLocationRepository;

    public void onCourierLocationUpdate(CourierLocation courierLocation) {
        // TODO: Implement logic to observe courier proximity to stores
        courierLocationRepository.save(courierLocation);
    }

    public void onStoreProximityUpdate(Store store) {
        // TODO: Implement logic to observe store proximity to couriers
        courierLocationRepository.findAll().forEach(courierLocation -> {
            // TODO: Implement logic to observe store proximity to couriers
        });
    }
}
