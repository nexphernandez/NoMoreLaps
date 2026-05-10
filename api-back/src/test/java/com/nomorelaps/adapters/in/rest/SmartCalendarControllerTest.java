package com.nomorelaps.adapters.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

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
import com.nomorelaps.adapters.in.api.SmartGeocodeRequest;
import com.nomorelaps.adapters.in.api.SmartGeocodeResponse;
import com.nomorelaps.adapters.in.api.SmartRecommendationRequest;
import com.nomorelaps.adapters.in.api.SmartRecommendationResponse;
import com.nomorelaps.business.SmartCalendarService;

/**
 * Integration tests for SmartCalendarController.
 * Validates geocoding requests and smart parking recommendations.
 * 
 * @author nexphernandez
 * @version 1.1.0
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class SmartCalendarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SmartCalendarService smartCalendarService;

    private SmartGeocodeRequest geocodeRequest;
    private SmartRecommendationRequest recommendationRequest;

    @BeforeEach
    void setUp() {
        geocodeRequest = new SmartGeocodeRequest();
        geocodeRequest.setQuery("Puerta del Sol, Madrid");

        recommendationRequest = new SmartRecommendationRequest();
        recommendationRequest.setLatitude(40.4168);
        recommendationRequest.setLongitude(-3.7038);
        recommendationRequest.setStartTime(LocalDateTime.now().plusHours(1).toString());
        recommendationRequest.setDurationHours(2);
        recommendationRequest.setRadiusKm(5.0);
    }

    @Test
    @DisplayName("POST /api/smart-calendar/geocode - Success: Should return coordinates")
    @WithMockUser
    void shouldGeocodeSuccessfully() throws Exception {
        SmartGeocodeResponse response = new SmartGeocodeResponse("Puerta del Sol, Madrid", 40.4168, -3.7038);
        when(smartCalendarService.geocode(anyString())).thenReturn(response);

        mockMvc.perform(post("/api/smart-calendar/geocode")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(geocodeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latitude").value(40.4168))
                .andExpect(jsonPath("$.longitude").value(-3.7038));
    }

    @Test
    @DisplayName("POST /api/smart-calendar/recommendations - Success: Should return recommendations")
    @WithMockUser
    void shouldReturnRecommendationsSuccessfully() throws Exception {
        SmartRecommendationResponse response = new SmartRecommendationResponse();
        when(smartCalendarService.recommend(any(SmartRecommendationRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/smart-calendar/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recommendationRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/smart-calendar/geocode - Failure: Should return 400 when query missing")
    @WithMockUser
    void shouldReturnBadRequestWhenGeocodeQueryIsMissing() throws Exception {
        mockMvc.perform(post("/api/smart-calendar/geocode")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/smart-calendar/recommendations - Failure: Should return 400 when fields missing")
    @WithMockUser
    void shouldReturnBadRequestWhenRecommendationsFieldsAreMissing() throws Exception {
        mockMvc.perform(post("/api/smart-calendar/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }
}
