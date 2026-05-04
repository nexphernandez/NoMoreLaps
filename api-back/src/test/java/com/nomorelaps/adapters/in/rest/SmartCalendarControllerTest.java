package com.nomorelaps.adapters.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

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
import com.nomorelaps.adapters.in.api.SmartGeocodeRequest;
import com.nomorelaps.adapters.in.api.SmartGeocodeResponse;
import com.nomorelaps.adapters.in.api.SmartRecommendationRequest;
import com.nomorelaps.adapters.in.api.SmartRecommendationResponse;
import com.nomorelaps.business.SmartCalendarService;

/**
 * Integration tests for SmartCalendarController.
 *
 * @author nexphernandez
 * @version 1.0.0
 */
@SpringBootTest
@AutoConfigureMockMvc
class SmartCalendarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SmartCalendarService smartCalendarService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/smart-calendar/geocode - Should return coordinates")
    @WithMockUser
    void shouldGeocodeDestination() throws Exception {
        SmartGeocodeRequest request = new SmartGeocodeRequest();
        request.setQuery("Puerta del Sol, Madrid");

        SmartGeocodeResponse response = new SmartGeocodeResponse("Puerta del Sol, Madrid", 40.4168, -3.7038);
        when(smartCalendarService.geocode(any(String.class))).thenReturn(response);

        mockMvc.perform(post("/api/smart-calendar/geocode")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latitude").value(40.4168))
                .andExpect(jsonPath("$.longitude").value(-3.7038));
    }

    @Test
    @DisplayName("POST /api/smart-calendar/recommendations - Should return recommendations")
    @WithMockUser
    void shouldReturnRecommendations() throws Exception {
        SmartRecommendationRequest request = new SmartRecommendationRequest();
        request.setLatitude(40.4168);
        request.setLongitude(-3.7038);
        request.setStartTime(LocalDateTime.now().plusHours(1).toString());
        request.setDurationHours(2);
        request.setRadiusKm(5.0);

        SmartRecommendationResponse response = new SmartRecommendationResponse();
        when(smartCalendarService.recommend(any(SmartRecommendationRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/smart-calendar/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/smart-calendar/geocode - Bad Request when query missing")
    @WithMockUser
    void shouldReturn400WhenGeocodeMissingQuery() throws Exception {
        mockMvc.perform(post("/api/smart-calendar/geocode")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/smart-calendar/recommendations - Bad Request when fields missing")
    @WithMockUser
    void shouldReturn400WhenRecommendationsMissingFields() throws Exception {
        mockMvc.perform(post("/api/smart-calendar/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/smart-calendar/geocode - Forbidden without user")
    void shouldReturnForbiddenWithoutUser() throws Exception {
        mockMvc.perform(post("/api/smart-calendar/geocode")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isForbidden());
    }
}
