package com.nomorelaps.adapters.out.persistence.repository;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nomorelaps.adapters.out.persistence.jpa.ReservationJpaEntity;

/**
 * JPA Repository for {@link ReservationJpaEntity}.
 * Provides database access and CRUD operations via Spring Data.
 * 
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface ReservationJpaRepository extends JpaRepository<ReservationJpaEntity, Long> {

    /**
     * Retrieves all reservations made by a specific user with parking details.
     * Uses EntityGraph to eagerly load associated entities (ParkingSpot and
     * Parking).
     * 
     * @param userId The user ID.
     * @return A list of matching reservations.
     */
    @EntityGraph(attributePaths = { "parkingSpot", "parkingSpot.parking" })
    List<ReservationJpaEntity> findByUserId(Long userId);

    /**
     * Retrieves all reservations for a specific parking spot.
     * 
     * @param spotId The parking spot ID.
     * @return A list of matching reservations.
     */
    List<ReservationJpaEntity> findByParkingSpotId(Long spotId);

    /**
     * Retrieves all reservations for a specific parking using property path
     * navigation.
     * 
     * @param parkingId The parking ID.
     * @return A list of matching reservations.
     */
    List<ReservationJpaEntity> findByParkingSpotParkingId(Long parkingId);

    /**
     * Retrieves reservations by their state (ACTIVE, COMPLETED, etc).
     * 
     * @param state The reservation state.
     * @return A list of matching reservations.
     */
    @EntityGraph(attributePaths = { "parkingSpot", "parkingSpot.parking" })
    List<ReservationJpaEntity> findByState(String state);

    /**
     * Checks for overlapping reservations for a specific spot and timeframe.
     * Uses a derived query method: r.startTime < end AND r.endTime > start.
     * 
     * @param spotId The parking spot ID.
     * @param state  The state to filter (e.g., 'ACTIVE').
     * @param end    The end time of the check range (must be before startTime).
     * @param start  The start time of the check range (must be after endTime).
     * @return A list of overlapping reservations.
     */
    List<ReservationJpaEntity> findByParkingSpotIdAndStateAndStartTimeBeforeAndEndTimeAfter(
            Long spotId, String state, LocalDateTime end, LocalDateTime start);

    /**
     * Checks for overlapping reservations excluding a specific ID.
     * 
     * @param spotId The parking spot ID.
     * @param state The reservation state (e.g. ACTIVE).
     * @param end The end time of the range.
     * @param start The start time of the range.
     * @param id The reservation ID to exclude.
     * @return A list of conflicting reservations.
     */
    List<ReservationJpaEntity> findByParkingSpotIdAndStateAndStartTimeBeforeAndEndTimeAfterAndIdNot(
            Long spotId, String state, LocalDateTime end, LocalDateTime start, Long id);
}