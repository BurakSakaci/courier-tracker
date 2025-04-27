package com.sakaci.couriertracking.domain.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import java.util.List;

import com.sakaci.couriertracking.domain.entity.CourierLocation;

@Component
public class InMemoryCourierLocationRepository {
    // In-memory storage for courier locations
    private final Map<UUID, CourierLocation> courierLocations = new HashMap<>();

    private final CourierLocationRepository courierLocationRepository;

    public InMemoryCourierLocationRepository(CourierLocationRepository courierLocationRepository) {
        this.courierLocationRepository = courierLocationRepository;
    }

    public CourierLocation save(CourierLocation courierLocation) {
        courierLocations.put(courierLocation.getId(), courierLocation);
        return courierLocation;
    }

    public CourierLocation findById(UUID id) {
        return courierLocations.get(id);
    }

    public List<CourierLocation> findAll() {
        return new ArrayList<>(courierLocations.values());
    }

    public void delete(UUID id) {
        courierLocations.remove(id);
    }

    public void deleteAll() {
        courierLocations.clear();
    }

    // TODO: This is a temporary method for development, improve it
    public void clearCache() {
        courierLocations.clear();
        courierLocationRepository.findAll().forEach(courierLocation -> save(courierLocation));
    }
}
