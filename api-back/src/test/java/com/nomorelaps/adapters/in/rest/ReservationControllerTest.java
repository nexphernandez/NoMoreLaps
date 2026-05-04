package com.nomorelaps.adapters.in.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
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
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/reservations/{id} - Found")
    @WithMockUser
    void shouldReturnReservationById() throws Exception {
        // Arrange
        Reservation reservation = new Reservation(1L);
        reservation.setState("ACTIVE");
        when(reservationService.findById(1L)).thenReturn(Optional.of(reservation));

        // Act & Assert
        mockMvc.perform(get("/api/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.state").value("ACTIVE"));
    }

    @Test
    @DisplayName("POST /api/reservations - Created")
    @WithMockUser
    void shouldCreateReservation() throws Exception {
        // Arrange
        ReservationRequest request = new ReservationRequest();
        request.setStartTime(LocalDateTime.now().plusHours(1));
        request.setEndTime(LocalDateTime.now().plusHours(2));
        request.setPrice(10.0);
        request.setState("ACTIVE");
        request.setUserId(1L);
        request.setParkingSpotId(1L);

        Reservation saved = new Reservation(1L);
        when(reservationService.create(any(Reservation.class))).thenReturn(saved);

        // Act & Assert
        mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GET /api/reservations/user/{id} - List")
    @WithMockUser
    void shouldReturnUserReservations() throws Exception {
        // Arrange
        when(reservationService.findByUserId(1L)).thenReturn(Collections.emptyList());

        // Act & Assert
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
    @DisplayName("GET /api/reservations/{id} - Forbidden if no user")
    void shouldReturnForbiddenWhenNoUser() throws Exception {
        mockMvc.perform(get("/api/reservations/1"))
                .andExpect(status().isForbidden());
    }
}
