package com.nomorelaps.adapters.out.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nomorelaps.adapters.out.persistence.interfaces.ISanctionPersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.jpa.SanctionJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.SanctionJpaRepository;
import com.nomorelaps.adapters.out.persistence.repository.abstracta.BasePersistenceAdapter;

/**
 * Persistence implementation for SanctionJpaEntity via Spring Data repositories.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Component
public class SanctionPersistenceAdapter extends BasePersistenceAdapter<SanctionJpaEntity, 
        Long, SanctionJpaRepository> implements ISanctionPersistenceAdapter {
    
    @Autowired
    public SanctionPersistenceAdapter(SanctionJpaRepository repository) {
        super(repository);
    }

    @Override
    public List<SanctionJpaEntity> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<SanctionJpaEntity> findByReservationId(Long reservationId) {
        return repository.findByReservationId(reservationId);
    }
}
