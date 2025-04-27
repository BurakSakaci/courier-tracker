package com.sakaci.couriertracking.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sakaci.couriertracking.domain.dto.CourierLocationDto;
import com.sakaci.couriertracking.event.LocationUpdateEvent;
import com.sakaci.couriertracking.event.producer.LocationEventProducer;
import com.sakaci.couriertracking.service.CourierLocationService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;

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
    private final CourierLocationService courierLocationService;

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
    public ResponseEntity<Void> recordLocation(@RequestBody CourierLocationDto dto) {
        LocationUpdateEvent event = new LocationUpdateEvent(
                dto.getCourierId(),
                dto.getLat(),
                dto.getLng(),
                Instant.now().toString()
            );
        locationEventProducer.sendLocationUpdate(event);
        return ResponseEntity.accepted().build();
    }

}
