package com.nomorelaps.adapters.out.persistence.interfaces;

import java.util.List;
import com.nomorelaps.domain.models.DynamicPrice;

/**
 * Persistence secondary port for {@link DynamicPrice}.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface IDynamicPricePersistenceAdapter extends IBasePersistenceAdapter<DynamicPrice, Long> {
    /**
     * Finds and lists the dynamic prices assigned to a parking lot.
     * @param parkingId Parking identifier.
     * @return Collection of dynamic prices based on hours and days for that parking lot.
     */
    List<DynamicPrice> findByParkingId(Long parkingId);
}
