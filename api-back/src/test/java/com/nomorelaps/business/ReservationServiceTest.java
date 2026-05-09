package com.nomorelaps.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
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
import com.nomorelaps.domain.models.User;

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
    }

    @Test
    @DisplayName("Should create reservation when valid")
    void shouldCreateReservationWhenValid() {
        
        when(persistencePort.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(false);
        when(persistencePort.save(any(Reservation.class))).thenReturn(validReservation);

        Reservation created = reservationService.create(validReservation);

        assertNotNull(created);
        assertEquals("ACTIVE", created.getState());
        verify(persistencePort).save(validReservation);
    }

    @Test
    @DisplayName("Should throw exception when end time is before start time")
    void shouldThrowExceptionWhenEndTimeIsInvalid() {
        validReservation.setEndTime(validReservation.getStartTime().minusHours(1));

        assertThrows(IllegalArgumentException.class, () -> {
            reservationService.create(validReservation);
        });
        verify(persistencePort, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when reservation overlaps")
    void shouldThrowExceptionWhenOverlaps() {
        when(persistencePort.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> {
            reservationService.create(validReservation);
        });
        verify(persistencePort, never()).save(any());
    }

    @Test
    @DisplayName("Should update reservation when valid")
    void shouldUpdateReservationWhenValid() {
        validReservation.setId(100L);
        when(persistencePort.hasOverlappingReservationsExcluding(anyLong(), any(), any(), anyLong())).thenReturn(false);
        when(persistencePort.save(any(Reservation.class))).thenReturn(validReservation);

        Reservation updated = reservationService.update(validReservation);

        assertNotNull(updated);
        verify(persistencePort).save(validReservation);
    }

    @Test
    @DisplayName("Should find reservation by id")
    void shouldFindById() {
        when(persistencePort.findById(1L)).thenReturn(Optional.of(validReservation));
        Optional<Reservation> found = reservationService.findById(1L);
        assertTrue(found.isPresent());
        assertEquals(validReservation, found.get());
    }

    @Test
    @DisplayName("Should find reservations by user id")
    void shouldFindByUserId() {
        when(persistencePort.findByUserId(1L)).thenReturn(List.of(validReservation));
        List<Reservation> found = reservationService.findByUserId(1L);
        assertEquals(1, found.size());
    }

    @Test
    @DisplayName("Should find reservations by parking spot id")
    void shouldFindByParkingSpotId() {
        when(persistencePort.findByParkingSpotId(1L)).thenReturn(List.of(validReservation));
        List<Reservation> found = reservationService.findByParkingSpotId(1L);
        assertEquals(1, found.size());
    }

    @Test
    @DisplayName("Should find reservations by parking id")
    void shouldFindByParkingId() {
        when(persistencePort.findByParkingId(1L)).thenReturn(List.of(validReservation));
        List<Reservation> found = reservationService.findByParkingId(1L);
        assertEquals(1, found.size());
    }

    @Test
    @DisplayName("Should find reservations by state")
    void shouldFindByState() {
        when(persistencePort.findByState("ACTIVE")).thenReturn(List.of(validReservation));
        List<Reservation> found = reservationService.findByState("ACTIVE");
        assertEquals(1, found.size());
    }

    @Test
    @DisplayName("Should throw exception when updating reservation without ID")
    void shouldThrowExceptionWhenUpdatingWithoutId() {
        assertThrows(IllegalArgumentException.class, () -> reservationService.update(validReservation));
    }

    @Test
    @DisplayName("Should throw exception when updating with overlapping schedule")
    void shouldThrowExceptionWhenUpdateOverlaps() {
        validReservation.setId(100L);
        when(persistencePort.hasOverlappingReservationsExcluding(anyLong(), any(), any(), anyLong())).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> reservationService.update(validReservation));
    }

    @Test
    @DisplayName("Should check for overlapping reservations")
    void shouldCheckOverlapping() {
        when(persistencePort.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(true);
        assertTrue(reservationService.hasOverlappingReservations(1L, LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
    }

    @Test
    @DisplayName("Should check for overlapping reservations excluding one")
    void shouldCheckOverlappingExcluding() {
        when(persistencePort.hasOverlappingReservationsExcluding(anyLong(), any(), any(), anyLong())).thenReturn(false);
        assertFalse(reservationService.hasOverlappingReservationsExcluding(1L, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100L));
    }

    @Test
    @DisplayName("Should delete reservation by id")
    void shouldDeleteById() {
        doNothing().when(persistencePort).deleteById(1L);
        reservationService.deleteById(1L);
        verify(persistencePort).deleteById(1L);
    }

    @Test
    @DisplayName("Should create reservation when parking spot is null (no overlap check)")
    void shouldCreateWhenSpotIsNull() {
        validReservation.setParkingSpot(null);
        when(persistencePort.save(any(Reservation.class))).thenReturn(validReservation);

        Reservation created = reservationService.create(validReservation);

        assertNotNull(created);
        verify(persistencePort, never()).hasOverlappingReservations(anyLong(), any(), any());
        verify(persistencePort).save(validReservation);
    }

    @Test
    @DisplayName("Should throw when updating with end time before start time")
    void shouldThrowWhenUpdateEndTimeInvalid() {
        validReservation.setId(100L);
        validReservation.setEndTime(validReservation.getStartTime().minusHours(1));

        assertThrows(IllegalArgumentException.class, () -> reservationService.update(validReservation));
        verify(persistencePort, never()).save(any());
    }

    @Test
    @DisplayName("Should update when parking spot is null (no overlap check)")
    void shouldUpdateWhenSpotIsNull() {
        validReservation.setId(100L);
        validReservation.setParkingSpot(null);
        when(persistencePort.save(any(Reservation.class))).thenReturn(validReservation);

        Reservation updated = reservationService.update(validReservation);

        assertNotNull(updated);
        verify(persistencePort, never()).hasOverlappingReservationsExcluding(anyLong(), any(), any(), anyLong());
        verify(persistencePort).save(validReservation);
    }

    @Test
    @DisplayName("Should create reservation when parking spot ID is null (no overlap check)")
    void shouldCreateWhenSpotIdIsNull() {
        validReservation.getParkingSpot().setId(null);
        when(persistencePort.save(any(Reservation.class))).thenReturn(validReservation);

        Reservation created = reservationService.create(validReservation);

        assertNotNull(created);
        verify(persistencePort, never()).hasOverlappingReservations(anyLong(), any(), any());
        verify(persistencePort).save(validReservation);
    }

    @Test
    @DisplayName("Should update reservation when parking spot ID is null (no overlap check)")
    void shouldUpdateWhenSpotIdIsNull() {
        validReservation.setId(100L);
        validReservation.getParkingSpot().setId(null);
        when(persistencePort.save(any(Reservation.class))).thenReturn(validReservation);

        Reservation updated = reservationService.update(validReservation);

        assertNotNull(updated);
        verify(persistencePort, never()).hasOverlappingReservationsExcluding(anyLong(), any(), any(), anyLong());
        verify(persistencePort).save(validReservation);
    }

    @Test
    @DisplayName("Should create reservation and send notification")
    void shouldCreateReservationAndSendNotification() {
        validReservation.setId(500L);
        User user = new User();
        user.setName("John Doe");
        validReservation.setUser(user);

        Parking parking = new Parking();
        parking.setName("Main Parking");
        Company company = new Company(10L);
        parking.setCompany(company);
        
        validSpot.setParking(parking);

        when(persistencePort.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(false);
        when(persistencePort.save(any(Reservation.class))).thenReturn(validReservation);
        when(spotPersistencePort.findById(validSpot.getId())).thenReturn(Optional.of(validSpot));

        Reservation created = reservationService.create(validReservation);

        assertNotNull(created);
        verify(notificationService, times(1)).create(any(Notification.class));
    }

    @Test
    @DisplayName("updatePaymentStatus - Should set to COMPLETED and free the spot when paid")
    void shouldUpdatePaymentStatusToCompleted() {
        validReservation.setId(1L);
        validReservation.setPaid(false);
        validReservation.setState("ACTIVE");
        validSpot.setState(false); 

        when(persistencePort.findById(1L)).thenReturn(Optional.of(validReservation));
        when(persistencePort.save(any(Reservation.class))).thenAnswer(i -> i.getArgument(0));

        Reservation result = reservationService.updatePaymentStatus(1L, true);

        assertTrue(result.isPaid());
        assertEquals("COMPLETED", result.getState());
        assertTrue(validSpot.isState()); 
        verify(spotPersistencePort).save(validSpot);
        verify(persistencePort).save(validReservation);
    }

    @Test
    @DisplayName("Should create reservation and set basePrice if null")
    void shouldSetBasePriceIfNull() {
        validReservation.setPrice(10.0);
        validReservation.setBasePrice(null);
        when(persistencePort.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(false);
        when(persistencePort.save(any(Reservation.class))).thenAnswer(i -> i.getArgument(0));

        Reservation result = reservationService.create(validReservation);

        assertEquals(10.0, result.getBasePrice());
    }

    @Test
    @DisplayName("Should not crash and send no notification when objects are null in create")
    void shouldNotCrashWhenObjectsAreNullInNotificationLogic() {
        validReservation.setParkingSpot(new ParkingSpot(1L)); 
        when(persistencePort.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(false);
        when(persistencePort.save(any(Reservation.class))).thenReturn(validReservation);
        when(spotPersistencePort.findById(anyLong())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> reservationService.create(validReservation));
        verify(notificationService, never()).create(any());
    }

    @Test
    @DisplayName("updatePaymentStatus - Should not crash when spot is null")
    void shouldNotCrashWhenSpotIsNullInPaymentUpdate() {
        validReservation.setId(1L);
        validReservation.setPaid(false);
        validReservation.setParkingSpot(null);

        when(persistencePort.findById(1L)).thenReturn(Optional.of(validReservation));
        when(persistencePort.save(any(Reservation.class))).thenAnswer(i -> i.getArgument(0));

        Reservation result = reservationService.updatePaymentStatus(1L, true);

        assertTrue(result.isPaid());
        verify(spotPersistencePort, never()).save(any());
    }

    @Test
    @DisplayName("updatePaymentStatus - Should update sanctions to paid")
    void shouldUpdateSanctionsToPaid() {
        validReservation.setId(1L);
        validReservation.setPaid(false);
        Sanction s1 = new Sanction();
        s1.setPaid(false);
        Set<Sanction> sanctions = new HashSet<>();
        sanctions.add(s1);
        validReservation.setSanctions(sanctions);

        when(persistencePort.findById(1L)).thenReturn(Optional.of(validReservation));
        when(persistencePort.save(any(Reservation.class))).thenAnswer(i -> i.getArgument(0));

        Reservation result = reservationService.updatePaymentStatus(1L, true);

        assertTrue(s1.isPaid());
    }

    @Test
    @DisplayName("Should not set basePrice if already present in create")
    void shouldNotSetBasePriceIfPresent() {
        validReservation.setPrice(10.0);
        validReservation.setBasePrice(5.0); 
        when(persistencePort.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(false);
        when(persistencePort.save(any(Reservation.class))).thenAnswer(i -> i.getArgument(0));

        Reservation result = reservationService.create(validReservation);

        assertEquals(5.0, result.getBasePrice());
    }

    @Test
    @DisplayName("Should not send notification if spot has no parking in create")
    void shouldNotSendNotificationIfParkingIsNull() {
        validReservation.setParkingSpot(validSpot);
        validSpot.setParking(null);

        when(persistencePort.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(false);
        when(persistencePort.save(any(Reservation.class))).thenReturn(validReservation);
        when(spotPersistencePort.findById(validSpot.getId())).thenReturn(Optional.of(validSpot));

        reservationService.create(validReservation);

        verify(notificationService, never()).create(any());
    }

    @Test
    @DisplayName("Should not send notification if parking has no company in create")
    void shouldNotSendNotificationIfCompanyIsNull() {
        validReservation.setParkingSpot(validSpot);
        Parking parking = new Parking();
        parking.setCompany(null); 
        validSpot.setParking(parking);

        when(persistencePort.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(false);
        when(persistencePort.save(any(Reservation.class))).thenReturn(validReservation);
        when(spotPersistencePort.findById(validSpot.getId())).thenReturn(Optional.of(validSpot));

        reservationService.create(validReservation);

        verify(notificationService, never()).create(any());
    }

    @Test
    @DisplayName("updatePaymentStatus - Should not update state or free spot if paid is false")
    void shouldNotUpdateIfPaidIsFalse() {
        validReservation.setId(1L);
        validReservation.setPaid(false);
        validReservation.setState("ACTIVE");
        validSpot.setState(false);

        when(persistencePort.findById(1L)).thenReturn(Optional.of(validReservation));
        when(persistencePort.save(any(Reservation.class))).thenAnswer(i -> i.getArgument(0));

        Reservation result = reservationService.updatePaymentStatus(1L, false);

        assertFalse(result.isPaid());
        assertEquals("ACTIVE", result.getState());
        assertFalse(validSpot.isState());
        verify(spotPersistencePort, never()).save(any());
    }

    @Test
    @DisplayName("Should not throw when notification logic fails in create (try-catch)")
    void shouldNotThrowWhenNotificationFails() {
        when(persistencePort.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(false);
        when(persistencePort.save(any(Reservation.class))).thenReturn(validReservation);
        
        when(spotPersistencePort.findById(anyLong())).thenThrow(new RuntimeException("Database error"));

        assertDoesNotThrow(() -> {
            Reservation result = reservationService.create(validReservation);
            assertNotNull(result);
        });
    }

    @Test
    @DisplayName("updatePaymentStatus - Should not crash when sanctions are null")
    void shouldNotCrashWhenSanctionsAreNull() {
        validReservation.setId(1L);
        validReservation.setSanctions(null);
        when(persistencePort.findById(1L)).thenReturn(Optional.of(validReservation));
        when(persistencePort.save(any(Reservation.class))).thenAnswer(i -> i.getArgument(0));

        assertDoesNotThrow(() -> reservationService.updatePaymentStatus(1L, true));
    }

    @Test
    @DisplayName("Should not send notification if companyId is null in create")
    void shouldNotSendNotificationIfCompanyIdIsNull() {
        validReservation.setParkingSpot(validSpot);
        Parking parking = new Parking();
        Company company = new Company(null); 
        parking.setCompany(company);
        validSpot.setParking(parking);

        when(persistencePort.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(false);
        when(persistencePort.save(any(Reservation.class))).thenReturn(validReservation);
        when(spotPersistencePort.findById(validSpot.getId())).thenReturn(Optional.of(validSpot));

        reservationService.create(validReservation);

        verify(notificationService, never()).create(any());
    }

    @Test
    @DisplayName("Should use 'a user' in notification when user is null in create")
    void shouldSendNotificationWithGenericUserWhenUserIsNull() {
        validReservation.setUser(null); 
        validReservation.setParkingSpot(validSpot);
        Parking parking = new Parking();
        parking.setName("Main Parking");
        Company company = new Company(10L);
        parking.setCompany(company);
        validSpot.setParking(parking);

        when(persistencePort.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(false);
        when(persistencePort.save(any(Reservation.class))).thenReturn(validReservation);
        when(spotPersistencePort.findById(validSpot.getId())).thenReturn(Optional.of(validSpot));

        reservationService.create(validReservation);

        verify(notificationService).create(argThat(n -> n.getMessage().contains("received from a user")));
    }

    @Test
    @DisplayName("updatePaymentStatus - Should throw when reservation not found")
    void shouldThrowWhenReservationNotFoundForPayment() {
        when(persistencePort.findById(99L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> reservationService.updatePaymentStatus(99L, true));
    }
}

