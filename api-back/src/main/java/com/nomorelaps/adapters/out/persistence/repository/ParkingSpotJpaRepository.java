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
    /**
     * Retrieves all parking spots associated with a specific parking facility.
     * 
     * @param parkingId The ID of the parking facility.
     * @return A list of parking spots.
     */
    List<ParkingSpotJpaEntity> findByParkingId(Long parkingId);

    /**
     * Retrieves only the available (free) parking spots for a facility.
     * 
     * @param parkingId The ID of the parking facility.
     * @return A list of available parking spots.
     */
    List<ParkingSpotJpaEntity> findByParkingIdAndStateTrue(Long parkingId);
}
