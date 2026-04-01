package com.nomorelaps.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomorelaps.adapters.out.persistence.interfaces.IDynamicPricePersistenceAdapter;
import com.nomorelaps.business.interfaces.IDynamicPriceService;
import com.nomorelaps.domain.models.DynamicPrice;

/**
 * Use Case implementation for DynamicPrice operations.
 * Connects the API layer with the Domain and Persistence layers.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Service
public class DynamicPriceService implements IDynamicPriceService {

    private final IDynamicPricePersistenceAdapter persistencePort;

    @Autowired
    public DynamicPriceService(IDynamicPricePersistenceAdapter persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public DynamicPrice create(DynamicPrice dynamicPrice) {
        if (dynamicPrice.getMinPrice() > dynamicPrice.getMaxPrice()) {
            throw new IllegalArgumentException("El precio mínimo no puede ser mayor al máximo.");
        }
        return persistencePort.save(dynamicPrice);
    }

    @Override
    public Optional<DynamicPrice> findById(Long id) {
        return persistencePort.findById(id);
    }

    @Override
    public List<DynamicPrice> findByParkingId(Long parkingId) {
        return persistencePort.findByParkingId(parkingId);
    }

    @Override
    public DynamicPrice update(DynamicPrice dynamicPrice) {
        return persistencePort.save(dynamicPrice);
    }

    @Override
    public void deleteById(Long id) {
        persistencePort.deleteById(id);
    }
}
