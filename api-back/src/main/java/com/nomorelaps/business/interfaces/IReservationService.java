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
    Reservation create(Reservation reservation);
    Optional<Reservation> findById(Long id);
    List<Reservation> findByUserId(Long userId);
    List<Reservation> findByParkingSpotId(Long spotId);
    List<Reservation> findByState(String state);
    Reservation update(Reservation reservation);
    void deleteById(Long id);
}
