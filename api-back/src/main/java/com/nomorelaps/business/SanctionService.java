package com.nomorelaps.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomorelaps.adapters.out.persistence.interfaces.ISanctionPersistenceAdapter;
import com.nomorelaps.business.interfaces.ISanctionService;
import com.nomorelaps.domain.models.Sanction;

/**
 * Use Case implementation for Sanction operations.
 * Connects the API layer with the Domain and Persistence layers.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Service
public class SanctionService implements ISanctionService {

    private final ISanctionPersistenceAdapter persistencePort;

    @Autowired
    public SanctionService(ISanctionPersistenceAdapter persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public Sanction create(Sanction sanction) {
        return persistencePort.save(sanction);
    }

    @Override
    public Optional<Sanction> findById(Long id) {
        return persistencePort.findById(id);
    }

    @Override
    public List<Sanction> findByUserId(Long userId) {
        return persistencePort.findByUserId(userId);
    }

    @Override
    public List<Sanction> findByReservationId(Long reservationId) {
        return persistencePort.findByReservationId(reservationId);
    }

    @Override
    public Sanction update(Sanction sanction) {
        return persistencePort.save(sanction);
    }

    @Override
    public void deleteById(Long id) {
        persistencePort.deleteById(id);
    }

    @Override
    public Sanction paySanction(Long id) {
        return persistencePort.findById(id).map(sanction -> {
            sanction.setPaid(true);
            return persistencePort.save(sanction);
        }).orElseThrow(() -> new RuntimeException("Sanction not found with id: " + id));
    }
}
