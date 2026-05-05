package com.nomorelaps.adapters.in.soap;

import com.nomorelaps.adapters.in.api.ParkingResponse;
import com.nomorelaps.adapters.mapper.ParkingMapper;
import com.nomorelaps.business.interfaces.IParkingService;
import com.nomorelaps.domain.models.Parking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ParkingSoapServiceTest {

    private IParkingService parkingService;
    private ParkingMapper parkingMapper;
    private ParkingSoapService parkingSoapService;

    @BeforeEach
    void setUp() {
        parkingService = mock(IParkingService.class);
        parkingMapper = mock(ParkingMapper.class);
        parkingSoapService = new ParkingSoapService(parkingService, parkingMapper);
    }

    @Test
    @DisplayName("findAll - Should return list of parking responses")
    void shouldReturnListOfParkingResponses() {
        Parking parking = new Parking(1L);
        ParkingResponse response = new ParkingResponse();
        response.setId(1L);

        when(parkingService.findAll()).thenReturn(Collections.singletonList(parking));
        when(parkingMapper.toResponse(parking)).thenReturn(response);

        List<ParkingResponse> result = parkingSoapService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());

        verify(parkingService, times(1)).findAll();
        verify(parkingMapper, times(1)).toResponse(parking);
    }

    @Test
    @DisplayName("findById - Should return parking response when found")
    void shouldReturnParkingResponseWhenFound() {
        Parking parking = new Parking(1L);
        ParkingResponse response = new ParkingResponse();
        response.setId(1L);

        when(parkingService.findById(1L)).thenReturn(Optional.of(parking));
        when(parkingMapper.toResponse(parking)).thenReturn(response);

        ParkingResponse result = parkingSoapService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(parkingService, times(1)).findById(1L);
        verify(parkingMapper, times(1)).toResponse(parking);
    }

    @Test
    @DisplayName("findById - Should return null when not found")
    void shouldReturnNullWhenNotFound() {
        when(parkingService.findById(1L)).thenReturn(Optional.empty());

        ParkingResponse result = parkingSoapService.findById(1L);

        assertNull(result);

        verify(parkingService, times(1)).findById(1L);
        verify(parkingMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("findByCompanyId - Should return list")
    void shouldReturnFindByCompanyId() {
        Parking parking = new Parking(1L);
        ParkingResponse response = new ParkingResponse();
        response.setId(1L);

        when(parkingService.findAllByCompanyId(2L)).thenReturn(Collections.singletonList(parking));
        when(parkingMapper.toResponse(parking)).thenReturn(response);

        List<ParkingResponse> result = parkingSoapService.findByCompanyId(2L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(parkingService, times(1)).findAllByCompanyId(2L);
    }

    @Test
    @DisplayName("search - Should return list")
    void shouldReturnSearch() {
        Parking parking = new Parking(1L);
        ParkingResponse response = new ParkingResponse();
        response.setId(1L);

        when(parkingService.searchByNameOrAddress("test")).thenReturn(Collections.singletonList(parking));
        when(parkingMapper.toResponse(parking)).thenReturn(response);

        List<ParkingResponse> result = parkingSoapService.search("test");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(parkingService, times(1)).searchByNameOrAddress("test");
    }

    @Test
    @DisplayName("findNearby - Should return list")
    void shouldReturnFindNearby() {
        Parking parking = new Parking(1L);
        ParkingResponse response = new ParkingResponse();
        response.setId(1L);

        when(parkingService.findNearby(1.0, 2.0, 3.0)).thenReturn(Collections.singletonList(parking));
        when(parkingMapper.toResponse(parking)).thenReturn(response);

        List<ParkingResponse> result = parkingSoapService.findNearby(1.0, 2.0, 3.0);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(parkingService, times(1)).findNearby(1.0, 2.0, 3.0);
    }
}
