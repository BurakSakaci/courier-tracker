package com.sakaci.couriertracking.event.consumer;

import com.sakaci.couriertracking.event.StoreEntranceEvent;
import com.sakaci.couriertracking.event.consumer.listener.StoreEntranceEventListener;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreEntranceEventConsumerTest {
    @Mock
    private StoreEntranceEventListener listener1;
    @Mock
    private StoreEntranceEventListener listener2;
    @InjectMocks
    private StoreEntranceEventConsumer consumer;

    @Test
    void processStoreEntrance_callsListeners() {
        StoreEntranceEvent event = Instancio.create(StoreEntranceEvent.class);
        consumer = new StoreEntranceEventConsumer(List.of(listener1, listener2));
        consumer.processStoreEntrance(event);
        verify(listener1, times(1)).onStoreEntrance(event);
        verify(listener2, times(1)).onStoreEntrance(event);
    }
}
