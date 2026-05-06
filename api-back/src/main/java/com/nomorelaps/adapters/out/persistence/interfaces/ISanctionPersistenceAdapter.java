package com.nomorelaps.adapters.out.persistence.interfaces;

import java.util.List;
import com.nomorelaps.domain.models.Sanction;

/**
 * Persistence secondary port for {@link Sanction}.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface ISanctionPersistenceAdapter extends IBasePersistenceAdapter<Sanction, Long> {
    
    /**
     * Finds all penalizing sanctions imposed on a user globally.
     * @param userId The user to investigate.
     * @return List of sanctions.
     */
    List<Sanction> findByUserId(Long userId);

    /**
     * Finds the sanctions derived from and directly linked to a single reservation.
     * @param reservationId Identifier of the reservation.
     * @return List of possible sanctions for that reservation.
     */
    List<Sanction> findByReservationId(Long reservationId);
    
    /**
     * Retrieves all sanctions associated with a specific company's parkings.
     * @param companyId The company ID.
     * @return A list of matching sanctions.
     */
    List<Sanction> findByCompanyId(Long companyId);
}
