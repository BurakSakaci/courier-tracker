package com.sakaci.couriertracking.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sakaci.couriertracking.domain.dto.CourierLocationDto;
import com.sakaci.couriertracking.domain.dto.StoreEntranceDto;
import com.sakaci.couriertracking.event.LocationUpdateEvent;
import com.sakaci.couriertracking.event.StoreEntranceEvent;
import com.sakaci.couriertracking.event.producer.LocationEventProducer;
import com.sakaci.couriertracking.event.producer.StoreEntranceEventProducer;
import com.sakaci.couriertracking.service.CourierLocationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;

import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/courier")
@RequiredArgsConstructor
public class CourierTrackingController {

    private final LocationEventProducer locationEventProducer;
    private final StoreEntranceEventProducer storeEntranceEventProducer;
    private final CourierLocationService courierLocationService;
    private final CacheManager cacheManager;

    @GetMapping("/total-distance/{courierId}")
    public ResponseEntity<Double> getTotalTravelDistance(@PathVariable String courierId) {
        Double distance = courierLocationService.getTotalTravelDistance(courierId);
        return ResponseEntity.ok(distance);
    }
    
    @GetMapping("/last-distance/{courierId}")
    public ResponseEntity<Double> getLastTravelDistance(@PathVariable String courierId) {
        Double distance = courierLocationService.getLastTravelDistance(courierId);
        return ResponseEntity.ok(distance);
    }

    @PostMapping
    public ResponseEntity<Void> recordLocation(@RequestBody @Valid CourierLocationDto dto) {
        LocationUpdateEvent event = new LocationUpdateEvent(
                dto.getCourierId(),
                dto.getLat(),
                dto.getLng(),
                Instant.now()
            );
        locationEventProducer.sendLocationUpdate(event);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/store-entrance")
    public ResponseEntity<Void> recordStoreEntrance(@RequestBody @Valid StoreEntranceDto dto) {
        StoreEntranceEvent event = new StoreEntranceEvent(
                dto.getCourierId(),
                dto.getStoreId(),
                Instant.now()
            );
        storeEntranceEventProducer.sendStoreEntranceEvent(event);
        return ResponseEntity.accepted().build();
    }
    
    // cache eviction
    @PostMapping("/evict-cache")
    public ResponseEntity<Void> evictCache() {
        cacheManager.getCacheNames().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
        return ResponseEntity.accepted().build();
    }
}
