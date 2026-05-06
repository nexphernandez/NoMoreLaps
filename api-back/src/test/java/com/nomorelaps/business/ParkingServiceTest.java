package com.nomorelaps.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
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

import com.nomorelaps.adapters.out.persistence.interfaces.IParkingPersistenceAdapter;
import com.nomorelaps.domain.models.Parking;

/**
 * Unit tests for ParkingService covering all business methods.
 *
 * @author nexphernandez
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class ParkingServiceTest {

    @Mock
    private IParkingPersistenceAdapter persistencePort;

    @InjectMocks
    private ParkingService parkingService;

    private Parking testParking;

    @BeforeEach
    void setUp() {
        testParking = new Parking(1L);
        testParking.setName("Test Parking");
        testParking.setAddress("Test Street 1");
        testParking.setLatitude(40.4168);
        testParking.setLongitude(-3.7038);
    }


    @Test
    @DisplayName("create - Should delegate to persistence and return saved parking")
    void shouldCreateParking() {
        when(persistencePort.save(any(Parking.class))).thenReturn(testParking);

        Parking result = parkingService.create(testParking);

        assertNotNull(result);
        assertEquals("Test Parking", result.getName());
        verify(persistencePort).save(testParking);
    }


    @Test
    @DisplayName("findById - Should return parking when found")
    void shouldFindParkingById() {
        when(persistencePort.findById(1L)).thenReturn(Optional.of(testParking));

        Optional<Parking> result = parkingService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    @DisplayName("findById - Should return empty when not found")
    void shouldReturnEmptyWhenParkingNotFound() {
        when(persistencePort.findById(99L)).thenReturn(Optional.empty());

        Optional<Parking> result = parkingService.findById(99L);

        assertFalse(result.isPresent());
    }


    @Test
    @DisplayName("findAll - Should return all parkings")
    void shouldFindAllParkings() {
        Parking p2 = new Parking(2L);
        p2.setName("Parking B");
        when(persistencePort.findAll()).thenReturn(Arrays.asList(testParking, p2));

        List<Parking> result = parkingService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("findAll - Should return empty list when no parkings")
    void shouldReturnEmptyListWhenNoParkings() {
        when(persistencePort.findAll()).thenReturn(Collections.emptyList());

        List<Parking> result = parkingService.findAll();

        assertTrue(result.isEmpty());
    }


    @Test
    @DisplayName("findAllByCompanyId - Should return parkings for a company")
    void shouldFindParkingsByCompany() {
        when(persistencePort.findByCompanyId(5L)).thenReturn(Arrays.asList(testParking));

        List<Parking> result = parkingService.findAllByCompanyId(5L);

        assertEquals(1, result.size());
        verify(persistencePort).findByCompanyId(5L);
    }


    @Test
    @DisplayName("update - Should delegate to persistence save")
    void shouldUpdateParking() {
        testParking.setName("Updated Parking");
        when(persistencePort.save(testParking)).thenReturn(testParking);

        Parking result = parkingService.update(testParking);

        assertEquals("Updated Parking", result.getName());
        verify(persistencePort).save(testParking);
    }


    @Test
    @DisplayName("deleteById - Should call persistence deleteById")
    void shouldDeleteParkingById() {
        doNothing().when(persistencePort).deleteById(1L);

        parkingService.deleteById(1L);

        verify(persistencePort).deleteById(1L);
    }


    @Test
    @DisplayName("searchByNameOrAddress - Should return matching parkings")
    void shouldSearchParkingsByNameOrAddress() {
        when(persistencePort.searchByNameOrAddress("Test")).thenReturn(Arrays.asList(testParking));

        List<Parking> result = parkingService.searchByNameOrAddress("Test");

        assertEquals(1, result.size());
        assertEquals("Test Parking", result.get(0).getName());
    }

    @Test
    @DisplayName("searchByNameOrAddress - Should return empty when no match")
    void shouldReturnEmptyWhenNoSearchMatch() {
        when(persistencePort.searchByNameOrAddress("NOPE")).thenReturn(Collections.emptyList());

        List<Parking> result = parkingService.searchByNameOrAddress("NOPE");

        assertTrue(result.isEmpty());
    }


    @Test
    @DisplayName("findNearby - Should return nearby parkings within radius")
    void shouldFindNearbyParkings() {
        when(persistencePort.findNearby(40.4168, -3.7038, 5.0)).thenReturn(Arrays.asList(testParking));

        List<Parking> result = parkingService.findNearby(40.4168, -3.7038, 5.0);

        assertEquals(1, result.size());
        verify(persistencePort).findNearby(40.4168, -3.7038, 5.0);
    }

    @Test
    @DisplayName("findNearby - Should return empty when none in radius")
    void shouldReturnEmptyWhenNoneNearby() {
        when(persistencePort.findNearby(0.0, 0.0, 1.0)).thenReturn(Collections.emptyList());

        List<Parking> result = parkingService.findNearby(0.0, 0.0, 1.0);

        assertTrue(result.isEmpty());
    }
}
