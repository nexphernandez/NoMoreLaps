package com.nomorelaps.adapters.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nomorelaps.adapters.out.persistence.jpa.ParkingJpaEntity;
/**
 * JPA Repository for {@link ParkingJpaEntity}.
 * Provides database access and CRUD operations via Spring Data.
 * 
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface ParkingJpaRepository extends JpaRepository<ParkingJpaEntity,Long> {
    List<ParkingJpaEntity> findByCompanyId(Long companyId);
}
