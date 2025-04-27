package com.sakaci.couriertracking.event;

import lombok.Data;

@Data
public class CourierEnteredStoreEvent {
    private String courierId;
    private String storeId;
    private String timestamp;
}
