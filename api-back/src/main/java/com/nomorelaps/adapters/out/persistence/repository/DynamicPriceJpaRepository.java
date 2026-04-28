package com.nomorelaps.adapters.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nomorelaps.adapters.out.persistence.jpa.DynamicPriceJpaEntity;
/**
 * JPA Repository for {@link DynamicPriceJpaEntity}.
 * Provides database access and CRUD operations via Spring Data.
 * 
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface DynamicPriceJpaRepository extends JpaRepository<DynamicPriceJpaEntity, Long> {
    /**
     * Retrieves all dynamic price rules associated with a specific parking facility.
     * 
     * @param parkingId The ID of the parking facility.
     * @return A list of dynamic price rules.
     */
    List<DynamicPriceJpaEntity> findByParkingId(Long parkingId);
}
