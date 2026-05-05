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
    /**
     * Creates a new sanction record.
     * 
     * @param sanction The sanction data.
     * @return The created sanction.
     */
    Sanction create(Sanction sanction);

    /**
     * Finds a sanction by ID.
     * 
     * @param id The ID.
     * @return Optional sanction.
     */
    Optional<Sanction> findById(Long id);

    /**
     * Lists all sanctions for a specific user.
     * 
     * @param userId The user ID.
     * @return List of sanctions.
     */
    List<Sanction> findByUserId(Long userId);

    /**
     * Lists all sanctions associated with a reservation.
     * 
     * @param reservationId The reservation ID.
     * @return List of sanctions.
     */
    List<Sanction> findByReservationId(Long reservationId);

    /**
     * Updates an existing sanction.
     * 
     * @param sanction Updated data.
     * @return Updated sanction.
     */
    Sanction update(Sanction sanction);

    /**
     * Deletes a sanction.
     * 
     * @param id The ID.
     */
    void deleteById(Long id);

    /**
     * Marks a specific sanction as paid in the system.
     * 
     * @param id The unique identifier of the sanction.
     * @return The updated {@link Sanction} with paid status set to true.
     */
    Sanction paySanction(Long id);
}
