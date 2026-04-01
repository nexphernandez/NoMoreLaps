package com.nomorelaps.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomorelaps.adapters.out.persistence.interfaces.IParkingPersistenceAdapter;
import com.nomorelaps.business.interfaces.IParkingService;
import com.nomorelaps.domain.models.Parking;

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

    @Autowired
    public ParkingService(IParkingPersistenceAdapter persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public Parking create(Parking parking) {
        return persistencePort.save(parking);
    }

    @Override
    public Optional<Parking> findById(Long id) {
        return persistencePort.findById(id);
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
}
