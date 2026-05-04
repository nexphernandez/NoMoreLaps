package com.nomorelaps.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.adapters.out.persistence.interfaces.IParkingSpotPersistenceAdapter;
import com.nomorelaps.domain.models.ParkingSpot;

@ExtendWith(MockitoExtension.class)
class ParkingSpotServiceTest {

    @Mock
    private IParkingSpotPersistenceAdapter persistencePort;

    @InjectMocks
    private ParkingSpotService parkingSpotService;

    private ParkingSpot validSpot;

    @BeforeEach
    void setUp() {
        validSpot = new ParkingSpot(1L);
        validSpot.setNumber(101);
        validSpot.setState(true);
    }

    @Test
    @DisplayName("Should create spot as available by default")
    void shouldCreateSpotAsAvailable() {
        // Arrange
        when(persistencePort.save(any(ParkingSpot.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ParkingSpot created = parkingSpotService.create(validSpot);

        // Assert
        assertNotNull(created);
        assertTrue(created.isState(), "New spots should be available by default");
        verify(persistencePort).save(validSpot);
    }

    @Test
    @DisplayName("Should find available spots in a parking")
    void shouldFindAvailableSpots() {
        // Arrange
        when(persistencePort.findByParkingIdAndStateTrue(1L)).thenReturn(Collections.singletonList(validSpot));

        // Act
        List<ParkingSpot> available = parkingSpotService.findAvailableSpots(1L);

        // Assert
        assertFalse(available.isEmpty());
        assertEquals(1, available.size());
        assertTrue(available.get(0).isState());
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent spot")
    void shouldThrowExceptionOnUpdateNonExistent() {
        // Arrange
        when(persistencePort.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            parkingSpotService.update(validSpot);
        });
        verify(persistencePort, never()).save(any());
    }

    @Test
    @DisplayName("Should find spot by id")
    void shouldFindById() {
        when(persistencePort.findById(1L)).thenReturn(Optional.of(validSpot));
        Optional<ParkingSpot> found = parkingSpotService.findById(1L);
        assertTrue(found.isPresent());
        assertEquals(validSpot, found.get());
    }

    @Test
    @DisplayName("Should find all spots by parking id")
    void shouldFindByParkingId() {
        when(persistencePort.findByParkingId(1L)).thenReturn(List.of(validSpot));
        List<ParkingSpot> found = parkingSpotService.findByParkingId(1L);
        assertEquals(1, found.size());
    }

    @Test
    @DisplayName("Should throw exception when updating spot with null ID")
    void shouldThrowExceptionOnUpdateNullId() {
        validSpot.setId(null);
        assertThrows(RuntimeException.class, () -> parkingSpotService.update(validSpot));
    }

    @Test
    @DisplayName("Should delete spot by id")
    void shouldDeleteById() {
        doNothing().when(persistencePort).deleteById(1L);
        parkingSpotService.deleteById(1L);
        verify(persistencePort).deleteById(1L);
    }
}
