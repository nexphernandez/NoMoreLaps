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
public interface ParkingJpaRepository extends JpaRepository<ParkingJpaEntity, Long> {
    List<ParkingJpaEntity> findByCompanyId(Long companyId);

    /**
     * Finds parkings whose name or address contains the specified query string, ignoring case.
     * 
     * @param name    The name query.
     * @param address The address query.
     * @return A list of matching {@link ParkingJpaEntity}.
     */
    List<ParkingJpaEntity> findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(String name, String address);

    /**
     * Finds parkings located within a specific coordinate range (Bounding Box).
     * 
     * @param minLat Minimum latitude.
     * @param maxLat Maximum latitude.
     * @param minLng Minimum longitude.
     * @param maxLng Maximum longitude.
     * @return A list of parkings within the square range.
     */
    List<ParkingJpaEntity> findByLatitudeBetweenAndLongitudeBetween(double minLat, double maxLat, double minLng, double maxLng);
}
