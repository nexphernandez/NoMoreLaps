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

    List<ReservationJpaEntity> findByUserId(Long userId);

    List<ReservationJpaEntity> findByParkingSpotId(Long spotId);

    List<ReservationJpaEntity> findByState(String state);
} 