package com.sakaci.couriertracking.event.consumer.listener;

import com.sakaci.couriertracking.event.StoreEntranceEvent;

public interface StoreEntranceEventListener {
    void onStoreEntrance(StoreEntranceEvent event);
}
