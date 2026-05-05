package com.nomorelaps.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomorelaps.adapters.out.persistence.interfaces.IParkingSpotPersistenceAdapter;
import com.nomorelaps.business.interfaces.IParkingSpotService;
import com.nomorelaps.domain.models.ParkingSpot;

/**
 * Use Case implementation for ParkingSpot operations.
 * Connects the API layer with the Domain and Persistence layers.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Service
public class ParkingSpotService implements IParkingSpotService {

    private final IParkingSpotPersistenceAdapter persistencePort;

    @Autowired
    public ParkingSpotService(IParkingSpotPersistenceAdapter persistencePort) {
        this.persistencePort = persistencePort;
    }

    /**
     * Creates a new parking spot.
     * By default, new spots are created as available (state = true).
     * 
     * @param spot The parking spot data.
     * @return The persisted parking spot.
     */
    @Override
    public ParkingSpot create(ParkingSpot spot) {
        spot.setState(true);
        return persistencePort.save(spot);
    }

    /**
     * Retrieves a parking spot by its ID.
     * 
     * @param id The spot ID.
     * @return An Optional containing the spot if found.
     */
    @Override
    public Optional<ParkingSpot> findById(Long id) {
        return persistencePort.findById(id);
    }

    /**
     * Finds all spots belonging to a specific parking facility.
     * 
     * @param parkingId The parking facility ID.
     * @return A list of parking spots.
     */
    @Override
    public List<ParkingSpot> findByParkingId(Long parkingId) {
        return persistencePort.findByParkingId(parkingId);
    }

    /**
     * Finds only the currently available (free) spots in a parking facility.
     * 
     * @param parkingId The parking facility ID.
     * @return A list of available spots.
     */
    @Override
    public List<ParkingSpot> findAvailableSpots(Long parkingId) {
        return persistencePort.findByParkingIdAndStateTrue(parkingId);
    }

    /**
     * Updates an existing parking spot's information or state.
     * 
     * @param spot The updated spot data.
     * @return The updated spot.
     */
    @Override
    public ParkingSpot update(ParkingSpot spot) {
        if (spot.getId() == null || !persistencePort.findById(spot.getId()).isPresent()) {
            throw new RuntimeException("Cannot update: Parking spot not found with id: " + spot.getId());
        }
        return persistencePort.save(spot);
    }

    /**
     * Deletes a parking spot from the system.
     * 
     * @param id The spot ID to delete.
     */
    @Override
    public void deleteById(Long id) {
        persistencePort.deleteById(id);
    }
}
