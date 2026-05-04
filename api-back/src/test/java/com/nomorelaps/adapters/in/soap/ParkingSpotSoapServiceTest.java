package com.nomorelaps.adapters.in.soap;

import com.nomorelaps.adapters.in.api.ParkingSpotResponse;
import com.nomorelaps.adapters.mapper.ParkingSpotMapper;
import com.nomorelaps.business.interfaces.IParkingSpotService;
import com.nomorelaps.domain.models.ParkingSpot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingSpotSoapServiceTest {

    private IParkingSpotService parkingSpotService;
    private ParkingSpotMapper parkingSpotMapper;
    private ParkingSpotSoapService parkingSpotSoapService;

    @BeforeEach
    void setUp() {
        parkingSpotService = mock(IParkingSpotService.class);
        parkingSpotMapper = mock(ParkingSpotMapper.class);
        parkingSpotSoapService = new ParkingSpotSoapService(parkingSpotService, parkingSpotMapper);
    }

    @Test
    @DisplayName("findByParkingId - Should return list")
    void shouldReturnFindByParkingId() {
        ParkingSpot spot = new ParkingSpot(1L);
        ParkingSpotResponse response = new ParkingSpotResponse();
        response.setId(1L);

        when(parkingSpotService.findByParkingId(2L)).thenReturn(Collections.singletonList(spot));
        when(parkingSpotMapper.toResponse(spot)).thenReturn(response);

        List<ParkingSpotResponse> result = parkingSpotSoapService.findByParkingId(2L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(parkingSpotService, times(1)).findByParkingId(2L);
    }

    @Test
    @DisplayName("findAvailableSpots - Should return list")
    void shouldReturnFindAvailableSpots() {
        ParkingSpot spot = new ParkingSpot(1L);
        ParkingSpotResponse response = new ParkingSpotResponse();
        response.setId(1L);

        when(parkingSpotService.findAvailableSpots(2L)).thenReturn(Collections.singletonList(spot));
        when(parkingSpotMapper.toResponse(spot)).thenReturn(response);

        List<ParkingSpotResponse> result = parkingSpotSoapService.findAvailableSpots(2L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(parkingSpotService, times(1)).findAvailableSpots(2L);
    }
}
