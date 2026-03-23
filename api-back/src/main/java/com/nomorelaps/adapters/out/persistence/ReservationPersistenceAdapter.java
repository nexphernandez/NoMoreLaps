package com.nomorelaps.adapters.out.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nomorelaps.adapters.out.persistence.interfaces.IReservationPersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.jpa.ReservationJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.ReservationJpaRepository;
import com.nomorelaps.adapters.out.persistence.repository.abstracta.BasePersistenceAdapter;

/**
 * Persistence implementation for ReservationJpaEntity via Spring Data repositories.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Component
public class ReservationPersistenceAdapter extends BasePersistenceAdapter<ReservationJpaEntity, 
        Long, ReservationJpaRepository> implements IReservationPersistenceAdapter{

    @Autowired
    public ReservationPersistenceAdapter(ReservationJpaRepository repository) {
        super(repository);
    }

    @Override
    public List<ReservationJpaEntity> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<ReservationJpaEntity> findByParkingSpotId(Long spotId) {
        return repository.findByParkingSpotId(spotId);
    }

    @Override
    public List<ReservationJpaEntity> findByState(String state) {
        return repository.findByState(state);
    }
}
