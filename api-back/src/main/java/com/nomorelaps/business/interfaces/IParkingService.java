package com.nomorelaps.business.interfaces;

import java.util.List;
import java.util.Optional;

import com.nomorelaps.domain.models.Parking;

/**
 * Inbound port (Use Case) for Parking operations.
 * Defines the contract that the API presentation layer will consume.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface IParkingService {
    Parking create(Parking parking);
    Optional<Parking> findById(Long id);
    List<Parking> findAllByCompanyId(Long companyId);
    Parking update(Parking parking);
    void deleteById(Long id);
}
