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
    /**
     * Creates a new parking facility.
     * 
     * @param parking The parking data.
     * @return The created parking.
     */
    Parking create(Parking parking);

    /**
     * Finds a parking facility by ID.
     * 
     * @param id The parking ID.
     * @return Optional parking.
     */
    Optional<Parking> findById(Long id);

    /**
     * Lists all parking facilities.
     * 
     * @return List of parkings.
     */
    List<Parking> findAll();

    /**
     * Lists all parkings belonging to a company.
     * 
     * @param companyId The company ID.
     * @return List of parkings.
     */
    List<Parking> findAllByCompanyId(Long companyId);

    /**
     * Updates parking information.
     * 
     * @param parking Updated data.
     * @return Updated parking.
     */
    Parking update(Parking parking);

    /**
     * Deletes a parking facility.
     * 
     * @param id Parking ID.
     */
    void deleteById(Long id);

    /**
     * Searches for parking lots by name or address.
     * 
     * @param query The search text.
     * @return A list of matching {@link Parking} objects.
     */
    List<Parking> searchByNameOrAddress(String query);

    /**
     * Finds parkings located within a certain distance from a location.
     * 
     * @param lat        Target latitude.
     * @param lng        Target longitude.
     * @param radiusInKm Search radius in kilometers.
     * @return A list of nearby {@link Parking} objects.
     */
    List<Parking> findNearby(double lat, double lng, double radiusInKm);
}
