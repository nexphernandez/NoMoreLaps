package com.nomorelaps.adapters.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
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
import com.nomorelaps.adapters.in.api.ParkingRequest;
import com.nomorelaps.business.interfaces.IParkingService;
import com.nomorelaps.domain.models.Parking;

/**
 * Integration tests for ParkingController.
 *
 * @author nexphernandez
 * @version 1.0.0
 */
@SpringBootTest
@AutoConfigureMockMvc
class ParkingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IParkingService parkingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/parkings - Should return list (public endpoint)")
    void shouldReturnAllParkings() throws Exception {
        Parking p1 = new Parking(1L);
        p1.setName("Park A");
        Parking p2 = new Parking(2L);
        p2.setName("Park B");
        when(parkingService.findAll()).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/api/parkings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("GET /api/parkings/{id} - Found (public endpoint)")
    void shouldReturnParkingById() throws Exception {
        Parking parking = new Parking(1L);
        parking.setName("Test Parking");
        when(parkingService.findById(1L)).thenReturn(Optional.of(parking));

        mockMvc.perform(get("/api/parkings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("GET /api/parkings/{id} - Not Found (public endpoint)")
    void shouldReturn404WhenParkingNotFound() throws Exception {
        when(parkingService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/parkings/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/parkings - Created (requires COMPANY role)")
    @WithMockUser(roles = "COMPANY")
    void shouldCreateParking() throws Exception {
        ParkingRequest request = new ParkingRequest();
        request.setName("New Parking");
        request.setAddress("Test Street");
        request.setLatitude(40.4168);
        request.setLongitude(-3.7038);

        Parking saved = new Parking(1L);
        saved.setName("New Parking");
        when(parkingService.create(any(Parking.class))).thenReturn(saved);

        mockMvc.perform(post("/api/parkings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("DELETE /api/parkings/{id} - No Content (requires COMPANY role)")
    @WithMockUser(roles = "COMPANY")
    void shouldDeleteParking() throws Exception {
        doNothing().when(parkingService).deleteById(1L);

        mockMvc.perform(delete("/api/parkings/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("POST /api/parkings - Forbidden without COMPANY role")
    @WithMockUser(roles = "USER")
    void shouldReturnForbiddenWithoutCompanyRole() throws Exception {
        mockMvc.perform(post("/api/parkings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("GET /api/parkings - Returns 200 even without authentication (permitAll)")
    void shouldReturnOkWithoutUserForPublicEndpoint() throws Exception {
        when(parkingService.findAll()).thenReturn(java.util.Collections.emptyList());
        mockMvc.perform(get("/api/parkings"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/parkings/search - Should return results")
    @WithMockUser
    void shouldSearchParkings() throws Exception {
        Parking p = new Parking(1L);
        p.setName("Matching Parking");
        when(parkingService.searchByNameOrAddress("Matching")).thenReturn(Collections.singletonList(p));

        mockMvc.perform(get("/api/parkings/search").param("query", "Matching"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
