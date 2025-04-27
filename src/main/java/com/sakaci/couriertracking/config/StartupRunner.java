package com.sakaci.couriertracking.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StartupRunner implements ApplicationRunner {

    private final StoreLoader storeLoader;

    @Override
    public void run(ApplicationArguments args) {
        storeLoader.loadStores();
    }
}

