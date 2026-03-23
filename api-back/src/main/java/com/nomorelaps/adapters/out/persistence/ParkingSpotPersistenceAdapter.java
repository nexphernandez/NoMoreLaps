package com.nomorelaps.adapters.out.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nomorelaps.adapters.out.persistence.interfaces.IParkingSpotPersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingSpotJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.ParkingSpotJpaRepository;
import com.nomorelaps.adapters.out.persistence.repository.abstracta.BasePersistenceAdapter;

/**
 * Persistence implementation for ParkingSpotJpaEntity via Spring Data repositories.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Component
public class ParkingSpotPersistenceAdapter extends BasePersistenceAdapter<ParkingSpotJpaEntity, 
        Long, ParkingSpotJpaRepository> implements IParkingSpotPersistenceAdapter {
    
    @Autowired
    public ParkingSpotPersistenceAdapter(ParkingSpotJpaRepository repository) {
        super(repository);
    }

    @Override
    public List<ParkingSpotJpaEntity> findByParkingId(Long parkingId) {
        return repository.findByParkingId(parkingId);
    }

    @Override
    public List<ParkingSpotJpaEntity> findByParkingIdAndStateTrue(Long parkingId) {
        return repository.findByParkingIdAndStateTrue(parkingId);
    }
}
