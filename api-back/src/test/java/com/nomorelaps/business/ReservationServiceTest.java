package com.nomorelaps.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.adapters.out.persistence.interfaces.IReservationPersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.interfaces.IParkingSpotPersistenceAdapter;
import com.nomorelaps.business.interfaces.INotificationService;
import com.nomorelaps.domain.models.Company;
import com.nomorelaps.domain.models.Notification;
import com.nomorelaps.domain.models.Parking;
import com.nomorelaps.domain.models.ParkingSpot;
import com.nomorelaps.domain.models.Reservation;
import com.nomorelaps.domain.models.Sanction;

/**
 * Unit tests for ReservationService.
 * Verifies all business rules and repository delegations with exhaustive branch coverage.
 */
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private IReservationPersistenceAdapter persistencePort;

    @Mock
    private INotificationService notificationService;

    @Mock
    private IParkingSpotPersistenceAdapter spotPersistencePort;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation validReservation;
    private ParkingSpot validSpot;

    @BeforeEach
    void setUp() {
        validSpot = new ParkingSpot(1L);
        validReservation = new Reservation();
        validReservation.setStartTime(LocalDateTime.now().plusHours(1));
        validReservation.setEndTime(LocalDateTime.now().plusHours(2));
        validReservation.setParkingSpot(validSpot);
        validReservation.setPrice(10.0);
    }

    @Test
    @DisplayName("create - Should throw exception when end time is before start time")
    void create_ShouldThrowIfEndTimeInvalid() {
        validReservation.setEndTime(validReservation.getStartTime().minusHours(1));
        assertThrows(IllegalArgumentException.class, () -> reservationService.create(validReservation));
    }

    @Test
    @DisplayName("create - Should skip overlap check if spot is null")
    void create_ShouldSkipOverlapIfSpotNull() {
        validReservation.setParkingSpot(null);
        when(persistencePort.save(any())).thenReturn(validReservation);
        reservationService.create(validReservation);
        verify(persistencePort, never()).hasOverlappingReservations(any(), any(), any());
    }

    @Test
    @DisplayName("create - Should skip overlap check if spot ID is null")
    void create_ShouldSkipOverlapIfSpotIdNull() {
        validReservation.getParkingSpot().setId(null);
        when(persistencePort.save(any())).thenReturn(validReservation);
        reservationService.create(validReservation);
        verify(persistencePort, never()).hasOverlappingReservations(any(), any(), any());
    }

    @Test
    @DisplayName("create - Should throw exception if spot overlaps")
    void create_ShouldThrowIfOverlap() {
        when(persistencePort.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> reservationService.create(validReservation));
    }

    @Test
    @DisplayName("create - Should set basePrice if null")
    void create_ShouldSetBasePriceIfNull() {
        validReservation.setBasePrice(null);
        when(persistencePort.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(false);
        when(persistencePort.save(any())).thenAnswer(i -> i.getArgument(0));
        Reservation result = reservationService.create(validReservation);
        assertEquals(10.0, result.getBasePrice());
    }

    @Test
    @DisplayName("create - Should send notification if all metadata is present")
    void create_ShouldSendNotificationWhenMetadataComplete() {
        Parking parking = new Parking(); parking.setName("P1"); parking.setCompany(new Company(10L));
        validSpot.setParking(parking);
        when(persistencePort.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(false);
        when(persistencePort.save(any())).thenReturn(validReservation);
        when(spotPersistencePort.findById(1L)).thenReturn(Optional.of(validSpot));

        reservationService.create(validReservation);
        verify(notificationService).create(any(Notification.class));
    }

    @Test
    @DisplayName("create - Should skip notification if spotOpt is not present")
    void create_ShouldSkipNotificationIfSpotNotFound() {
        when(persistencePort.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(false);
        when(persistencePort.save(any())).thenReturn(validReservation);
        when(spotPersistencePort.findById(1L)).thenReturn(Optional.empty());

        reservationService.create(validReservation);
        verify(notificationService, never()).create(any());
    }

    @Test
    @DisplayName("create - Should skip notification if parking is null")
    void create_ShouldSkipNotificationIfParkingNull() {
        validSpot.setParking(null);
        when(persistencePort.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(false);
        when(persistencePort.save(any())).thenReturn(validReservation);
        when(spotPersistencePort.findById(1L)).thenReturn(Optional.of(validSpot));

        reservationService.create(validReservation);
        verify(notificationService, never()).create(any());
    }

    @Test
    @DisplayName("create - Should skip notification if company is null")
    void create_ShouldSkipNotificationIfCompanyNull() {
        Parking parking = new Parking(); parking.setCompany(null);
        validSpot.setParking(parking);
        when(persistencePort.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(false);
        when(persistencePort.save(any())).thenReturn(validReservation);
        when(spotPersistencePort.findById(1L)).thenReturn(Optional.of(validSpot));

        reservationService.create(validReservation);
        verify(notificationService, never()).create(any());
    }

    @Test
    @DisplayName("create - Should skip notification if company ID is null")
    void create_ShouldSkipNotificationIfCompanyIdNull() {
        Parking parking = new Parking(); parking.setCompany(new Company(null));
        validSpot.setParking(parking);
        when(persistencePort.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(false);
        when(persistencePort.save(any())).thenReturn(validReservation);
        when(spotPersistencePort.findById(1L)).thenReturn(Optional.of(validSpot));

        reservationService.create(validReservation);
        verify(notificationService, never()).create(any());
    }

    @Test
    @DisplayName("update - Should throw if ID missing")
    void update_ShouldThrowIfIdMissing() {
        validReservation.setId(null);
        assertThrows(IllegalArgumentException.class, () -> reservationService.update(validReservation));
    }

    @Test
    @DisplayName("update - Should throw if end time invalid")
    void update_ShouldThrowIfEndTimeInvalid() {
        validReservation.setId(1L);
        validReservation.setEndTime(validReservation.getStartTime().minusHours(1));
        assertThrows(IllegalArgumentException.class, () -> reservationService.update(validReservation));
    }

    @Test
    @DisplayName("update - Should skip overlap check if spot is null")
    void update_ShouldSkipOverlapIfSpotNull() {
        validReservation.setId(1L);
        validReservation.setParkingSpot(null);
        when(persistencePort.save(any())).thenReturn(validReservation);
        reservationService.update(validReservation);
        verify(persistencePort, never()).hasOverlappingReservationsExcluding(any(), any(), any(), any());
    }

    @Test
    @DisplayName("update - Should skip overlap check if spot ID is null")
    void update_ShouldSkipOverlapIfSpotIdNull() {
        validReservation.setId(1L);
        validReservation.setParkingSpot(new ParkingSpot());
        when(persistencePort.save(any())).thenReturn(validReservation);
        reservationService.update(validReservation);
        verify(persistencePort, never()).hasOverlappingReservationsExcluding(any(), any(), any(), any());
    }

    @Test
    @DisplayName("update - Should throw if overlap detected")
    void update_ShouldThrowIfOverlap() {
        validReservation.setId(1L);
        when(persistencePort.hasOverlappingReservationsExcluding(anyLong(), any(), any(), anyLong())).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> reservationService.update(validReservation));
    }

    @Test
    @DisplayName("updatePaymentStatus - Should throw if reservation not found")
    void updatePaymentStatus_ShouldThrowIfNotFound() {
        when(persistencePort.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> reservationService.updatePaymentStatus(1L, true));
    }

    @Test
    @DisplayName("updatePaymentStatus - Should save spot state when paid")
    void updatePaymentStatus_ShouldSaveSpotWhenPaid() {
        validReservation.setId(1L);
        when(persistencePort.findById(1L)).thenReturn(Optional.of(validReservation));
        when(persistencePort.save(any())).thenAnswer(i -> i.getArgument(0));

        reservationService.updatePaymentStatus(1L, true);
        assertTrue(validSpot.isState());
        verify(spotPersistencePort).save(validSpot);
    }

    @Test
    @DisplayName("updatePaymentStatus - Should not save spot when spot is null")
    void updatePaymentStatus_ShouldNotSaveSpotIfNull() {
        validReservation.setId(1L);
        validReservation.setParkingSpot(null);
        when(persistencePort.findById(1L)).thenReturn(Optional.of(validReservation));
        when(persistencePort.save(any())).thenAnswer(i -> i.getArgument(0));

        reservationService.updatePaymentStatus(1L, true);
        verify(spotPersistencePort, never()).save(any());
    }

    @Test
    @DisplayName("updatePaymentStatus - Should mark sanctions when paid")
    void updatePaymentStatus_ShouldUpdateSanctions() {
        validReservation.setId(1L);
        Sanction s = new Sanction(); s.setPaid(false);
        validReservation.setSanctions(new HashSet<>(Set.of(s)));
        when(persistencePort.findById(1L)).thenReturn(Optional.of(validReservation));
        when(persistencePort.save(any())).thenAnswer(i -> i.getArgument(0));

        reservationService.updatePaymentStatus(1L, true);
        assertTrue(s.isPaid());
    }

    @Test
    @DisplayName("updatePaymentStatus - Should skip sanctions if null")
    void updatePaymentStatus_ShouldSkipSanctionsIfNull() {
        validReservation.setId(1L);
        validReservation.setSanctions(null);
        when(persistencePort.findById(1L)).thenReturn(Optional.of(validReservation));
        when(persistencePort.save(any())).thenAnswer(i -> i.getArgument(0));

        assertDoesNotThrow(() -> reservationService.updatePaymentStatus(1L, true));
    }

    @Test
    @DisplayName("Delegations - findById")
    void delegation_findById() {
        when(persistencePort.findById(1L)).thenReturn(Optional.of(validReservation));
        assertTrue(reservationService.findById(1L).isPresent());
    }

    @Test
    @DisplayName("Delegations - findByUserId")
    void delegation_findByUserId() {
        when(persistencePort.findByUserId(1L)).thenReturn(List.of());
        assertEquals(0, reservationService.findByUserId(1L).size());
    }

    @Test
    @DisplayName("Delegations - findByParkingSpotId")
    void delegation_findByParkingSpotId() {
        when(persistencePort.findByParkingSpotId(1L)).thenReturn(List.of());
        assertEquals(0, reservationService.findByParkingSpotId(1L).size());
    }

    @Test
    @DisplayName("Delegations - findByParkingId")
    void delegation_findByParkingId() {
        when(persistencePort.findByParkingId(1L)).thenReturn(List.of());
        assertEquals(0, reservationService.findByParkingId(1L).size());
    }

    @Test
    @DisplayName("Delegations - findByState")
    void delegation_findByState() {
        when(persistencePort.findByState("S")).thenReturn(List.of());
        assertEquals(0, reservationService.findByState("S").size());
    }

    @Test
    @DisplayName("Delegations - findByCompanyId")
    void delegation_findByCompanyId() {
        when(persistencePort.findByCompanyId(1L)).thenReturn(List.of());
        assertEquals(0, reservationService.findByCompanyId(1L).size());
    }

    @Test
    @DisplayName("Delegations - deleteById")
    void delegation_deleteById() {
        reservationService.deleteById(1L);
        verify(persistencePort).deleteById(1L);
    }

    @Test
    @DisplayName("Delegations - hasOverlappingReservations")
    void delegation_hasOverlapping() {
        when(persistencePort.hasOverlappingReservations(any(), any(), any())).thenReturn(true);
        assertTrue(reservationService.hasOverlappingReservations(1L, null, null));
    }

    @Test
    @DisplayName("Delegations - hasOverlappingReservationsExcluding")
    void delegation_hasOverlappingExcluding() {
        when(persistencePort.hasOverlappingReservationsExcluding(any(), any(), any(), any())).thenReturn(true);
        assertTrue(reservationService.hasOverlappingReservationsExcluding(1L, null, null, 1L));
    }

    @Test
    @DisplayName("create - Should catch and log notification exception")
    void create_ShouldCatchNotificationException() {
        when(persistencePort.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(false);
        when(persistencePort.save(any())).thenReturn(validReservation);
        when(spotPersistencePort.findById(anyLong())).thenThrow(new RuntimeException("Notification Error"));

        assertDoesNotThrow(() -> reservationService.create(validReservation));
    }

    @Test
    @DisplayName("updatePaymentStatus - Should not update state if paid is false")
    void updatePaymentStatus_ShouldNotUpdateStateIfPaidIsFalse() {
        validReservation.setId(1L);
        validReservation.setPaid(true); // already paid
        validReservation.setState("ACTIVE");
        when(persistencePort.findById(1L)).thenReturn(Optional.of(validReservation));
        when(persistencePort.save(any())).thenAnswer(i -> i.getArgument(0));

        Reservation result = reservationService.updatePaymentStatus(1L, false);

        assertFalse(result.isPaid());
        assertEquals("ACTIVE", result.getState());
        verify(spotPersistencePort, never()).save(any());
    }
}
