package com.nomorelaps.business.interfaces;

import java.util.List;
import java.util.Optional;

import com.nomorelaps.domain.models.Reservation;

/**
 * Inbound port (Use Case) for Reservation operations.
 * Defines the contract that the API presentation layer will consume.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface IReservationService {
    /**
     * Creates a new reservation.
     * 
     * @param reservation The reservation data.
     * @return The created reservation.
     */
    Reservation create(Reservation reservation);

    /**
     * Finds a reservation by ID.
     * 
     * @param id The reservation ID.
     * @return Optional reservation.
     */
    Optional<Reservation> findById(Long id);

    /**
     * Lists all reservations for a specific user.
     * 
     * @param userId The user ID.
     * @return List of reservations.
     */
    List<Reservation> findByUserId(Long userId);

    /**
     * Lists all reservations for a specific parking spot.
     * 
     * @param spotId The spot ID.
     * @return List of reservations.
     */
    List<Reservation> findByParkingSpotId(Long spotId);

    /**
     * Lists reservations by their current state (e.g., ACTIVA).
     * 
     * @param state The state string.
     * @return List of reservations.
     */
    List<Reservation> findByState(String state);

    /**
     * Updates an existing reservation.
     * 
     * @param reservation Updated data.
     * @return Updated reservation.
     */
    Reservation update(Reservation reservation);

    /**
     * Deletes a reservation.
     * 
     * @param id The reservation ID.
     */
    void deleteById(Long id);
}
