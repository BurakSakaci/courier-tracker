package com.sakaci.couriertracking.service.impl;

import com.sakaci.couriertracking.domain.entity.Store;
import com.sakaci.couriertracking.domain.repository.StoreRepository;
import com.sakaci.couriertracking.service.DistanceCalculatorService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class StoreServiceImplTest {
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private DistanceCalculatorService distanceCalculatorService;
    @InjectMocks
    private StoreServiceImpl storeService;


    @Test
    void getAllStores_returnsStores() {
        List<Store> stores = Arrays.asList(Instancio.create(Store.class), Instancio.create(Store.class));
        when(storeRepository.findAll()).thenReturn(stores);
        List<Store> result = storeService.getAllStores();
        assertEquals(stores, result);
    }

    @Test
    void findNearbyStores_filtersByDistance() {
        Store store1 = Instancio.create(Store.class);
        Store store2 = Instancio.create(Store.class);
        List<Store> stores = Arrays.asList(store1, store2);
        when(storeRepository.findAll()).thenReturn(stores);
        when(distanceCalculatorService.calculateDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(100.0, 500.0);
        List<Store> result = storeService.findNearbyStores(1.0, 2.0, 200.0);
        assertTrue(result.contains(store1));
        assertFalse(result.contains(store2));
    }
}
