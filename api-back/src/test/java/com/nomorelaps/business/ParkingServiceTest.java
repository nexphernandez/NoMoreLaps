package com.nomorelaps.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

import com.nomorelaps.adapters.out.persistence.interfaces.IParkingPersistenceAdapter;
import com.nomorelaps.business.interfaces.IParkingSpotService;
import com.nomorelaps.domain.models.Parking;
import com.nomorelaps.domain.models.ParkingSpot;

/**
 * Unit tests for ParkingService.
 * Verifies business logic for parking management, including spot auto-generation
 * and proximity search.
 */
@ExtendWith(MockitoExtension.class)
class ParkingServiceTest {

    @Mock
    private IParkingPersistenceAdapter persistencePort;

    @Mock
    private IParkingSpotService parkingSpotService;

    @InjectMocks
    private ParkingService parkingService;

    private Parking testParking;

    @BeforeEach
    void setUp() {
        testParking = new Parking(1L);
        testParking.setName("Plaza Mayor");
        testParking.setAddress("Calle Principal 1");
        testParking.setLatitude(40.4168);
        testParking.setLongitude(-3.7038);
    }

    @Test
    @DisplayName("create - Should save parking and return result")
    void shouldCreateParkingSuccessfully() {
        when(persistencePort.save(testParking)).thenReturn(testParking);
        Parking result = parkingService.create(testParking);
        assertNotNull(result);
        verify(persistencePort).save(testParking);
    }

    @Test
    @DisplayName("create - Should auto-generate spots when totalSpots is positive")
    void shouldGenerateSpotsWhenPositive() {
        testParking.setTotalSpots(2);
        when(persistencePort.save(testParking)).thenReturn(testParking);

        parkingService.create(testParking);

        verify(parkingSpotService, times(2)).create(any(ParkingSpot.class));
    }

    @Test
    @DisplayName("create - Should not generate spots when totalSpots is null")
    void shouldNotGenerateSpotsWhenNull() {
        testParking.setTotalSpots(null);
        when(persistencePort.save(testParking)).thenReturn(testParking);

        parkingService.create(testParking);

        verify(parkingSpotService, never()).create(any());
    }

    @Test
    @DisplayName("create - Should not generate spots when totalSpots is zero or negative")
    void shouldNotGenerateSpotsWhenNonPositive() {
        testParking.setTotalSpots(0);
        when(persistencePort.save(testParking)).thenReturn(testParking);

        parkingService.create(testParking);

        verify(parkingSpotService, never()).create(any());
    }

    @Test
    @DisplayName("findById - Should return parking if found")
    void shouldReturnParkingById() {
        when(persistencePort.findById(1L)).thenReturn(Optional.of(testParking));
        assertTrue(parkingService.findById(1L).isPresent());
    }

    @Test
    @DisplayName("findAll - Should return all parkings")
    void shouldReturnAllParkings() {
        when(persistencePort.findAll()).thenReturn(List.of(testParking));
        assertEquals(1, parkingService.findAll().size());
    }

    @Test
    @DisplayName("findAllByCompanyId - Should return company parkings")
    void shouldReturnParkingsByCompany() {
        when(persistencePort.findByCompanyId(10L)).thenReturn(List.of(testParking));
        List<Parking> result = parkingService.findAllByCompanyId(10L);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("update - Should call persistence port")
    void shouldUpdateParking() {
        when(persistencePort.save(testParking)).thenReturn(testParking);
        Parking result = parkingService.update(testParking);
        assertNotNull(result);
        verify(persistencePort).save(testParking);
    }

    @Test
    @DisplayName("deleteById - Should call persistence port")
    void shouldDeleteById() {
        parkingService.deleteById(1L);
        verify(persistencePort).deleteById(1L);
    }

    @Test
    @DisplayName("searchByNameOrAddress - Should return matching results")
    void shouldSearchByNameOrAddress() {
        when(persistencePort.searchByNameOrAddress("Plaza")).thenReturn(List.of(testParking));
        List<Parking> result = parkingService.searchByNameOrAddress("Plaza");
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("findNearby - Should return results within radius")
    void shouldFindNearbyParkings() {
        when(persistencePort.findNearby(40.0, -3.0, 5.0)).thenReturn(List.of(testParking));
        List<Parking> result = parkingService.findNearby(40.0, -3.0, 5.0);
        assertEquals(1, result.size());
    }
}
