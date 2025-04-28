package com.sakaci.couriertracking.domain.dto;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourierLocationDto {
    @NotBlank(message = "Courier ID cannot be blank")
    private String courierId;

    @NotNull(message = "Latitude cannot be null")
    @Range(min = -90, max = 90, message = "Latitude must be between -90 and 90")
    private Double lat;

    @NotNull(message = "Longitude cannot be null")
    @Range(min = -180, max = 180, message = "Longitude must be between -180 and 180")
    private Double lng;
}
