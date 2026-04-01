package com.nomorelaps.business.interfaces;

import java.util.List;
import java.util.Optional;
import com.nomorelaps.domain.models.Sanction;

/**
 * Inbound port (Use Case) for Sanction operations.
 * Defines the contract that the API presentation layer will consume.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface ISanctionService {
    Sanction create(Sanction sanction);
    Optional<Sanction> findById(Long id);
    List<Sanction> findByUserId(Long userId);
    List<Sanction> findByReservationId(Long reservationId);
    Sanction update(Sanction sanction);
    void deleteById(Long id);
}
