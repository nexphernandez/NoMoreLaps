package com.nomorelaps.adapters.in.soap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import com.nomorelaps.adapters.in.api.ParkingSpotResponse;
import com.nomorelaps.adapters.mapper.ParkingSpotMapper;
import com.nomorelaps.business.interfaces.IParkingSpotService;
import com.nomorelaps.domain.models.ParkingSpot;

/**
 * Unit tests for ParkingSpotSoapService.
 * Validates the retrieval of parking spot information via SOAP adapter.
 * 
 * @author nexphernandez
 * @version 1.1.0
 */
@SpringBootTest
class ParkingSpotSoapServiceTest {

    @MockitoBean
    private IParkingSpotService parkingSpotService;

    @MockitoSpyBean
    private ParkingSpotMapper parkingSpotMapper;

    @Autowired
    private ParkingSpotSoapService parkingSpotSoapService;

    private ParkingSpot sampleSpot;

    @BeforeEach
    void setUp() {
        sampleSpot = new ParkingSpot(1L);
        sampleSpot.setNumber(101);
        sampleSpot.setState(true);
    }

    @Test
    @DisplayName("findByParkingId - Success: Should return list of all spots in a parking")
    void shouldReturnSpotsByParkingIdSuccessfully() {
        when(parkingSpotService.findByParkingId(2L)).thenReturn(List.of(sampleSpot));

        List<ParkingSpotResponse> result = parkingSpotSoapService.findByParkingId(2L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(101, result.get(0).getNumber());
        verify(parkingSpotService).findByParkingId(2L);
    }

    @Test
    @DisplayName("findAvailableSpots - Success: Should return only available spots")
    void shouldReturnAvailableSpotsSuccessfully() {
        when(parkingSpotService.findAvailableSpots(2L)).thenReturn(List.of(sampleSpot));

        List<ParkingSpotResponse> result = parkingSpotSoapService.findAvailableSpots(2L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(true, result.get(0).isState());
        verify(parkingSpotService).findAvailableSpots(2L);
    }
}
