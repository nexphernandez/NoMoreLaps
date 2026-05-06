package com.nomorelaps.business.interfaces;

import java.time.LocalDateTime;
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
     * Lists all reservations for a specific parking.
     * 
     * @param parkingId The parking ID.
     * @return List of reservations.
     */
    List<Reservation> findByParkingId(Long parkingId);

    /**
     * Lists reservations by their current state (e.g., ACTIVE).
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
     * Checks if there are any overlapping reservations for a specific spot and timeframe.
     * 
     * @param spotId The spot ID.
     * @param start The start time.
     * @param end The end time.
     * @return true if there is an overlap, false otherwise.
     */
    boolean hasOverlappingReservations(Long spotId, LocalDateTime start, LocalDateTime end);
    
    /**
     * Checks for overlaps excluding a specific reservation ID.
     * 
     * @param spotId The parking spot ID.
     * @param start Requested start time.
     * @param end Requested end time.
     * @param excludeId The reservation ID to ignore.
     * @return true if there is a conflict, false otherwise.
     */
    boolean hasOverlappingReservationsExcluding(Long spotId, LocalDateTime start, LocalDateTime end, Long excludeId);

    /**
     * Lists all reservations for a specific company.
     * 
     * @param companyId The company ID.
     * @return List of reservations.
     */
    List<Reservation> findByCompanyId(Long companyId);

    /**
     * Deletes a reservation.
     * 
     * @param id The reservation ID.
     */
    void deleteById(Long id);
}
