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

    @Override
    public ParkingSpot create(ParkingSpot spot) {
        // spot.setState(true) automatically defaults to free/available initially?
        return persistencePort.save(spot);
    }

    @Override
    public Optional<ParkingSpot> findById(Long id) {
        return persistencePort.findById(id);
    }

    @Override
    public List<ParkingSpot> findByParkingId(Long parkingId) {
        return persistencePort.findByParkingId(parkingId);
    }

    @Override
    public List<ParkingSpot> findAvailableSpots(Long parkingId) {
        return persistencePort.findByParkingIdAndStateTrue(parkingId);
    }

    @Override
    public ParkingSpot update(ParkingSpot spot) {
        return persistencePort.save(spot);
    }

    @Override
    public void deleteById(Long id) {
        persistencePort.deleteById(id);
    }
}
