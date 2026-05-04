package com.nomorelaps.adapters.in.soap;

import com.nomorelaps.adapters.in.api.ReservationResponse;
import com.nomorelaps.adapters.mapper.ReservationMapper;
import com.nomorelaps.business.interfaces.IReservationService;
import com.nomorelaps.domain.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationSoapServiceTest {

    private IReservationService reservationService;
    private ReservationMapper reservationMapper;
    private ReservationSoapService reservationSoapService;

    @BeforeEach
    void setUp() {
        reservationService = mock(IReservationService.class);
        reservationMapper = mock(ReservationMapper.class);
        reservationSoapService = new ReservationSoapService(reservationService, reservationMapper);
    }

    @Test
    @DisplayName("findByUserId - Should return list")
    void shouldReturnFindByUserId() {
        Reservation reservation = new Reservation(1L);
        ReservationResponse response = new ReservationResponse();
        response.setId(1L);

        when(reservationService.findByUserId(2L)).thenReturn(Collections.singletonList(reservation));
        when(reservationMapper.toResponse(reservation)).thenReturn(response);

        List<ReservationResponse> result = reservationSoapService.findByUserId(2L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(reservationService, times(1)).findByUserId(2L);
    }

    @Test
    @DisplayName("findByParkingSpotId - Should return list")
    void shouldReturnFindByParkingSpotId() {
        Reservation reservation = new Reservation(1L);
        ReservationResponse response = new ReservationResponse();
        response.setId(1L);

        when(reservationService.findByParkingSpotId(2L)).thenReturn(Collections.singletonList(reservation));
        when(reservationMapper.toResponse(reservation)).thenReturn(response);

        List<ReservationResponse> result = reservationSoapService.findByParkingSpotId(2L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(reservationService, times(1)).findByParkingSpotId(2L);
    }

    @Test
    @DisplayName("findByState - Should return list")
    void shouldReturnFindByState() {
        Reservation reservation = new Reservation(1L);
        ReservationResponse response = new ReservationResponse();
        response.setId(1L);

        when(reservationService.findByState("ACTIVE")).thenReturn(Collections.singletonList(reservation));
        when(reservationMapper.toResponse(reservation)).thenReturn(response);

        List<ReservationResponse> result = reservationSoapService.findByState("ACTIVE");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(reservationService, times(1)).findByState("ACTIVE");
    }
}
