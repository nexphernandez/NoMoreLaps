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
    /**
     * Creates a new parking spot.
     * 
     * @param spot The parking spot data.
     * @return The created parking spot.
     */
    ParkingSpot create(ParkingSpot spot);

    /**
     * Finds a parking spot by its ID.
     * 
     * @param id The spot ID.
     * @return An Optional containing the spot.
     */
    Optional<ParkingSpot> findById(Long id);

    /**
     * Lists all spots in a parking facility.
     * 
     * @param parkingId The parking facility ID.
     * @return A list of spots.
     */
    List<ParkingSpot> findByParkingId(Long parkingId);

    /**
     * Lists only the free spots in a facility.
     * 
     * @param parkingId The parking facility ID.
     * @return A list of free spots.
     */
    List<ParkingSpot> findAvailableSpots(Long parkingId);

    /**
     * Updates spot information.
     * 
     * @param spot The updated data.
     * @return The updated spot.
     */
    ParkingSpot update(ParkingSpot spot);

    /**
     * Deletes a spot.
     * 
     * @param id The spot ID.
     */
    void deleteById(Long id);
}
