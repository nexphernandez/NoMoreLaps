package com.nomorelaps.adapters.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nomorelaps.adapters.in.api.ReservationRequest;
import com.nomorelaps.adapters.mapper.ReservationMapper;
import com.nomorelaps.business.interfaces.IReservationService;
import com.nomorelaps.domain.models.Reservation;
import com.nomorelaps.infrastructure.security.SecurityService;

/**
 * Integration tests for ReservationController.
 * Verifies the full reservation lifecycle including creation, retrieval by user/company/parking,
 * state filtering, and payment status updates.
 * 
 * @author nexphernandez
 * @version 1.1.0
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IReservationService reservationService;

    @MockitoSpyBean
    private ReservationMapper reservationMapper;

    @MockitoBean(name = "securityService")
    private SecurityService securityService;

    private ReservationRequest validRequest;
    private Reservation sampleReservation;

    @BeforeEach
    void setUp() {
        validRequest = new ReservationRequest();
        validRequest.setStartTime(LocalDateTime.now().plusHours(1));
        validRequest.setEndTime(LocalDateTime.now().plusHours(2));
        validRequest.setPrice(15.5);
        validRequest.setUserId(10L);
        validRequest.setParkingSpotId(20L);
        validRequest.setState("ACTIVE");

        sampleReservation = new Reservation(1L);
        sampleReservation.setState("ACTIVE");
        sampleReservation.setPrice(15.5);
        sampleReservation.setPaid(false);

        when(securityService.isCompanyOwner(any())).thenReturn(true);
    }

    @Test
    @DisplayName("POST /api/reservations - Success: Should create reservation")
    @WithMockUser
    void shouldCreateReservationSuccessfully() throws Exception {
        when(reservationService.create(any(Reservation.class))).thenReturn(sampleReservation);

        mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("GET /api/reservations/{id} - Success: Should return reservation details")
    @WithMockUser
    void shouldReturnReservationByIdSuccessfully() throws Exception {
        when(reservationService.findById(1L)).thenReturn(Optional.of(sampleReservation));

        mockMvc.perform(get("/api/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.state").value("ACTIVE"));
    }

    @Test
    @DisplayName("GET /api/reservations/user/{userId} - Success: Should return user reservations")
    @WithMockUser
    void shouldReturnUserReservationsSuccessfully() throws Exception {
        when(reservationService.findByUserId(10L)).thenReturn(List.of(sampleReservation));

        mockMvc.perform(get("/api/reservations/user/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @DisplayName("GET /api/reservations/company/{companyId} - Success: Should return company reservations")
    @WithMockUser
    void shouldReturnCompanyReservationsSuccessfully() throws Exception {
        when(reservationService.findByCompanyId(100L)).thenReturn(List.of(sampleReservation));

        mockMvc.perform(get("/api/reservations/company/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @DisplayName("GET /api/reservations/spot/{spotId}/occupied - Success: Should return active spot reservations")
    @WithMockUser
    void shouldReturnOccupiedHoursForSpotSuccessfully() throws Exception {
        Reservation cancelled = new Reservation(2L);
        cancelled.setState("CANCELLED");
        when(reservationService.findByParkingSpotId(20L)).thenReturn(List.of(sampleReservation, cancelled));

        mockMvc.perform(get("/api/reservations/spot/20/occupied"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].state").value("ACTIVE"));
    }

    @Test
    @DisplayName("GET /api/reservations/state/{state} - Success: Should return filtered reservations")
    @WithMockUser
    void shouldFilterReservationsByStateSuccessfully() throws Exception {
        when(reservationService.findByState("ACTIVE")).thenReturn(List.of(sampleReservation));

        mockMvc.perform(get("/api/reservations/state/ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].state").value("ACTIVE"));
    }

    @Test
    @DisplayName("PUT /api/reservations/{id} - Success: Should update reservation")
    @WithMockUser
    void shouldUpdateReservationSuccessfully() throws Exception {
        sampleReservation.setPrice(20.0);
        when(reservationService.update(any(Reservation.class))).thenReturn(sampleReservation);

        mockMvc.perform(put("/api/reservations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(20.0));
    }

    @Test
    @DisplayName("DELETE /api/reservations/{id} - Success: Should return 204")
    @WithMockUser
    void shouldDeleteReservationSuccessfully() throws Exception {
        doNothing().when(reservationService).deleteById(1L);

        mockMvc.perform(delete("/api/reservations/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("PATCH /api/reservations/{id}/payment-status - Success: Should update paid flag")
    @WithMockUser
    void shouldUpdatePaymentStatusSuccessfully() throws Exception {
        sampleReservation.setPaid(true);
        when(reservationService.updatePaymentStatus(eq(1L), anyBoolean())).thenReturn(sampleReservation);

        mockMvc.perform(patch("/api/reservations/1/payment-status")
                .param("paid", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paid").value(true));
    }
}
