package com.nomorelaps.adapters.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
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
import com.nomorelaps.adapters.in.api.ParkingRequest;
import com.nomorelaps.business.interfaces.IParkingService;
import com.nomorelaps.domain.models.Parking;
import com.nomorelaps.infrastructure.security.SecurityService;

/**
 * Integration tests for ParkingController.
 * Verifies parking management including CRUD, nearby search, and company-specific filtering.
 * 
 * @author nexphernandez
 * @version 1.1.0
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ParkingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IParkingService parkingService;

    @MockitoBean(name = "securityService")
    private SecurityService securityService;

    private ParkingRequest validRequest;
    private Parking sampleParking;

    @BeforeEach
    void setUp() {
        validRequest = new ParkingRequest();
        validRequest.setName("Central Station Parking");
        validRequest.setAddress("Main Ave 123");
        validRequest.setLatitude(40.416775);
        validRequest.setLongitude(-3.703790);

        sampleParking = new Parking(1L);
        sampleParking.setName("Central Station Parking");
        sampleParking.setAddress("Main Ave 123");
        sampleParking.setLatitude(40.416775);
        sampleParking.setLongitude(-3.703790);

        when(securityService.isCompanyOwner(any())).thenReturn(true);
    }

    @Test
    @DisplayName("GET /api/parkings - Success: Should return list of all parkings")
    void shouldReturnAllParkingsSuccessfully() throws Exception {
        when(parkingService.findAll()).thenReturn(List.of(sampleParking));

        mockMvc.perform(get("/api/parkings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Central Station Parking"));
    }

    @Test
    @DisplayName("GET /api/parkings/{id} - Success: Should return parking details")
    void shouldReturnParkingByIdSuccessfully() throws Exception {
        when(parkingService.findById(1L)).thenReturn(Optional.of(sampleParking));

        mockMvc.perform(get("/api/parkings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Central Station Parking"));
    }

    @Test
    @DisplayName("GET /api/parkings/{id} - Failure: Should return 404 when missing")
    void shouldReturnNotFoundWhenParkingIsMissing() throws Exception {
        when(parkingService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/parkings/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/parkings - Success: Should create parking")
    @WithMockUser(roles = "COMPANY")
    void shouldCreateParkingSuccessfully() throws Exception {
        when(parkingService.create(any(Parking.class))).thenReturn(sampleParking);

        mockMvc.perform(post("/api/parkings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("PUT /api/parkings/{id} - Success: Should update parking")
    @WithMockUser(roles = "COMPANY")
    void shouldUpdateParkingSuccessfully() throws Exception {
        sampleParking.setName("Updated Name");
        when(parkingService.update(any(Parking.class))).thenReturn(sampleParking);

        mockMvc.perform(put("/api/parkings/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    @DisplayName("DELETE /api/parkings/{id} - Success: Should return 204")
    @WithMockUser(roles = "COMPANY")
    void shouldDeleteParkingSuccessfully() throws Exception {
        doNothing().when(parkingService).deleteById(1L);

        mockMvc.perform(delete("/api/parkings/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/parkings/search - Success: Should return matching results")
    void shouldSearchParkingsSuccessfully() throws Exception {
        when(parkingService.searchByNameOrAddress("Central")).thenReturn(List.of(sampleParking));

        mockMvc.perform(get("/api/parkings/search").param("query", "Central"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Central Station Parking"));
    }

    @Test
    @DisplayName("GET /api/parkings/nearby - Success: Should return nearby results")
    void shouldFindNearbyParkingsSuccessfully() throws Exception {
        when(parkingService.findNearby(anyDouble(), anyDouble(), anyDouble())).thenReturn(List.of(sampleParking));

        mockMvc.perform(get("/api/parkings/nearby")
                .param("lat", "40.41")
                .param("lng", "-3.70")
                .param("radius", "5.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("GET /api/parkings/company/{id} - Success: Should return company parkings")
    @WithMockUser(roles = "COMPANY")
    void shouldFindParkingsByCompanyIdSuccessfully() throws Exception {
        when(parkingService.findAllByCompanyId(10L)).thenReturn(List.of(sampleParking));

        mockMvc.perform(get("/api/parkings/company/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L));
    }
}
