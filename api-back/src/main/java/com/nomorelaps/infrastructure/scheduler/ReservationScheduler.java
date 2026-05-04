package com.nomorelaps.infrastructure.scheduler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nomorelaps.business.interfaces.IReservationService;
import com.nomorelaps.business.interfaces.ISanctionService;
import com.nomorelaps.domain.models.Reservation;
import com.nomorelaps.domain.models.Sanction;

/**
 * Background task to monitor and manage reservation lifecycles.
 * Automatically applies sanctions to expired reservations.
 */
@Component
public class ReservationScheduler {

    private final IReservationService reservationService;
    private final ISanctionService sanctionService;

    @Autowired
    public ReservationScheduler(IReservationService reservationService, ISanctionService sanctionService) {
        this.reservationService = reservationService;
        this.sanctionService = sanctionService;
    }

    /**
     * Checks for active reservations that have exceeded their end time.
     * Runs every minute.
     */
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void checkExpiredReservations() {
        System.out.println("--- SCHEDULER: Checking for expired reservations ---");
        
        List<Reservation> activeReservations = reservationService.findByState("ACTIVE");
        LocalDateTime now = LocalDateTime.now();

        for (Reservation reservation : activeReservations) {
            if (reservation.getEndTime().isBefore(now)) {
                System.out.println("Found expired reservation: " + reservation.getId());
                applySanction(reservation);
            }
        }
    }

    private void applySanction(Reservation reservation) {
        reservation.setState("SANCTIONED");
        reservationService.update(reservation);

        com.nomorelaps.domain.models.Parking parking = reservation.getParkingSpot().getParking();
        Double rate = (parking != null && parking.getSanctionAmount() != null) ? parking.getSanctionAmount() : 0.0;
        Integer interval = (parking != null && parking.getSanctionIntervalInMinutes() != null) ? parking.getSanctionIntervalInMinutes() : 15;

        if (rate <= 0) {
            System.out.println("Reservation " + reservation.getId() + " expired but no sanction policy defined. Skipping fine.");
            return;
        }

        long minutesOverdue = Duration.between(reservation.getEndTime(), LocalDateTime.now()).toMinutes();
        long intervalsPassed = (long) Math.ceil((double) minutesOverdue / interval);
        double totalAmount = intervalsPassed * rate;

        Sanction sanction = new Sanction();
        sanction.setAmount(totalAmount);
        sanction.setReason(String.format("Overtime (%d min) for reservation #%d. Policy: %.2f€ per %d min.", 
                           minutesOverdue, reservation.getId(), rate, interval));
        sanction.setArrivalTime(LocalDateTime.now());
        sanction.setPaid(false);
        sanction.setReservation(reservation);
        sanction.setUser(reservation.getUser());

        sanctionService.create(sanction);
        System.out.println("Applied dynamic sanction of " + totalAmount + "€ to user for reservation " + reservation.getId());
    }
}
