package com.nomorelaps.adapters.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
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
 * Verifies sanction management including creation, retrieval by user/company/reservation,
 * and payment status updates.
 * 
 * @author nexphernandez
 * @version 1.1.0
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class SanctionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ISanctionService sanctionService;

    @MockitoSpyBean
    private SanctionMapper sanctionMapper;

    private SanctionRequest validRequest;
    private Sanction sampleSanction;

    @BeforeEach
    void setUp() {
        validRequest = new SanctionRequest();
        validRequest.setAmount(25.0);
        validRequest.setReason("Unauthorized extended stay");
        validRequest.setReservationId(10L);
        validRequest.setUserId(5L);

        sampleSanction = new Sanction(1L);
        sampleSanction.setAmount(25.0);
        sampleSanction.setReason("Unauthorized extended stay");
        sampleSanction.setPaid(false);
    }

    @Test
    @DisplayName("POST /api/sanctions - Success: Should create sanction")
    @WithMockUser
    void shouldCreateSanctionSuccessfully() throws Exception {
        when(sanctionService.create(any(Sanction.class))).thenReturn(sampleSanction);

        mockMvc.perform(post("/api/sanctions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.amount").value(25.0));
    }

    @Test
    @DisplayName("GET /api/sanctions/{id} - Success: Should return sanction details")
    @WithMockUser
    void shouldReturnSanctionByIdSuccessfully() throws Exception {
        when(sanctionService.findById(1L)).thenReturn(Optional.of(sampleSanction));

        mockMvc.perform(get("/api/sanctions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("GET /api/sanctions/{id} - Failure: Should return 404 when missing")
    @WithMockUser
    void shouldReturnNotFoundWhenSanctionIsMissing() throws Exception {
        when(sanctionService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/sanctions/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/sanctions/user/{userId} - Success: Should return user sanctions")
    @WithMockUser
    void shouldReturnSanctionsByUserIdSuccessfully() throws Exception {
        when(sanctionService.findByUserId(5L)).thenReturn(List.of(sampleSanction));

        mockMvc.perform(get("/api/sanctions/user/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @DisplayName("GET /api/sanctions/company/{companyId} - Success: Should return company sanctions")
    @WithMockUser
    void shouldReturnSanctionsByCompanyIdSuccessfully() throws Exception {
        when(sanctionService.findByCompanyId(100L)).thenReturn(List.of(sampleSanction));

        mockMvc.perform(get("/api/sanctions/company/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("GET /api/sanctions/reservation/{id} - Success: Should return reservation sanctions")
    @WithMockUser
    void shouldReturnSanctionsByReservationIdSuccessfully() throws Exception {
        when(sanctionService.findByReservationId(10L)).thenReturn(List.of(sampleSanction));

        mockMvc.perform(get("/api/sanctions/reservation/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @DisplayName("PUT /api/sanctions/{id} - Success: Should update sanction")
    @WithMockUser
    void shouldUpdateSanctionSuccessfully() throws Exception {
        sampleSanction.setAmount(30.0);
        when(sanctionService.update(any(Sanction.class))).thenReturn(sampleSanction);

        mockMvc.perform(put("/api/sanctions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(30.0));
    }

    @Test
    @DisplayName("DELETE /api/sanctions/{id} - Success: Should return 204")
    @WithMockUser
    void shouldDeleteSanctionSuccessfully() throws Exception {
        doNothing().when(sanctionService).deleteById(1L);

        mockMvc.perform(delete("/api/sanctions/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("PATCH /api/sanctions/{id}/pay - Success: Should mark as paid")
    @WithMockUser
    void shouldPaySanctionSuccessfully() throws Exception {
        sampleSanction.setPaid(true);
        when(sanctionService.paySanction(1L)).thenReturn(sampleSanction);

        mockMvc.perform(patch("/api/sanctions/1/pay"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paid").value(true));
    }
}
