package com.nomorelaps.business.interfaces;

import java.util.List;
import java.util.Optional;
import com.nomorelaps.domain.models.ParkingSpot;

/**
 * Inbound port (Use Case) for ParkingSpot operations.
 * Defines the contract that the API presentation layer will consume.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface IParkingSpotService {
    ParkingSpot create(ParkingSpot spot);
    Optional<ParkingSpot> findById(Long id);
    List<ParkingSpot> findByParkingId(Long parkingId);
    List<ParkingSpot> findAvailableSpots(Long parkingId);
    ParkingSpot update(ParkingSpot spot);
    void deleteById(Long id);
}
