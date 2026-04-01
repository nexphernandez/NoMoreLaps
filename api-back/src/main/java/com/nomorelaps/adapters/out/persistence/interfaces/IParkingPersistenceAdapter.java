package com.nomorelaps.adapters.out.persistence.interfaces;

import java.util.List;

import com.nomorelaps.domain.models.Parking;

/**
 * Persistence secondary port for {@link Parking}.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface IParkingPersistenceAdapter extends IBasePersistenceAdapter<Parking,Long>{
    /**
     * Retrieves a list containing all parking lots registered and managed by a specific company.
     * 
     * @param companyId The unique identifier (ID) of the company.
     * @return A list of {@link Parking} entities belonging to the company.
     *         If it has no parking lots, an empty list is returned.
     */
    List<Parking> findByCompanyId(Long companyId);
}
