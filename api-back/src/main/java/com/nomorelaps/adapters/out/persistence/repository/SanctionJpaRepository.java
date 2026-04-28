package com.nomorelaps.adapters.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nomorelaps.adapters.out.persistence.jpa.SanctionJpaEntity;
/**
 * JPA Repository for {@link SanctionJpaEntity}.
 * Provides database access and CRUD operations via Spring Data.
 * 
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface SanctionJpaRepository extends JpaRepository<SanctionJpaEntity,Long> {
    /**
     * Retrieves all sanctions applied to a specific user.
     * 
     * @param userId The user ID.
     * @return A list of matching sanctions.
     */
    List<SanctionJpaEntity> findByUserId(Long userId);

    /**
     * Retrieves all sanctions associated with a specific reservation.
     * 
     * @param reservationId The reservation ID.
     * @return A list of matching sanctions.
     */
    List<SanctionJpaEntity> findByReservationId(Long reservationId);
}
