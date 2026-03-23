package com.nomorelaps.adapters.out.persistence.interfaces;

import java.util.List;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingSpotJpaEntity;

/**
 * Persistence secondary port for {@link ParkingSpotJpaEntity}.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface IParkingSpotPersistenceAdapter extends IBasePersistenceAdapter<ParkingSpotJpaEntity, Long> {
    
    /**
     * Finds all spots belonging to a specific parking lot.
     * @param parkingId Parking identifier.
     * @return The spots registered in that parking lot.
     */
    List<ParkingSpotJpaEntity> findByParkingId(Long parkingId);

    /**
     * Finds the operational/active parking spots (state = true).
     * @param parkingId Parking identifier.
     * @return Available/enabled parking spots in the database.
     */
    List<ParkingSpotJpaEntity> findByParkingIdAndStateTrue(Long parkingId);
}
