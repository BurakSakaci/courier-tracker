package com.sakaci.couriertracking.domain.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StoreEntranceDto {
    @NotBlank(message = "Courier ID cannot be blank")
    private String courierId;
    @NotNull(message = "Store ID cannot be null")
    private UUID storeId;
}
