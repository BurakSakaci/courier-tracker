package com.sakaci.couriertracking.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sakaci.couriertracking.domain.entity.CourierLocation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourierLocationRepository extends JpaRepository<CourierLocation, UUID> {
    
    @Query("SELECT cl FROM CourierLocation cl " +
           "WHERE cl.courierId = :courierId " +
           "ORDER BY cl.timestamp ASC")
    List<CourierLocation> findAllByCourierIdOrderByTimestampAsc(@Param("courierId") String courierId);
    
    @Query("SELECT cl FROM CourierLocation cl " +
           "WHERE cl.courierId = :courierId AND cl.id = " +
           "(SELECT MAX(cl2.id) FROM CourierLocation cl2 WHERE cl2.courierId = :courierId)")
    Optional<CourierLocation> findLatestByCourierId(@Param("courierId") String courierId);

    @Query("SELECT cl FROM CourierLocation cl " +
           "WHERE cl.courierId = :courierId " +
           "ORDER BY cl.timestamp DESC LIMIT 2")
    List<CourierLocation> findTop2ByCourierIdOrderByTimestampDesc(@Param("courierId") String courierId);
}
