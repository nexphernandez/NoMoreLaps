package com.nomorelaps.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nomorelaps.adapters.out.persistence.interfaces.IReservationPersistenceAdapter;
import com.nomorelaps.business.interfaces.IReservationService;
import com.nomorelaps.domain.models.Reservation;

/**
 * Use Case implementation for Reservation operations.
 * Connects the API layer with the Domain and Persistence layers.
 * 
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Service
public class ReservationService implements IReservationService {

    private final IReservationPersistenceAdapter persistencePort;

    @Autowired
    public ReservationService(IReservationPersistenceAdapter persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    @Transactional
    public Reservation create(Reservation reservation) {
        if (reservation.getEndTime().isBefore(reservation.getStartTime())) {
            throw new IllegalArgumentException("BusinessRuleException: La hora de finalización no puede ser previa a la de inicio.");
        }

        if (reservation.getParkingSpot() != null && reservation.getParkingSpot().getId() != null) {
            boolean isOccupied = persistencePort.hasOverlappingReservations(
                reservation.getParkingSpot().getId(), 
                reservation.getStartTime(), 
                reservation.getEndTime()
            );

            if (isOccupied) {
                throw new IllegalStateException("Conflict: La plaza ya está reservada en el horario seleccionado.");
            }
            
        }

        reservation.setState("ACTIVE");
        return persistencePort.save(reservation);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return persistencePort.findById(id);
    }

    @Override
    public List<Reservation> findByUserId(Long userId) {
        return persistencePort.findByUserId(userId);
    }

    @Override
    public List<Reservation> findByParkingSpotId(Long spotId) {
        return persistencePort.findByParkingSpotId(spotId);
    }

    @Override
    public List<Reservation> findByParkingId(Long parkingId) {
        return persistencePort.findByParkingId(parkingId);
    }

    @Override
    public List<Reservation> findByState(String state) {
        return persistencePort.findByState(state);
    }

    @Override
    public Reservation update(Reservation reservation) {
        return persistencePort.save(reservation);
    }

    @Override
    public boolean hasOverlappingReservations(Long spotId, java.time.LocalDateTime start, java.time.LocalDateTime end) {
        return persistencePort.hasOverlappingReservations(spotId, start, end);
    }

    @Override
    public void deleteById(Long id) {
        persistencePort.deleteById(id);
    }
}
