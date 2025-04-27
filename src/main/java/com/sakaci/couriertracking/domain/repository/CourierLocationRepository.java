package com.sakaci.couriertracking.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sakaci.couriertracking.domain.entity.CourierLocation;

import java.util.UUID;

@Repository
public interface CourierLocationRepository extends JpaRepository<CourierLocation, UUID> {
    // Additional query methods if needed
}
