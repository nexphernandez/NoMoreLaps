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
import com.nomorelaps.adapters.in.api.DynamicPriceRequest;
import com.nomorelaps.business.interfaces.IDynamicPriceService;
import com.nomorelaps.domain.models.DynamicPrice;

/**
 * Integration tests for DynamicPriceController.
 * Validates dynamic pricing CRUD operations and parking-specific rule retrieval.
 * 
 * @author nexphernandez
 * @version 1.1.0
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class DynamicPriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IDynamicPriceService dynamicPriceService;

    private DynamicPriceRequest validRequest;
    private DynamicPrice samplePrice;

    @BeforeEach
    void setUp() {
        validRequest = new DynamicPriceRequest();
        validRequest.setDayOfWeek(1); 
        validRequest.setStartHour("09:00");
        validRequest.setEndHour("18:00");
        validRequest.setMinPrice(1.5);
        validRequest.setMaxPrice(4.0);

        samplePrice = new DynamicPrice(1L);
        samplePrice.setDayOfWeek(1);
        samplePrice.setStartHour("09:00");
        samplePrice.setEndHour("18:00");
        samplePrice.setMinPrice(1.5);
        samplePrice.setMaxPrice(4.0);
    }

    @Test
    @DisplayName("POST /api/dynamic-prices - Success: Should create price rule")
    @WithMockUser(roles = "COMPANY")
    void shouldCreateDynamicPriceSuccessfully() throws Exception {
        when(dynamicPriceService.create(any(DynamicPrice.class))).thenReturn(samplePrice);

        mockMvc.perform(post("/api/dynamic-prices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.minPrice").value(1.5));
    }

    @Test
    @DisplayName("GET /api/dynamic-prices/{id} - Success: Should return rule when exists")
    @WithMockUser(roles = "COMPANY")
    void shouldReturnDynamicPriceWhenExists() throws Exception {
        when(dynamicPriceService.findById(1L)).thenReturn(Optional.of(samplePrice));

        mockMvc.perform(get("/api/dynamic-prices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("GET /api/dynamic-prices/{id} - Failure: Should return 404 when missing")
    @WithMockUser(roles = "COMPANY")
    void shouldReturnNotFoundWhenPriceIsMissing() throws Exception {
        when(dynamicPriceService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/dynamic-prices/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/dynamic-prices/parking/{id} - Success: Should return list of rules")
    @WithMockUser(roles = "COMPANY")
    void shouldReturnRulesForParking() throws Exception {
        when(dynamicPriceService.findByParkingId(10L)).thenReturn(List.of(samplePrice));

        mockMvc.perform(get("/api/dynamic-prices/parking/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @DisplayName("PUT /api/dynamic-prices/{id} - Success: Should update rule")
    @WithMockUser(roles = "COMPANY")
    void shouldUpdateDynamicPriceSuccessfully() throws Exception {
        samplePrice.setMaxPrice(6.0);
        when(dynamicPriceService.update(any(DynamicPrice.class))).thenReturn(samplePrice);

        mockMvc.perform(put("/api/dynamic-prices/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxPrice").value(6.0));
    }

    @Test
    @DisplayName("DELETE /api/dynamic-prices/{id} - Success: Should return 204")
    @WithMockUser(roles = "COMPANY")
    void shouldDeleteDynamicPriceSuccessfully() throws Exception {
        doNothing().when(dynamicPriceService).deleteById(1L);

        mockMvc.perform(delete("/api/dynamic-prices/1"))
                .andExpect(status().isNoContent());
    }
}
