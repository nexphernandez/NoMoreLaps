package com.nomorelaps.adapters.in.soap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import com.nomorelaps.adapters.in.api.ParkingResponse;
import com.nomorelaps.adapters.mapper.ParkingMapper;
import com.nomorelaps.business.interfaces.IParkingService;
import com.nomorelaps.domain.models.Parking;

/**
 * Unit tests for ParkingSoapService.
 * Validates retrieval and search operations for parking facilities via SOAP adapter.
 * 
 * @author nexphernandez
 * @version 1.1.0
 */
@SpringBootTest
class ParkingSoapServiceTest {

    @MockitoBean
    private IParkingService parkingService;

    @MockitoSpyBean
    private ParkingMapper parkingMapper;

    @Autowired
    private ParkingSoapService parkingSoapService;

    private Parking sampleParking;

    @BeforeEach
    void setUp() {
        sampleParking = new Parking(1L);
        sampleParking.setName("Grand Central Parking");
        sampleParking.setAddress("42nd St, NY");
    }

    @Test
    @DisplayName("findAll - Success: Should return list of all parkings")
    void shouldReturnAllParkingsSuccessfully() {
        when(parkingService.findAll()).thenReturn(List.of(sampleParking));

        List<ParkingResponse> result = parkingSoapService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Grand Central Parking", result.get(0).getName());
        verify(parkingService).findAll();
    }

    @Test
    @DisplayName("findById - Success: Should return parking details when found")
    void shouldReturnParkingByIdSuccessfully() {
        when(parkingService.findById(1L)).thenReturn(Optional.of(sampleParking));

        ParkingResponse result = parkingSoapService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Grand Central Parking", result.getName());
        verify(parkingService).findById(1L);
    }

    @Test
    @DisplayName("findById - Failure: Should return null when parking does not exist")
    void shouldReturnNullWhenParkingNotFound() {
        when(parkingService.findById(99L)).thenReturn(Optional.empty());

        ParkingResponse result = parkingSoapService.findById(99L);

        assertNull(result);
        verify(parkingService).findById(99L);
        verify(parkingMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("findByCompanyId - Success: Should return parkings for a specific company")
    void shouldReturnParkingsByCompanyIdSuccessfully() {
        when(parkingService.findAllByCompanyId(10L)).thenReturn(List.of(sampleParking));

        List<ParkingResponse> result = parkingSoapService.findByCompanyId(10L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(parkingService).findAllByCompanyId(10L);
    }

    @Test
    @DisplayName("search - Success: Should return parkings matching the query")
    void shouldSearchParkingsSuccessfully() {
        when(parkingService.searchByNameOrAddress("Central")).thenReturn(List.of(sampleParking));

        List<ParkingResponse> result = parkingSoapService.search("Central");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Grand Central Parking", result.get(0).getName());
        verify(parkingService).searchByNameOrAddress("Central");
    }

    @Test
    @DisplayName("findNearby - Success: Should return parkings within radius")
    void shouldFindNearbyParkingsSuccessfully() {

        when(parkingService.findNearby(40.7128, -74.0060, 5.0)).thenReturn(List.of(sampleParking));

        List<ParkingResponse> result = parkingSoapService.findNearby(40.7128, -74.0060, 5.0);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(parkingService).findNearby(40.7128, -74.0060, 5.0);
    }
}
