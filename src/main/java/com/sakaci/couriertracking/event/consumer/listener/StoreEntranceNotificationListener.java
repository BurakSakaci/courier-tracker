package com.sakaci.couriertracking.event.consumer.listener;

import org.springframework.stereotype.Component;

import com.sakaci.couriertracking.event.StoreEntranceEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StoreEntranceNotificationListener implements StoreEntranceEventListener {
    @Override
    public void onStoreEntrance(StoreEntranceEvent event) {
        log.info("Processing store entrance notification event: {}", event);
        // TODO: Implement notification logic
    }
}
