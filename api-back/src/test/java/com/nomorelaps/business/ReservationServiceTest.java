package com.nomorelaps.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.adapters.out.persistence.interfaces.IReservationPersistenceAdapter;
import com.nomorelaps.domain.models.ParkingSpot;
import com.nomorelaps.domain.models.Reservation;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private IReservationPersistenceAdapter persistencePort;

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
        // Arrange
        when(persistencePort.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(false);
        when(persistencePort.save(any(Reservation.class))).thenReturn(validReservation);

        // Act
        Reservation created = reservationService.create(validReservation);

        // Assert
        assertNotNull(created);
        assertEquals("ACTIVE", created.getState());
        verify(persistencePort).save(validReservation);
    }

    @Test
    @DisplayName("Should throw exception when end time is before start time")
    void shouldThrowExceptionWhenEndTimeIsInvalid() {
        // Arrange
        validReservation.setEndTime(validReservation.getStartTime().minusHours(1));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            reservationService.create(validReservation);
        });
        verify(persistencePort, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when reservation overlaps")
    void shouldThrowExceptionWhenOverlaps() {
        // Arrange
        when(persistencePort.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            reservationService.create(validReservation);
        });
        verify(persistencePort, never()).save(any());
    }

    @Test
    @DisplayName("Should update reservation when valid")
    void shouldUpdateReservationWhenValid() {
        // Arrange
        validReservation.setId(100L);
        when(persistencePort.hasOverlappingReservationsExcluding(anyLong(), any(), any(), anyLong())).thenReturn(false);
        when(persistencePort.save(any(Reservation.class))).thenReturn(validReservation);

        // Act
        Reservation updated = reservationService.update(validReservation);

        // Assert
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
        when(persistencePort.findByUserId(1L)).thenReturn(java.util.List.of(validReservation));
        java.util.List<Reservation> found = reservationService.findByUserId(1L);
        assertEquals(1, found.size());
    }

    @Test
    @DisplayName("Should find reservations by parking spot id")
    void shouldFindByParkingSpotId() {
        when(persistencePort.findByParkingSpotId(1L)).thenReturn(java.util.List.of(validReservation));
        java.util.List<Reservation> found = reservationService.findByParkingSpotId(1L);
        assertEquals(1, found.size());
    }

    @Test
    @DisplayName("Should find reservations by parking id")
    void shouldFindByParkingId() {
        when(persistencePort.findByParkingId(1L)).thenReturn(java.util.List.of(validReservation));
        java.util.List<Reservation> found = reservationService.findByParkingId(1L);
        assertEquals(1, found.size());
    }

    @Test
    @DisplayName("Should find reservations by state")
    void shouldFindByState() {
        when(persistencePort.findByState("ACTIVE")).thenReturn(java.util.List.of(validReservation));
        java.util.List<Reservation> found = reservationService.findByState("ACTIVE");
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
}
