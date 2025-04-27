package com.sakaci.couriertracking.config;


import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import com.sakaci.couriertracking.domain.constants.StoreType;
import com.sakaci.couriertracking.domain.entity.Store;
import com.sakaci.couriertracking.domain.repository.StoreRepository;

import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class StoreLoader {

    private final ObjectMapper objectMapper;
    private final StoreRepository storeRepository;

    @Value("${app.store-data-file}")
    private Resource storesFile;


    public StoreLoader(ObjectMapper objectMapper, StoreRepository storeRepository) {
        this.objectMapper = objectMapper;
        this.storeRepository = storeRepository;
    }

    @Transactional
    public void loadStores() {
        try {
            long count = storeRepository.count();
            if (count == 0) {
                log.info("No stores found in database. Loading from {}...", storesFile.getFilename());
                loadFromFileAndSaveToDb();
            } else {
                log.info("Found {} stores in database. Skipping load from file.", count);
            }
        } catch (Exception e) {
            log.error("Critical error loading stores: {}", e.getMessage(), e);
        }
    }

    private void loadFromFileAndSaveToDb() throws IOException {
        if (!resourceExists()) {
            throw new IllegalStateException(storesFile.getFilename() + " not found in classpath");
        }

        try (InputStream inputStream = storesFile.getInputStream()) {
            List<Store> stores = objectMapper.readValue(
                inputStream,
                new TypeReference<List<Store>>() {}
            );

            if (stores.isEmpty()) {
                log.warn("Loaded empty store list from file");
                return;
            }

            // TODO: can be better
            stores.forEach(store -> {    
                if (store.getName().contains("Migros")) {
                    store.setStoreType(StoreType.MIGROS);
                } else if (store.getName().contains("Macro")) {
                    store.setStoreType(StoreType.MACRO_CENTER);
                } else if (store.getName().contains("Mion")) {
                    store.setStoreType(StoreType.MION);
                } else {
                    store.setStoreType(StoreType.OTHER);
                }
            });

            List<Store> savedStores = storeRepository.saveAll(stores);
            log.info("Successfully loaded {} stores", savedStores.size());
        } catch (Exception e) {
            log.error("Failed to load stores from file", e);
        }
    }

    private boolean resourceExists() {
        return storesFile != null && storesFile.exists();
    }
}