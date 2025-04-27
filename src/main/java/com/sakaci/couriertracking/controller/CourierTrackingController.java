package com.sakaci.couriertracking.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/courier")
public class CourierTrackingController {

    @GetMapping("distance/{id}")
    public ResponseEntity<String> getDistance(@PathVariable String id) {
        return ResponseEntity.ok(id);
    }

    @PostMapping("locations")
    public ResponseEntity<String> setLocation(@RequestBody String entity) {
        return ResponseEntity.ok(entity);
    }
}
