package com.sakaci.couriertracking.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationUpdateEvent {
    private String courierId;
    private Double lat;
    private Double lng;
    private String timestamp;
}
