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
import com.nomorelaps.business.interfaces.INotificationService;
import com.nomorelaps.domain.models.Reservation;
import com.nomorelaps.domain.models.Sanction;
import com.nomorelaps.domain.models.Notification;

/**
 * Background task to monitor and manage reservation lifecycles.
 * Automatically applies sanctions to expired reservations.
 */
@Component
public class ReservationScheduler {

    private final IReservationService reservationService;
    private final ISanctionService sanctionService;
    private final INotificationService notificationService;

    /**
     * Constructor for ReservationScheduler.
     * 
     * @param reservationService service for managing reservations
     * @param sanctionService service for applying sanctions
     * @param notificationService service for sending automated alerts
     */
    @Autowired
    public ReservationScheduler(IReservationService reservationService, 
                                ISanctionService sanctionService,
                                INotificationService notificationService) {
        this.reservationService = reservationService;
        this.sanctionService = sanctionService;
        this.notificationService = notificationService;
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

        if (parking != null && parking.getCompany() != null && parking.getCompany().getId() != null) {
            Notification notification = new Notification();
            notification.setCompanyId(parking.getCompany().getId());
            notification.setType("SANCTION");
            notification.setMessage(String.format("New sanction for %s: %.2f€ due to overtime.", 
                                    reservation.getUser() != null ? reservation.getUser().getName() : "User",
                                    totalAmount));
            notification.setRead(false);
            notificationService.create(notification);
        }
    }
}
