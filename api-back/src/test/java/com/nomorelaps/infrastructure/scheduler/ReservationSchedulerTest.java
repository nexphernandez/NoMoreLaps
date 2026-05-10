package com.nomorelaps.infrastructure.scheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.nomorelaps.business.interfaces.INotificationService;
import com.nomorelaps.domain.models.Company;
import com.nomorelaps.domain.models.Parking;
import com.nomorelaps.domain.models.ParkingSpot;
import com.nomorelaps.domain.models.Reservation;
import com.nomorelaps.domain.models.User;

/**
 * Unit tests for ReservationScheduler.
 * Adheres to the New Backend Test Refactoring Plan for granularity and business naming.
 * Verifies background tasks for expiring reservations and applying penalty logic.
 */
@ExtendWith(MockitoExtension.class)
class ReservationSchedulerTest {

    @Mock
    private IReservationService reservationService;

    @Mock
    private ISanctionService sanctionService;

    @Mock
    private INotificationService notificationService;

    @InjectMocks
    private ReservationScheduler reservationScheduler;

    private Reservation activeReservation;
    private ParkingSpot testSpot;
    private Parking testParking;

    @BeforeEach
    void setUp() {
        testParking = new Parking(1L);
        testParking.setSanctionAmount(5.0);
        testParking.setSanctionIntervalInMinutes(15);

        testSpot = new ParkingSpot(1L);
        testSpot.setParking(testParking);

        activeReservation = new Reservation(1L);
        activeReservation.setParkingSpot(testSpot);
        activeReservation.setUser(new User(1L));
        activeReservation.setState("ACTIVE");
        activeReservation.setPrice(10.0);
    }


    @Test
    @DisplayName("checkExpiredReservations - No active: Should not perform updates")
    void checkExpiredReservations_NoActive_ShouldPerformNoActions() {
        when(reservationService.findByState("ACTIVE")).thenReturn(Collections.emptyList());
        reservationScheduler.checkExpiredReservations();
        verify(reservationService, never()).update(any());
    }

    @Test
    @DisplayName("checkExpiredReservations - Future end time: Should skip update")
    void checkExpiredReservations_FutureEnd_ShouldSkipUpdate() {
        activeReservation.setEndTime(LocalDateTime.now().plusHours(1));
        when(reservationService.findByState("ACTIVE")).thenReturn(List.of(activeReservation));
        reservationScheduler.checkExpiredReservations();
        verify(reservationService, never()).update(any());
    }

    @Test
    @DisplayName("checkExpiredReservations - Past end time: Should transition state to SANCTIONED")
    void checkExpiredReservations_PastEnd_ShouldTransitionState() {
        activeReservation.setEndTime(LocalDateTime.now().minusMinutes(5));
        when(reservationService.findByState("ACTIVE")).thenReturn(List.of(activeReservation));
        reservationScheduler.checkExpiredReservations();
        assertEquals("SANCTIONED", activeReservation.getState());
    }


    @Test
    @DisplayName("applySanction - Parking missing: Should skip fine creation")
    void applySanction_ParkingMissing_ShouldSkipFineCreation() {
        activeReservation.setEndTime(LocalDateTime.now().minusMinutes(5));
        testSpot.setParking(null);
        when(reservationService.findByState("ACTIVE")).thenReturn(List.of(activeReservation));
        reservationScheduler.checkExpiredReservations();
        verify(sanctionService, never()).create(any());
    }

    @Test
    @DisplayName("applySanction - Amount missing: Should skip fine creation")
    void applySanction_AmountMissing_ShouldSkipFineCreation() {
        activeReservation.setEndTime(LocalDateTime.now().minusMinutes(5));
        testParking.setSanctionAmount(null);
        when(reservationService.findByState("ACTIVE")).thenReturn(List.of(activeReservation));
        reservationScheduler.checkExpiredReservations();
        verify(sanctionService, never()).create(any());
    }

    @Test
    @DisplayName("applySanction - Rate zero: Should skip fine creation")
    void applySanction_RateZero_ShouldSkipFineCreation() {
        activeReservation.setEndTime(LocalDateTime.now().minusMinutes(5));
        testParking.setSanctionAmount(0.0);
        when(reservationService.findByState("ACTIVE")).thenReturn(List.of(activeReservation));
        reservationScheduler.checkExpiredReservations();
        verify(sanctionService, never()).create(any());
    }

