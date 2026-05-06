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
import com.nomorelaps.adapters.in.api.DynamicPriceRequest;
import com.nomorelaps.business.interfaces.IDynamicPriceService;
import com.nomorelaps.domain.models.DynamicPrice;

/**
 * Integration tests for DynamicPriceController.
 *
 * @author nexphernandez
 * @version 1.0.0
 */
@SpringBootTest
@AutoConfigureMockMvc
class DynamicPriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IDynamicPriceService dynamicPriceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/dynamic-prices - Should create price rule")
    @WithMockUser(roles = "COMPANY")
    void shouldCreateDynamicPrice() throws Exception {
        DynamicPriceRequest request = new DynamicPriceRequest();
        request.setDayOfWeek(1);
        request.setStartHour("08:00");
        request.setEndHour("20:00");
        request.setMinPrice(1.0);
        request.setMaxPrice(5.0);

        DynamicPrice saved = new DynamicPrice(1L);
        when(dynamicPriceService.create(any(DynamicPrice.class))).thenReturn(saved);

        mockMvc.perform(post("/api/dynamic-prices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("GET /api/dynamic-prices/{id} - Found")
    @WithMockUser(roles = "COMPANY")
    void shouldReturnDynamicPriceById() throws Exception {
        DynamicPrice dp = new DynamicPrice(1L);
        when(dynamicPriceService.findById(1L)).thenReturn(Optional.of(dp));

        mockMvc.perform(get("/api/dynamic-prices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("GET /api/dynamic-prices/parking/{id} - List")
    @WithMockUser(roles = "COMPANY")
    void shouldReturnParkingRules() throws Exception {
        when(dynamicPriceService.findByParkingId(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/dynamic-prices/parking/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("PUT /api/dynamic-prices/{id} - Updated")
    @WithMockUser(roles = "COMPANY")
    void shouldUpdateDynamicPrice() throws Exception {
        DynamicPriceRequest request = new DynamicPriceRequest();
        request.setDayOfWeek(1);
        request.setStartHour("08:00");
        request.setEndHour("22:00");
        request.setMinPrice(2.0);
        request.setMaxPrice(10.0);

        DynamicPrice updated = new DynamicPrice(1L);
        when(dynamicPriceService.update(any(DynamicPrice.class))).thenReturn(updated);

        mockMvc.perform(put("/api/dynamic-prices/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("DELETE /api/dynamic-prices/{id} - Deleted")
    @WithMockUser(roles = "COMPANY")
    void shouldDeleteDynamicPrice() throws Exception {
        doNothing().when(dynamicPriceService).deleteById(1L);

        mockMvc.perform(delete("/api/dynamic-prices/1"))
                .andExpect(status().isNoContent());
    }
}
