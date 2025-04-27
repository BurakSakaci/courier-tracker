package com.sakaci.couriertracking.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sakaci.couriertracking.domain.entity.Store;
import com.sakaci.couriertracking.domain.entity.StoreEntrance;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoreRepository extends JpaRepository<Store, UUID> {
    
    @Query("SELECT se FROM StoreEntrance se " +
           "WHERE se.courierId = :courierId AND se.storeId = :storeId " +
           "ORDER BY se.entranceTime DESC LIMIT 1")
    Optional<StoreEntrance> findLastByCourierAndStore(
        @Param("courierId") String courierId, 
        @Param("storeId") UUID storeId);
}