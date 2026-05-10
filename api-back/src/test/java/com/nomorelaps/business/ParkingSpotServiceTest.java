package com.nomorelaps.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

/**
 * Unit tests for ParkingSpotService.
 * Verifies business logic for parking spots, including availability filters
 * and existence checks during updates.
 */
@ExtendWith(MockitoExtension.class)
class ParkingSpotServiceTest {

    @Mock
    private IParkingSpotPersistenceAdapter persistencePort;

    @InjectMocks
    private ParkingSpotService parkingSpotService;

    private ParkingSpot testSpot;

    @BeforeEach
    void setUp() {
        testSpot = new ParkingSpot(1L);
        testSpot.setNumber(101);
        testSpot.setState(true);
    }

    @Test
    @DisplayName("create - Should set state to true by default and save")
    void shouldCreateSpotWithDefaultState() {
        testSpot.setState(false);
        when(persistencePort.save(any(ParkingSpot.class))).thenAnswer(i -> i.getArguments()[0]);

        ParkingSpot result = parkingSpotService.create(testSpot);

        assertTrue(result.isState());
        verify(persistencePort).save(testSpot);
    }

    @Test
    @DisplayName("findById - Should return spot if exists")
    void shouldReturnSpotById() {
        when(persistencePort.findById(1L)).thenReturn(Optional.of(testSpot));
        assertTrue(parkingSpotService.findById(1L).isPresent());
    }

    @Test
    @DisplayName("findByParkingId - Should return all spots for parking")
    void shouldReturnSpotsByParkingId() {
        when(persistencePort.findByParkingId(5L)).thenReturn(List.of(testSpot));
        assertEquals(1, parkingSpotService.findByParkingId(5L).size());
    }

    @Test
    @DisplayName("findAvailableSpots - Should return only available spots")
    void shouldReturnAvailableSpots() {
        when(persistencePort.findByParkingIdAndStateTrue(5L)).thenReturn(List.of(testSpot));
        List<ParkingSpot> result = parkingSpotService.findAvailableSpots(5L);
        assertEquals(1, result.size());
        assertTrue(result.get(0).isState());
    }

    @Test
    @DisplayName("update - Should save spot if it exists")
    void shouldUpdateExistingSpot() {
        when(persistencePort.findById(1L)).thenReturn(Optional.of(testSpot));
        when(persistencePort.save(testSpot)).thenReturn(testSpot);

        ParkingSpot result = parkingSpotService.update(testSpot);

        assertNotNull(result);
        verify(persistencePort).save(testSpot);
    }

    @Test
    @DisplayName("update - Should throw exception if spot ID is null")
    void shouldThrowIfIdIsNullOnUpdate() {
        testSpot.setId(null);
        assertThrows(RuntimeException.class, () -> parkingSpotService.update(testSpot));
    }

    @Test
    @DisplayName("update - Should throw exception if spot not found")
    void shouldThrowIfNotFoundOnUpdate() {
        when(persistencePort.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> parkingSpotService.update(testSpot));
    }

    @Test
    @DisplayName("deleteById - Should call persistence port")
    void shouldDeleteById() {
        parkingSpotService.deleteById(1L);
        verify(persistencePort).deleteById(1L);
    }
}
