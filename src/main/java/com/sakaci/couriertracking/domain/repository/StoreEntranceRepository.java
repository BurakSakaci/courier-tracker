package com.sakaci.couriertracking.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sakaci.couriertracking.domain.entity.StoreEntrance;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoreEntranceRepository extends JpaRepository<StoreEntrance, UUID> {

    Optional<StoreEntrance> findLastByCourierIdAndStoreId(String courierId, UUID storeId);
    
}
