package com.nomorelaps.adapters.out.persistence.interfaces;

import java.util.List;
import com.nomorelaps.adapters.out.persistence.jpa.SanctionJpaEntity;

/**
 * Persistence secondary port for {@link SanctionJpaEntity}.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface ISanctionPersistenceAdapter extends IBasePersistenceAdapter<SanctionJpaEntity, Long> {
    
    /**
     * Finds all penalizing sanctions imposed on a user globally.
     * @param userId The user to investigate.
     * @return List of sanctions.
     */
    List<SanctionJpaEntity> findByUserId(Long userId);

    /**
     * Finds the sanctions derived from and directly linked to a single reservation.
     * @param reservationId Identifier of the reservation.
     * @return List of possible sanctions for that reservation.
     */
    List<SanctionJpaEntity> findByReservationId(Long reservationId);
}
