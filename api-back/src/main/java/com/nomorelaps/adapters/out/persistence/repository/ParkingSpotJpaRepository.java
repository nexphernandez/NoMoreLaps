package com.nomorelaps.adapters.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nomorelaps.adapters.out.persistence.jpa.ParkingSpotJpaEntity;
/**
 * @author nexphernandez
 * @version 1.0.0
 * Parking jpa interface
 */
public interface ParkingSpotJpaRepository extends JpaRepository<ParkingSpotJpaEntity,Long> {

}
