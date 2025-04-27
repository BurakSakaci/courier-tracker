package com.sakaci.couriertracking.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sakaci.couriertracking.domain.dto.CourierLocationDto;
import com.sakaci.couriertracking.event.LocationUpdateEvent;
import com.sakaci.couriertracking.event.producer.LocationEventProducer;

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

    @GetMapping("distance/{id}")
    public ResponseEntity<String> getDistance(@PathVariable String id) {
        return ResponseEntity.ok(id);
    }

    @PostMapping("/locations")
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
