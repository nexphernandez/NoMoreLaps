package com.nomorelaps.infrastructure.scheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.business.interfaces.IReservationService;
import com.nomorelaps.business.interfaces.ISanctionService;
import com.nomorelaps.domain.models.Parking;
import com.nomorelaps.domain.models.ParkingSpot;
import com.nomorelaps.domain.models.Reservation;
import com.nomorelaps.domain.models.User;

@ExtendWith(MockitoExtension.class)
class ReservationSchedulerTest {

    @Mock
    private IReservationService reservationService;

    @Mock
    private ISanctionService sanctionService;

    @InjectMocks
    private ReservationScheduler scheduler;

    private Reservation reservation;
    private ParkingSpot parkingSpot;
    private Parking parking;

    @BeforeEach
    void setUp() {
        parking = new Parking(1L);
        parking.setSanctionAmount(5.0);
        parking.setSanctionIntervalInMinutes(15);

        parkingSpot = new ParkingSpot(1L);
        parkingSpot.setParking(parking);

        reservation = new Reservation(1L);
        reservation.setParkingSpot(parkingSpot);
        reservation.setUser(new User(1L));
        reservation.setState("ACTIVE");
    }

    @Test
    @DisplayName("Should do nothing when no active reservations exist")
    void shouldDoNothingWhenNoReservations() {
        when(reservationService.findByState("ACTIVE")).thenReturn(Collections.emptyList());

        scheduler.checkExpiredReservations();

        verify(reservationService, never()).update(any());
        verify(sanctionService, never()).create(any());
    }

    @Test
    @DisplayName("Should do nothing when reservations are not expired")
    void shouldDoNothingWhenNotExpired() {
        reservation.setEndTime(LocalDateTime.now().plusHours(1));
        when(reservationService.findByState("ACTIVE")).thenReturn(List.of(reservation));

        scheduler.checkExpiredReservations();

        verify(reservationService, never()).update(any());
        verify(sanctionService, never()).create(any());
    }

    @Test
    @DisplayName("Should apply sanction when reservation is expired and policy is defined")
    void shouldApplySanctionWhenExpired() {
        reservation.setEndTime(LocalDateTime.now().minusMinutes(20)); 
        when(reservationService.findByState("ACTIVE")).thenReturn(List.of(reservation));

        scheduler.checkExpiredReservations();

        verify(reservationService, times(1)).update(reservation);
        verify(sanctionService, times(1)).create(any());
    }

    @Test
    @DisplayName("Should skip fine when parking is null")
    void shouldSkipFineWhenParkingIsNull() {
        reservation.setEndTime(LocalDateTime.now().minusMinutes(20));
        parkingSpot.setParking(null);
        when(reservationService.findByState("ACTIVE")).thenReturn(List.of(reservation));

        scheduler.checkExpiredReservations();

        verify(reservationService, times(1)).update(reservation); 
        verify(sanctionService, never()).create(any()); 
    }

    @Test
    @DisplayName("Should skip fine when sanctionAmount is null")
    void shouldSkipFineWhenSanctionAmountIsNull() {
        reservation.setEndTime(LocalDateTime.now().minusMinutes(20));
        parking.setSanctionAmount(null);
        when(reservationService.findByState("ACTIVE")).thenReturn(List.of(reservation));

        scheduler.checkExpiredReservations();

        verify(reservationService, times(1)).update(reservation);
        verify(sanctionService, never()).create(any());
    }

    @Test
    @DisplayName("Should skip fine when sanctionAmount is 0")
    void shouldSkipFineWhenSanctionAmountIsZero() {
        reservation.setEndTime(LocalDateTime.now().minusMinutes(20));
        parking.setSanctionAmount(0.0);
        when(reservationService.findByState("ACTIVE")).thenReturn(List.of(reservation));

        scheduler.checkExpiredReservations();

        verify(reservationService, times(1)).update(reservation);
        verify(sanctionService, never()).create(any());
    }

    @Test
    @DisplayName("Should use default interval of 15 min when sanctionInterval is null")
    void shouldUseDefaultIntervalWhenNull() {
        reservation.setEndTime(LocalDateTime.now().minusMinutes(20)); 
        parking.setSanctionIntervalInMinutes(null); 
        when(reservationService.findByState("ACTIVE")).thenReturn(List.of(reservation));

        scheduler.checkExpiredReservations();

        verify(reservationService, times(1)).update(reservation);
        verify(sanctionService, times(1)).create(any());
    }
}
