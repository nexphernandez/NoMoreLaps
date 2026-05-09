package com.nomorelaps.adapters.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import com.nomorelaps.adapters.in.api.SanctionRequest;
import com.nomorelaps.adapters.mapper.SanctionMapper;
import com.nomorelaps.business.interfaces.ISanctionService;
import com.nomorelaps.domain.models.Sanction;

/**
 * Integration tests for SanctionController.
 *
 * @author nexphernandez
 * @version 1.0.0
 */
@SpringBootTest
@AutoConfigureMockMvc
class SanctionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ISanctionService sanctionService;

    @Autowired
    private SanctionMapper sanctionMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/sanctions/{id} - Found")
    @WithMockUser
    void shouldReturnSanctionById() throws Exception {
        Sanction sanction = new Sanction(1L);
        sanction.setAmount(50.0);
        when(sanctionService.findById(1L)).thenReturn(Optional.of(sanction));

        mockMvc.perform(get("/api/sanctions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("GET /api/sanctions/{id} - Not Found")
    @WithMockUser
    void shouldReturn404WhenSanctionNotFound() throws Exception {
        when(sanctionService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/sanctions/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/sanctions/user/{userId} - List")
    @WithMockUser
    void shouldReturnSanctionsByUser() throws Exception {
        Sanction s1 = new Sanction(1L);
        when(sanctionService.findByUserId(1L)).thenReturn(Arrays.asList(s1));

        mockMvc.perform(get("/api/sanctions/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("GET /api/sanctions/reservation/{id} - List")
    @WithMockUser
    void shouldReturnSanctionsByReservation() throws Exception {
        Sanction s = new Sanction(1L);
        when(sanctionService.findByReservationId(1L)).thenReturn(List.of(s));

        mockMvc.perform(get("/api/sanctions/reservation/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("DELETE /api/sanctions/{id} - No Content")
    @WithMockUser
    void shouldDeleteSanction() throws Exception {
        doNothing().when(sanctionService).deleteById(1L);

        mockMvc.perform(delete("/api/sanctions/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("PATCH /api/sanctions/{id}/pay - Should pay sanction")
    @WithMockUser
    void shouldPaySanction() throws Exception {
        Sanction paid = new Sanction(1L);
        paid.setAmount(50.0);
        paid.setPaid(true);
        when(sanctionService.paySanction(1L)).thenReturn(paid);

        mockMvc.perform(patch("/api/sanctions/1/pay"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("POST /api/sanctions - Created")
    @WithMockUser
    void shouldCreateSanction() throws Exception {
        SanctionRequest request = new SanctionRequest();
        request.setAmount(50.0);
        request.setReason("Overtime");
        request.setReservationId(1L);
        request.setUserId(1L);

        Sanction saved = new Sanction(1L);
        when(sanctionService.create(any(Sanction.class))).thenReturn(saved);

        mockMvc.perform(post("/api/sanctions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("POST /api/sanctions - Bad Request")
    @WithMockUser
    void shouldReturn400WhenInvalidCreate() throws Exception {
        mockMvc.perform(post("/api/sanctions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/sanctions/{id} - Updated")
    @WithMockUser
    void shouldUpdateSanction() throws Exception {
        SanctionRequest request = new SanctionRequest();
        request.setAmount(50.0);
        request.setReason("Updated Reason");
        request.setReservationId(1L);
        request.setUserId(1L);

        Sanction updated = new Sanction(1L);
        when(sanctionService.update(any(Sanction.class))).thenReturn(updated);

        mockMvc.perform(put("/api/sanctions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("GET /api/sanctions - Forbidden without user")
    void shouldReturnForbiddenWithoutUser() throws Exception {
        mockMvc.perform(get("/api/sanctions/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("GET /api/sanctions/company/{companyId} - Should return list")
    @WithMockUser
    void shouldFindByCompanyId() throws Exception {
        Sanction domain = new Sanction(1L);
        when(sanctionService.findByCompanyId(200L)).thenReturn(List.of(domain));

        mockMvc.perform(get("/api/sanctions/company/200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }
}
