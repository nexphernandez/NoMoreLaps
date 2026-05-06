package com.nomorelaps.adapters.out.persistence.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import com.nomorelaps.domain.models.Reservation;

/**
 * Persistence secondary port for {@link Reservation}.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface IReservationPersistenceAdapter extends IBasePersistenceAdapter<Reservation,Long>{

    /**
     * Retrieves the reservation history associated with a specific user.
     * Useful for checking the user's account history on the platform.
     * 
     * @param userId The unique identifier of the user in the system.
     * @return A list of {@link Reservation} entities with their reservations.
     */
    List<Reservation> findByUserId(Long userId);
    
    /**
     * Gets the list of reservations linked to a specific parking spot.
     * Allows companies to view and organize the spot's occupancy schedule.
     * 
     * @param spotId The identifier of the parking spot.
     * @return The reservations made on the parking spot.
     */
    List<Reservation> findByParkingSpotId(Long spotId);
    
    /**
     * Gets all reservations for all spots in a specific parking.
     * 
     * @param parkingId The parking ID.
     * @return List of reservations.
     */
    List<Reservation> findByParkingId(Long parkingId);
    
    /**
     * Finds and lists reservations based on their current state (e.g., CONFIRMADA, FINALIZADA, CANCELADA).
     * Allows filtering occupancy slots easily.
     * 
     * @param state The textual state that characterizes the reservation.
     * @return List of reservations that exactly match the provided state.
     */
    List<Reservation> findByState(String state);
    
    /**
     * Checks if there are any active reservations for a spot that overlap with the requested timeframe.
     * 
     * @param spotId The parking spot ID.
     * @param start Requested start time.
     * @param end Requested end time.
     * @return true if there is a conflict, false otherwise.
     */
    boolean hasOverlappingReservations(Long spotId, LocalDateTime start, LocalDateTime end);

    /**
     * Checks for overlaps excluding a specific reservation ID (used during updates).
     * 
     * @param spotId The parking spot ID.
     * @param start Requested start time.
     * @param end Requested end time.
     * @param excludeId The ID to ignore (the one being updated).
     * @return true if there is a conflict, false otherwise.
     */
    boolean hasOverlappingReservationsExcluding(Long spotId, LocalDateTime start, LocalDateTime end, Long excludeId);

    /**
     * Retrieves all reservations associated with a specific company's parkings.
     * 
     * @param companyId The company ID.
     * @return A list of matching reservations.
     */
    List<Reservation> findByCompanyId(Long companyId);
}
