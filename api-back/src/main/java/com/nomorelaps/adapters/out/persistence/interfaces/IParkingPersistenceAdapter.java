package com.nomorelaps.adapters.out.persistence.interfaces;

import java.util.List;

import com.nomorelaps.adapters.out.persistence.jpa.ParkingJpaEntity;

/**
 * Persistence secondary port for {@link ParkingJpaEntity}.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface IParkingPersistenceAdapter extends IBasePersistenceAdapter<ParkingJpaEntity,Long>{
    /**
     * Retrieves a list containing all parking lots registered and managed by a specific company.
     * 
     * @param companyId The unique identifier (ID) of the company.
     * @return A list of {@link ParkingJpaEntity} entities belonging to the company.
     *         If it has no parking lots, an empty list is returned.
     */
    List<ParkingJpaEntity> findByCompanyId(Long companyId);
}
