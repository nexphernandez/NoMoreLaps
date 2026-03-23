package com.nomorelaps.adapters.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nomorelaps.adapters.out.persistence.jpa.ParkingSpotJpaEntity;
/**
 * JPA Repository for {@link ParkingSpotJpaEntity}.
 * Provides database access and CRUD operations via Spring Data.
 * 
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface ParkingSpotJpaRepository extends JpaRepository<ParkingSpotJpaEntity,Long> {
    List<ParkingSpotJpaEntity> findByParkingId(Long parkingId);

    List<ParkingSpotJpaEntity> findByParkingIdAndStateTrue(Long parkingId);
}
