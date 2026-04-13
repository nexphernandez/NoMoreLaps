package com.nomorelaps.business.interfaces;

import java.util.List;
import java.util.Optional;
import com.nomorelaps.domain.models.DynamicPrice;

/**
 * Inbound port (Use Case) for DynamicPrice operations.
 * Defines the contract that the API presentation layer will consume.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface IDynamicPriceService {
    DynamicPrice create(DynamicPrice dynamicPrice);
    Optional<DynamicPrice> findById(Long id);
    List<DynamicPrice> findByParkingId(Long parkingId);
    DynamicPrice update(DynamicPrice dynamicPrice);
    void deleteById(Long id);
}
