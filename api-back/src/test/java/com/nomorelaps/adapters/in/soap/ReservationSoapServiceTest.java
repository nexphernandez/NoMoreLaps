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

import com.nomorelaps.adapters.in.api.ReservationResponse;
import com.nomorelaps.adapters.mapper.ReservationMapper;
import com.nomorelaps.business.interfaces.IReservationService;
import com.nomorelaps.domain.models.Reservation;

/**
 * Unit tests for ReservationSoapService.
 * Validates the retrieval of reservation information via SOAP adapter.
 * 
 * @author nexphernandez
 * @version 1.1.0
 */
@SpringBootTest
class ReservationSoapServiceTest {

    @MockitoBean
    private IReservationService reservationService;

    @MockitoSpyBean
    private ReservationMapper reservationMapper;

    @Autowired
    private ReservationSoapService reservationSoapService;

    private Reservation sampleReservation;

    @BeforeEach
    void setUp() {
        sampleReservation = new Reservation(1L);
        sampleReservation.setState("ACTIVE");
        sampleReservation.setPrice(20.0);
    }

    @Test
    @DisplayName("findByUserId - Success: Should return list of user reservations")
    void shouldReturnReservationsByUserIdSuccessfully() {
        when(reservationService.findByUserId(10L)).thenReturn(List.of(sampleReservation));

        List<ReservationResponse> result = reservationSoapService.findByUserId(10L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(reservationService).findByUserId(10L);
    }

    @Test
    @DisplayName("findByParkingSpotId - Success: Should return list of spot reservations")
    void shouldReturnReservationsBySpotIdSuccessfully() {
        when(reservationService.findByParkingSpotId(20L)).thenReturn(List.of(sampleReservation));

        List<ReservationResponse> result = reservationSoapService.findByParkingSpotId(20L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ACTIVE", result.get(0).getState());
        verify(reservationService).findByParkingSpotId(20L);
    }

    @Test
    @DisplayName("findByState - Success: Should return filtered reservations")
    void shouldReturnReservationsByStateSuccessfully() {
        when(reservationService.findByState("ACTIVE")).thenReturn(List.of(sampleReservation));

        List<ReservationResponse> result = reservationSoapService.findByState("ACTIVE");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(20.0, result.get(0).getPrice());
        verify(reservationService).findByState("ACTIVE");
    }
}
