package com.nomorelaps.adapters.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nomorelaps.adapters.in.api.ReservationRequest;
import com.nomorelaps.adapters.in.api.ReservationResponse;
import com.nomorelaps.business.interfaces.IReservationService;
import com.nomorelaps.domain.models.Reservation;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IReservationService reservationService;

    @Autowired
    private com.nomorelaps.adapters.mapper.ReservationMapper reservationMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private com.nomorelaps.infrastructure.security.SecurityService securityService;

    @Test
    @DisplayName("GET /api/reservations/{id} - Found")
    @WithMockUser
    void shouldReturnReservationById() throws Exception {
        Reservation reservation = new Reservation(1L);
        reservation.setState("ACTIVE");
        when(reservationService.findById(1L)).thenReturn(Optional.of(reservation));

        mockMvc.perform(get("/api/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.state").value("ACTIVE"));
    }

    @Test
    @DisplayName("POST /api/reservations - Created")
    @WithMockUser
    void shouldCreateReservation() throws Exception {
        ReservationRequest request = new ReservationRequest();
        request.setStartTime(LocalDateTime.now().plusHours(1));
        request.setEndTime(LocalDateTime.now().plusHours(2));
        request.setPrice(10.0);
        request.setState("ACTIVE");
        request.setUserId(1L);
        request.setParkingSpotId(1L);

        Reservation saved = new Reservation(1L);
        when(reservationService.create(any(Reservation.class))).thenReturn(saved);

        mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GET /api/reservations/user/{id} - List")
    @WithMockUser
    void shouldReturnUserReservations() throws Exception {
        when(reservationService.findByUserId(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/reservations/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("GET /api/reservations/{id} - Not Found")
    @WithMockUser
    void shouldReturn404WhenReservationNotFound() throws Exception {
        when(reservationService.findById(99L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/reservations/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/reservations/spot/{id} - List")
    @WithMockUser
    void shouldReturnSpotReservations() throws Exception {
        when(reservationService.findByParkingSpotId(1L)).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/reservations/spot/1/occupied"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/reservations/parking/{id} - List")
    @WithMockUser
    void shouldReturnParkingReservations() throws Exception {
        when(reservationService.findByParkingId(1L)).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/reservations/parking/1/occupied"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/reservations/state/{state} - List")
    @WithMockUser
    void shouldReturnReservationsByState() throws Exception {
        when(reservationService.findByState("ACTIVE")).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/reservations/state/ACTIVE"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /api/reservations/{id} - Updated")
    @WithMockUser
    void shouldUpdateReservation() throws Exception {
        Reservation saved = new Reservation(1L);
        when(reservationService.update(any(Reservation.class))).thenReturn(saved);

        ReservationRequest request = new ReservationRequest();
        request.setStartTime(LocalDateTime.now().plusHours(1));
        request.setEndTime(LocalDateTime.now().plusHours(2));
        request.setPrice(10.0);
        request.setState("ACTIVE");
        request.setUserId(1L);
        request.setParkingSpotId(1L);

        mockMvc.perform(put("/api/reservations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/reservations/{id} - Deleted")
    @WithMockUser
    void shouldDeleteReservation() throws Exception {
        mockMvc.perform(delete("/api/reservations/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/reservations/spot/{id}/occupied - Filter check")
    @WithMockUser
    void shouldFilterNonActiveReservations() throws Exception {
        Reservation active = new Reservation(1L);
        active.setState("ACTIVE");
        Reservation cancelled = new Reservation(2L);
        cancelled.setState("CANCELLED");
        
        when(reservationService.findByParkingSpotId(1L)).thenReturn(Arrays.asList(active, cancelled));
        
        mockMvc.perform(get("/api/reservations/spot/1/occupied"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("GET /api/reservations/parking/{id}/occupied - Filter check")
    @WithMockUser
    void shouldFilterNonActiveReservationsByParking() throws Exception {
        Reservation active = new Reservation(10L);
        active.setState("ACTIVE");
        Reservation completed = new Reservation(11L);
        completed.setState("COMPLETED");
        
        when(reservationService.findByParkingId(1L)).thenReturn(Arrays.asList(active, completed));
        
        mockMvc.perform(get("/api/reservations/parking/1/occupied"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(10));
    }

    @Test
    @DisplayName("GET /api/reservations/company/{companyId} - Should return list")
    @WithMockUser
    void shouldFindByCompanyId() throws Exception {
        Reservation domain = new Reservation(1L);
        domain.setState("ACTIVE"); // Ensure state is set for json path

        when(securityService.isCompanyOwner(100L)).thenReturn(true);
        when(reservationService.findByCompanyId(100L)).thenReturn(List.of(domain));

        mockMvc.perform(get("/api/reservations/company/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @DisplayName("PATCH /api/reservations/{id}/payment-status - Success")
    @WithMockUser
    void shouldUpdatePaymentStatus() throws Exception {
        Reservation updated = new Reservation(1L);
        updated.setPaid(true);
        when(reservationService.updatePaymentStatus(eq(1L), anyBoolean())).thenReturn(updated);

        mockMvc.perform(patch("/api/reservations/1/payment-status")
                .param("paid", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paid").value(true));
    }
}

