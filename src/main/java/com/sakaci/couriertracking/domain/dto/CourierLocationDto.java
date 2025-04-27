package com.sakaci.couriertracking.domain.dto;

import lombok.Data;

@Data
public class CourierLocationDto {
    private String courierId;
    private Double lat;
    private Double lng;
}
