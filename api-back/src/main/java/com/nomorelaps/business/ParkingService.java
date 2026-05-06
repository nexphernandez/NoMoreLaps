package com.nomorelaps.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomorelaps.adapters.out.persistence.interfaces.IParkingPersistenceAdapter;
import com.nomorelaps.business.interfaces.IParkingService;
import com.nomorelaps.business.interfaces.IParkingSpotService;
import com.nomorelaps.domain.models.Parking;
import com.nomorelaps.domain.models.ParkingSpot;

/**
 * Use Case implementation for Parking operations.
 * Connects the API layer with the Domain and Persistence layers.
 * 
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Service
public class ParkingService implements IParkingService {

    private final IParkingPersistenceAdapter persistencePort;
    private final IParkingSpotService parkingSpotService;

    @Autowired
    public ParkingService(IParkingPersistenceAdapter persistencePort, IParkingSpotService parkingSpotService) {
        this.persistencePort = persistencePort;
        this.parkingSpotService = parkingSpotService;
    }

    @Override
    public Parking create(Parking parking) {
        Parking savedParking = persistencePort.save(parking);
        
        if (parking.getTotalSpots() != null && parking.getTotalSpots() > 0) {
            for (int i = 1; i <= parking.getTotalSpots(); i++) {
                ParkingSpot spot = new ParkingSpot();
                spot.setNumber(i);
                spot.setParking(savedParking);
                spot.setState(true);
                parkingSpotService.create(spot);
            }
        }
        
        return savedParking;
    }

    @Override
    public Optional<Parking> findById(Long id) {
        return persistencePort.findById(id);
    }

    @Override
    public List<Parking> findAll() {
        return persistencePort.findAll();
    }

    @Override
    public List<Parking> findAllByCompanyId(Long companyId) {
        return persistencePort.findByCompanyId(companyId);
    }

    @Override
    public Parking update(Parking parking) {
        return persistencePort.save(parking);
    }

    @Override
    public void deleteById(Long id) {
        persistencePort.deleteById(id);
    }

    @Override
    public List<Parking> searchByNameOrAddress(String query) {
        return persistencePort.searchByNameOrAddress(query);
    }

    @Override
    public List<Parking> findNearby(double lat, double lng, double radiusInKm) {
        return persistencePort.findNearby(lat, lng, radiusInKm);
    }
}