    @Test
    @DisplayName("applySanction - Interval missing: Should use default 15 min and calculate correctly")
    void applySanction_IntervalMissing_ShouldUseDefault15Minutes() {
        activeReservation.setEndTime(LocalDateTime.now().minusMinutes(20));
        testParking.setSanctionIntervalInMinutes(null);
        when(reservationService.findByState("ACTIVE")).thenReturn(List.of(activeReservation));
        
        reservationScheduler.checkExpiredReservations();

        verify(sanctionService).create(argThat(s -> s.getAmount() == 10.0));
    }

    @Test
    @DisplayName("applySanction - Valid policy: Should update reservation price")
    void applySanction_ValidPolicy_ShouldIncrementReservationPrice() {
        activeReservation.setEndTime(LocalDateTime.now().minusMinutes(10));
        activeReservation.setPrice(10.0);
        when(reservationService.findByState("ACTIVE")).thenReturn(List.of(activeReservation));
        
        reservationScheduler.checkExpiredReservations();

        assertEquals(15.0, activeReservation.getPrice());
    }


    @Test
    @DisplayName("applySanction - Valid company: Should create notification for owner")
    void applySanction_ValidCompany_ShouldCreateNotification() {
        activeReservation.setEndTime(LocalDateTime.now().minusMinutes(5));
        Company owner = new Company(100L);
        testParking.setCompany(owner);
        when(reservationService.findByState("ACTIVE")).thenReturn(List.of(activeReservation));
        
        reservationScheduler.checkExpiredReservations();

        verify(notificationService).create(argThat(n -> n.getCompanyId().equals(100L)));
    }

    @Test
    @DisplayName("applySanction - User exists: Should use name in notification")
    void applySanction_UserExists_ShouldIncludeNameInMessage() {
        activeReservation.setEndTime(LocalDateTime.now().minusMinutes(5));
        activeReservation.getUser().setName("Alice");
        testParking.setCompany(new Company(100L));
        when(reservationService.findByState("ACTIVE")).thenReturn(List.of(activeReservation));
        
        reservationScheduler.checkExpiredReservations();

        verify(notificationService).create(argThat(n -> n.getMessage().contains("Alice")));
    }

    @Test
    @DisplayName("applySanction - User missing: Should use generic 'User' in notification")
    void applySanction_UserMissing_ShouldIncludeGenericLabelInMessage() {
        activeReservation.setEndTime(LocalDateTime.now().minusMinutes(5));
        activeReservation.setUser(null);
        testParking.setCompany(new Company(100L));
        when(reservationService.findByState("ACTIVE")).thenReturn(List.of(activeReservation));
        
        reservationScheduler.checkExpiredReservations();

        verify(notificationService).create(argThat(n -> n.getMessage().contains("User")));
    }

    @Test
    @DisplayName("applySanction - Spot missing: Should skip all actions")
    void applySanction_SpotMissing_ShouldSkipAllActions() {
        activeReservation.setEndTime(LocalDateTime.now().minusMinutes(5));
        activeReservation.setParkingSpot(null);
        when(reservationService.findByState("ACTIVE")).thenReturn(List.of(activeReservation));
        
        reservationScheduler.checkExpiredReservations();

        verify(sanctionService, never()).create(any());
        verify(notificationService, never()).create(any());
    }

    @Test
    @DisplayName("applySanction - Parking missing: Should return early")
    void applySanction_ParkingMissing_ShouldReturnEarly() {
        activeReservation.setEndTime(LocalDateTime.now().minusMinutes(5));
        testSpot.setParking(null);
        when(reservationService.findByState("ACTIVE")).thenReturn(List.of(activeReservation));
        
        reservationScheduler.checkExpiredReservations();

        verify(sanctionService, never()).create(any());
        verify(notificationService, never()).create(any());
    }

    @Test
    @DisplayName("applySanction - Company missing: Should skip notification creation")
    void applySanction_CompanyMissing_ShouldSkipNotification() {
        activeReservation.setEndTime(LocalDateTime.now().minusMinutes(5));
        testParking.setCompany(null);
        when(reservationService.findByState("ACTIVE")).thenReturn(List.of(activeReservation));
        
        reservationScheduler.checkExpiredReservations();

        verify(notificationService, never()).create(any());
    }

    @Test
    @DisplayName("applySanction - Company ID missing: Should skip notification creation")
    void applySanction_CompanyIdMissing_ShouldSkipNotification() {
        activeReservation.setEndTime(LocalDateTime.now().minusMinutes(5));
        Company owner = new Company(); // ID is null
        testParking.setCompany(owner);
        when(reservationService.findByState("ACTIVE")).thenReturn(List.of(activeReservation));
        
        reservationScheduler.checkExpiredReservations();

        verify(notificationService, never()).create(any());
    }
}
