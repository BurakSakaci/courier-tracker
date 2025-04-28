package com.sakaci.couriertracking.service.impl;

import com.sakaci.couriertracking.domain.entity.Store;
import com.sakaci.couriertracking.domain.entity.StoreEntrance;
import com.sakaci.couriertracking.domain.repository.StoreEntranceRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class StoreEntranceServiceImplTest {
    @Mock
    private StoreEntranceRepository entranceRepository;
    @InjectMocks
    private StoreEntranceServiceImpl storeEntranceService;

    @Test
    void processPotentialEntrance_firstEntrance_savesEntrance() {
        Store store = Instancio.create(Store.class);
        when(entranceRepository.findLastByCourierIdAndStoreId(any(), any())).thenReturn(Optional.empty());
        storeEntranceService.processPotentialEntrance("courier1", store, 10.0, Instant.now());
        verify(entranceRepository, times(1)).save(any(StoreEntrance.class));
    }

    @Test
    void processPotentialEntrance_outsideReentryWindow_savesEntrance() {
        Store store = Instancio.create(Store.class);
        StoreEntrance lastEntrance = Instancio.create(StoreEntrance.class);
        when(entranceRepository.findLastByCourierIdAndStoreId(any(), any())).thenReturn(Optional.of(lastEntrance));
        // simulate outside reentry window
        Instant newTime = lastEntrance.getEntranceTime().plusSeconds(120);
        storeEntranceService.processPotentialEntrance("courier1", store, 10.0, newTime);
        verify(entranceRepository, times(1)).save(any(StoreEntrance.class));
    }

    @Test
    void processPotentialEntrance_withinReentryWindow_doesNotSave() {
        Store store = Instancio.create(Store.class);
        StoreEntrance lastEntrance = Instancio.create(StoreEntrance.class);
        when(entranceRepository.findLastByCourierIdAndStoreId(any(), any())).thenReturn(Optional.of(lastEntrance));
        // simulate within reentry window
        Instant newTime = lastEntrance.getEntranceTime().plusSeconds(30);
        storeEntranceService.processPotentialEntrance("courier1", store, 10.0, newTime);
        verify(entranceRepository, never()).save(any(StoreEntrance.class));
    }
}
