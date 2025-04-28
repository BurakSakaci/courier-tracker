package com.sakaci.couriertracking.event.consumer.listener;

import com.sakaci.couriertracking.domain.entity.Store;
import com.sakaci.couriertracking.domain.entity.StoreEntrance;
import com.sakaci.couriertracking.domain.repository.StoreEntranceRepository;
import com.sakaci.couriertracking.event.StoreEntranceEvent;
import com.sakaci.couriertracking.service.CourierLocationService;
import com.sakaci.couriertracking.service.StoreService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreEntrancePersistenceListenerTest {
    @Mock
    private StoreEntranceRepository storeEntranceRepository;
    @Mock
    private StoreService storeService;
    @Mock
    private CourierLocationService courierLocationService;
    @InjectMocks
    private StoreEntrancePersistenceListener listener;

    @Test
    void onStoreEntrance_firstEntrance_savesEntrance() {
        StoreEntranceEvent event = Instancio.create(StoreEntranceEvent.class);
        Store store = Instancio.create(Store.class);
        event.setStoreId(store.getId());
        when(storeService.getAllStores()).thenReturn(Collections.singletonList(store));
        when(storeEntranceRepository.findLastByCourierIdAndStoreId(anyString(), any(UUID.class)))
                .thenReturn(Optional.empty());
        listener.onStoreEntrance(event);
        verify(storeEntranceRepository, times(1)).save(any(StoreEntrance.class));
        verify(courierLocationService, times(1)).recordLocation(
                eq(event.getCourierId()), eq(store.getLat()), eq(store.getLng()), any(Instant.class));
    }

    @Test
    void onStoreEntrance_withinReentryWindow_doesNotSave() {
        StoreEntranceEvent event = Instancio.create(StoreEntranceEvent.class);
        Store store = Instancio.create(Store.class);
        StoreEntrance lastEntrance = Instancio.create(StoreEntrance.class);
        lastEntrance.setEntranceTime(event.getTimestamp());
        event.setStoreId(store.getId());
        when(storeService.getAllStores()).thenReturn(Collections.singletonList(store));
        when(storeEntranceRepository.findLastByCourierIdAndStoreId(anyString(), any(UUID.class)))
                .thenReturn(Optional.of(lastEntrance));
        listener.onStoreEntrance(event);
        verify(storeEntranceRepository, never()).save(any(StoreEntrance.class));
    }

    @Test
    void onStoreEntrance_storeNotFound_throws() {
        StoreEntranceEvent event = Instancio.create(StoreEntranceEvent.class);
        when(storeService.getAllStores()).thenReturn(Collections.emptyList());
        assertThrows(RuntimeException.class, () -> listener.onStoreEntrance(event));
    }
}
