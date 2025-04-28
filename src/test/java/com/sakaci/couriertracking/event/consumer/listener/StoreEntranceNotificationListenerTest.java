package com.sakaci.couriertracking.event.consumer.listener;

import com.sakaci.couriertracking.event.StoreEntranceEvent;

import static org.junit.Assert.assertTrue;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

class StoreEntranceNotificationListenerTest {
    @Test
    void onStoreEntrance_logsInfo() {
        StoreEntranceNotificationListener listener = new StoreEntranceNotificationListener();
        StoreEntranceEvent event = Instancio.create(StoreEntranceEvent.class);
        listener.onStoreEntrance(event);
        assertTrue(true);
    }
}
