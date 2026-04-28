package com.nomorelaps.adapters.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nomorelaps.adapters.out.persistence.jpa.ReservationJpaEntity;
/**
 * JPA Repository for {@link ReservationJpaEntity}.
 * Provides database access and CRUD operations via Spring Data.
 * 
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface ReservationJpaRepository extends JpaRepository<ReservationJpaEntity,Long> {

    /**
     * Retrieves all reservations made by a specific user.
     * 
     * @param userId The user ID.
     * @return A list of matching reservations.
     */
    List<ReservationJpaEntity> findByUserId(Long userId);

    /**
     * Retrieves all reservations for a specific parking spot.
     * 
     * @param spotId The parking spot ID.
     * @return A list of matching reservations.
     */
    List<ReservationJpaEntity> findByParkingSpotId(Long spotId);

    /**
     * Retrieves all reservations in a specific state.
     * 
     * @param state The state string (e.g., ACTIVA, CANCELADA).
     * @return A list of matching reservations.
     */
    List<ReservationJpaEntity> findByState(String state);
} 