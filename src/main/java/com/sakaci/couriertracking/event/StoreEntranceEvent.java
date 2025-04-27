package com.sakaci.couriertracking.event;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreEntranceEvent {
    private String courierId;
    private UUID storeId;
    private String storeName;
    private double distance;
    private String timestamp;
}
