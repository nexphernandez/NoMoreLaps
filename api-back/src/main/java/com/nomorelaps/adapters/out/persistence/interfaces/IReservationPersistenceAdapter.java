package com.nomorelaps.adapters.out.persistence.interfaces;

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
     * Finds and lists reservations based on their current state (e.g., CONFIRMADA, FINALIZADA, CANCELADA).
     * Allows filtering occupancy slots easily.
     * 
     * @param state The textual state that characterizes the reservation.
     * @return List of reservations that exactly match the provided state.
     */
    List<Reservation> findByState(String state);
}
