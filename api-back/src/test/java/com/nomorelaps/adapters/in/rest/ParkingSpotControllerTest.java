package com.nomorelaps.adapters.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nomorelaps.adapters.in.api.ParkingSpotRequest;
import com.nomorelaps.business.interfaces.IParkingSpotService;
import com.nomorelaps.domain.models.ParkingSpot;

/**
 * Integration tests for ParkingSpotController.
 * Validates parking spot management, availability checks, and status updates.
 * 
 * @author nexphernandez
 * @version 1.1.0
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ParkingSpotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IParkingSpotService parkingSpotService;

    private ParkingSpotRequest validRequest;
    private ParkingSpot sampleSpot;

    @BeforeEach
    void setUp() {
        validRequest = new ParkingSpotRequest();
        validRequest.setNumber(101);
        validRequest.setState(true);

        sampleSpot = new ParkingSpot(1L);
        sampleSpot.setNumber(101);
        sampleSpot.setState(true);
    }

    @Test
    @DisplayName("POST /api/parking-spots - Success: Should create spot")
    @WithMockUser(roles = "COMPANY")
    void shouldCreateParkingSpotSuccessfully() throws Exception {
        when(parkingSpotService.create(any(ParkingSpot.class))).thenReturn(sampleSpot);

        mockMvc.perform(post("/api/parking-spots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.number").value(101));
    }

    @Test
    @DisplayName("GET /api/parking-spots/{id} - Success: Should return spot data")
    void shouldReturnParkingSpotByIdSuccessfully() throws Exception {
        when(parkingSpotService.findById(1L)).thenReturn(Optional.of(sampleSpot));

        mockMvc.perform(get("/api/parking-spots/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("GET /api/parking-spots/parking/{id} - Success: Should return list of spots")
    void shouldReturnSpotsByParkingIdSuccessfully() throws Exception {
        when(parkingSpotService.findByParkingId(10L)).thenReturn(List.of(sampleSpot));

        mockMvc.perform(get("/api/parking-spots/parking/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @DisplayName("GET /api/parking-spots/parking/{id}/available - Success: Should return only available spots")
    void shouldReturnAvailableSpotsSuccessfully() throws Exception {
        when(parkingSpotService.findAvailableSpots(10L)).thenReturn(List.of(sampleSpot));

        mockMvc.perform(get("/api/parking-spots/parking/10/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].state").value(true));
    }

    @Test
    @DisplayName("PUT /api/parking-spots/{id} - Success: Should update spot")
    @WithMockUser(roles = "COMPANY")
    void shouldUpdateParkingSpotSuccessfully() throws Exception {
        sampleSpot.setState(false);
        when(parkingSpotService.update(any(ParkingSpot.class))).thenReturn(sampleSpot);

        mockMvc.perform(put("/api/parking-spots/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value(false));
    }

    @Test
    @DisplayName("DELETE /api/parking-spots/{id} - Success: Should return 204")
    @WithMockUser(roles = "COMPANY")
    void shouldDeleteParkingSpotSuccessfully() throws Exception {
        doNothing().when(parkingSpotService).deleteById(1L);

        mockMvc.perform(delete("/api/parking-spots/1"))
                .andExpect(status().isNoContent());
    }
}
