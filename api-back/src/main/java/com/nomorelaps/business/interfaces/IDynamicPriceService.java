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
    /**
     * Creates a new dynamic price rule.
     * 
     * @param dynamicPrice The dynamic price data.
     * @return The created dynamic price.
     */
    DynamicPrice create(DynamicPrice dynamicPrice);

    /**
     * Finds a dynamic price rule by ID.
     * 
     * @param id The ID.
     * @return Optional dynamic price.
     */
    Optional<DynamicPrice> findById(Long id);

    /**
     * Lists all dynamic price rules.
     * 
     * @return List of dynamic price rules.
     */
    List<DynamicPrice> findAll();

    /**
     * Finds dynamic price rules for a specific parking.
     * 
     * @param parkingId The parking ID.
     * @return List of dynamic price rules.
     */
    List<DynamicPrice> findByParkingId(Long parkingId);

    /**
     * Updates a dynamic price rule.
     * 
     * @param dynamicPrice Updated data.
     * @return Updated dynamic price.
     */
    DynamicPrice update(DynamicPrice dynamicPrice);

    /**
     * Deletes a dynamic price rule.
     * 
     * @param id The ID.
     */
    void deleteById(Long id);
}
