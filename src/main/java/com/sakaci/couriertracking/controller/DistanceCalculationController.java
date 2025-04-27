package com.sakaci.couriertracking.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakaci.couriertracking.service.DistanceCalculatorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/distance-calculation")
@RequiredArgsConstructor
public class DistanceCalculationController {
    
    private final DistanceCalculatorService calculatorService;
    
    @GetMapping("/strategies")
    public ResponseEntity<List<String>> getAvailableStrategies() {
        return ResponseEntity.ok(calculatorService.getAvailableStrategies());
    }
    
    @PostMapping("/strategy/{name}")
    public ResponseEntity<String> setActiveStrategy(@PathVariable String name) {
        try {
            calculatorService.setActiveStrategy(name);
            return ResponseEntity.ok("Strategy set to: " + name);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
