package com.sakaci.couriertracking.event.consumer;

import com.sakaci.couriertracking.domain.entity.Store;
import com.sakaci.couriertracking.event.LocationUpdateEvent;
import com.sakaci.couriertracking.service.CourierLocationService;
import com.sakaci.couriertracking.service.StoreEntranceService;
import com.sakaci.couriertracking.service.StoreService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationEventConsumerTest {
    @Mock
    private CourierLocationService courierLocationService;
    @Mock
    private StoreService storeService;
    @Mock
    private StoreEntranceService entranceService;
    @InjectMocks
    private LocationEventConsumer consumer;

    @Test
    void processLocationUpdate_processesCorrectly() {
        LocationUpdateEvent event = Instancio.create(LocationUpdateEvent.class);
        Store store = Instancio.create(Store.class);
        when(storeService.findNearbyStores(anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(List.of(store));
        consumer.processLocationUpdate(event);
        verify(courierLocationService, times(1)).recordLocation(
                eq(event.getCourierId()), eq(event.getLat()), eq(event.getLng()), eq(event.getTimestamp()));
        verify(storeService, times(1)).findNearbyStores(eq(event.getLat()), eq(event.getLng()), anyDouble());
        verify(entranceService, times(1)).processPotentialEntrance(
                eq(event.getCourierId()), eq(store), anyDouble(), eq(event.getTimestamp()));
    }
}
