package com.sakaci.couriertracking.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sakaci.couriertracking.domain.entity.Store;

import java.util.List;
import java.util.UUID;

@Repository
public interface StoreRepository extends JpaRepository<Store, UUID> {
    
    @Query(value = """
        SELECT * FROM stores 
        WHERE SQRT(
            POWER(69.1 * (lat - :lat), 2) + 
            POWER(69.1 * (:lng - lng) * COS(lat / 57.3), 2)
        ) * 1609.34 <= :radius
        """, nativeQuery = true)
    List<Store> findNearbyStores(@Param("lat") double lat, 
                               @Param("lng") double lng,
                               @Param("radius") double radiusInMeters);
}