package com.nomorelaps.business;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nomorelaps.adapters.out.persistence.interfaces.IReservationPersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.interfaces.IParkingSpotPersistenceAdapter;
import com.nomorelaps.business.interfaces.IReservationService;
import com.nomorelaps.business.interfaces.INotificationService;
import com.nomorelaps.domain.models.Reservation;
import com.nomorelaps.domain.models.Notification;
import com.nomorelaps.domain.models.ParkingSpot;

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
    private final INotificationService notificationService;
    private final IParkingSpotPersistenceAdapter spotPersistencePort;

    /**
     * Constructor for ReservationService.
     * 
     * @param persistencePort     the persistence adapter for reservation operations
     * @param notificationService domain service for automated notifications
     * @param spotPersistencePort the persistence adapter for parking spot lookup
     */
    @Autowired
    public ReservationService(IReservationPersistenceAdapter persistencePort,
            INotificationService notificationService,
            IParkingSpotPersistenceAdapter spotPersistencePort) {
        this.persistencePort = persistencePort;
        this.notificationService = notificationService;
        this.spotPersistencePort = spotPersistencePort;
    }

    @Override
    @Transactional
    public Reservation create(Reservation reservation) {
        if (reservation.getEndTime().isBefore(reservation.getStartTime())) {
            throw new IllegalArgumentException("BusinessRuleException: End time cannot be before start time.");
        }

        if (reservation.getParkingSpot() != null && reservation.getParkingSpot().getId() != null) {
            boolean isOccupied = persistencePort.hasOverlappingReservations(
                    reservation.getParkingSpot().getId(),
                    reservation.getStartTime(),
                    reservation.getEndTime());

            if (isOccupied) {
                throw new IllegalStateException("Conflict: The spot is already reserved for the selected time slot.");
            }

        }

        reservation.setState("ACTIVE");
        if (reservation.getBasePrice() == null) {
            reservation.setBasePrice(reservation.getPrice());
        }
        Reservation saved = persistencePort.save(reservation);

        try {
            if (saved.getParkingSpot() != null && saved.getParkingSpot().getId() != null) {
                Optional<ParkingSpot> spotOpt = spotPersistencePort.findById(saved.getParkingSpot().getId());
                if (spotOpt.isPresent() && spotOpt.get().getParking() != null
                        && spotOpt.get().getParking().getCompany() != null) {
                    Long companyId = spotOpt.get().getParking().getCompany().getId();
                    if (companyId != null) {
                        Notification notification = new Notification();
                        notification.setCompanyId(companyId);
                        notification.setType("RESERVATION");
                        notification.setMessage("New reservation received from " +
                                (saved.getUser() != null ? saved.getUser().getName() : "a user") +
                                " at " + spotOpt.get().getParking().getName());
                        notification.setIsRead(false);
                        notificationService.create(notification);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error creating notification: " + e.getMessage());
        }

        return saved;
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
    @Transactional
    public Reservation update(Reservation reservation) {
        if (reservation.getId() == null) {
            throw new IllegalArgumentException("Cannot update reservation without ID");
        }

        if (reservation.getEndTime().isBefore(reservation.getStartTime())) {
            throw new IllegalArgumentException("BusinessRuleException: End time cannot be before start time.");
        }

        if (reservation.getParkingSpot() != null && reservation.getParkingSpot().getId() != null) {
            boolean isOccupied = persistencePort.hasOverlappingReservationsExcluding(
                    reservation.getParkingSpot().getId(),
                    reservation.getStartTime(),
                    reservation.getEndTime(),
                    reservation.getId());

            if (isOccupied) {
                throw new IllegalStateException(
                        "Conflict: The new time slot overlaps with another existing reservation.");
            }
        }

        return persistencePort.save(reservation);
    }

    @Override
    public boolean hasOverlappingReservations(Long spotId, LocalDateTime start, LocalDateTime end) {
        return persistencePort.hasOverlappingReservations(spotId, start, end);
    }

    @Override
    public boolean hasOverlappingReservationsExcluding(Long spotId, LocalDateTime start, LocalDateTime end,
            Long excludeId) {
        return persistencePort.hasOverlappingReservationsExcluding(spotId, start, end, excludeId);
    }

    @Override
    public List<Reservation> findByCompanyId(Long companyId) {
        return persistencePort.findByCompanyId(companyId);
    }

    @Override
    public void deleteById(Long id) {
        persistencePort.deleteById(id);
    }

    @Override
    @Transactional
    public Reservation updatePaymentStatus(Long id, boolean paid) {
        Reservation reservation = persistencePort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        reservation.setPaid(paid);
        
        if (paid) {
            reservation.setState("COMPLETED");
            if (reservation.getParkingSpot() != null) {
                reservation.getParkingSpot().setState(true); 
                spotPersistencePort.save(reservation.getParkingSpot());
            }
        }
        
        if (reservation.getSanctions() != null) {
            reservation.getSanctions().forEach(s -> s.setPaid(paid));
        }
        
        return persistencePort.save(reservation);
    }
}
