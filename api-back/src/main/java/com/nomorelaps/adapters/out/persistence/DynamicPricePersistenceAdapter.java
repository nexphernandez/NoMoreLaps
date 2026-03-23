package com.nomorelaps.adapters.out.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nomorelaps.adapters.out.persistence.interfaces.IDynamicPricePersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.jpa.DynamicPriceJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.DynamicPriceJpaRepository;
import com.nomorelaps.adapters.out.persistence.repository.abstracta.BasePersistenceAdapter;

/**
 * Persistence implementation for DynamicPriceJpaEntity via Spring Data repositories.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Component
public class DynamicPricePersistenceAdapter extends BasePersistenceAdapter<DynamicPriceJpaEntity,
        Long, DynamicPriceJpaRepository> implements IDynamicPricePersistenceAdapter {
    
    @Autowired
    public DynamicPricePersistenceAdapter(DynamicPriceJpaRepository repository) {
        super(repository);
    }

    @Override
    public List<DynamicPriceJpaEntity> findByParkingId(Long parkingId) {
        return repository.findByParkingId(parkingId);
    }
}
