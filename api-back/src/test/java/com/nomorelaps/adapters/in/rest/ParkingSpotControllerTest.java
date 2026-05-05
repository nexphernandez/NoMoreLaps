package com.nomorelaps.adapters.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import com.nomorelaps.adapters.in.api.ParkingSpotRequest;
import com.nomorelaps.business.interfaces.IParkingSpotService;
import com.nomorelaps.domain.models.ParkingSpot;

/**
 * Integration tests for ParkingSpotController.
 *
 * @author nexphernandez
 * @version 1.0.0
 */
@SpringBootTest
@AutoConfigureMockMvc
class ParkingSpotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IParkingSpotService parkingSpotService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/parking-spots - Should create spot")
    @WithMockUser(roles = "COMPANY")
    void shouldCreateParkingSpot() throws Exception {
        ParkingSpotRequest request = new ParkingSpotRequest();
        request.setNumber(101);

        ParkingSpot saved = new ParkingSpot(1L);
        when(parkingSpotService.create(any(ParkingSpot.class))).thenReturn(saved);

        mockMvc.perform(post("/api/parking-spots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("GET /api/parking-spots/{id} - Found")
    @WithMockUser
    void shouldReturnParkingSpotById() throws Exception {
        ParkingSpot spot = new ParkingSpot(1L);
        when(parkingSpotService.findById(1L)).thenReturn(Optional.of(spot));

        mockMvc.perform(get("/api/parking-spots/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("GET /api/parking-spots/parking/{id} - List")
    @WithMockUser
    void shouldReturnParkingSpots() throws Exception {
        when(parkingSpotService.findByParkingId(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/parking-spots/parking/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("GET /api/parking-spots/parking/{id}/available - List")
    @WithMockUser
    void shouldReturnAvailableSpots() throws Exception {
        when(parkingSpotService.findAvailableSpots(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/parking-spots/parking/1/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("PUT /api/parking-spots/{id} - Updated")
    @WithMockUser(roles = "COMPANY")
    void shouldUpdateParkingSpot() throws Exception {
        ParkingSpotRequest request = new ParkingSpotRequest();
        request.setNumber(102);

        ParkingSpot updated = new ParkingSpot(1L);
        when(parkingSpotService.update(any(ParkingSpot.class))).thenReturn(updated);

        mockMvc.perform(put("/api/parking-spots/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("DELETE /api/parking-spots/{id} - Deleted")
    @WithMockUser(roles = "COMPANY")
    void shouldDeleteParkingSpot() throws Exception {
        doNothing().when(parkingSpotService).deleteById(1L);

        mockMvc.perform(delete("/api/parking-spots/1"))
                .andExpect(status().isNoContent());
    }
}
