package com.nomorelaps.adapters.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import com.nomorelaps.adapters.in.api.CompanyRequest;
import com.nomorelaps.business.interfaces.ICompanyService;
import com.nomorelaps.domain.models.Company;

/**
 * Integration tests for CompanyController - method by method.
 *
 * @author nexphernandez
 * @version 1.0.0
 */
@SpringBootTest
@AutoConfigureMockMvc
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICompanyService companyService;

    @Autowired
    private ObjectMapper objectMapper;

    private CompanyRequest buildValidRequest() {
        CompanyRequest req = new CompanyRequest();
        req.setName("Parking Corp");
        req.setEmail("parking@corp.com");
        req.setPassword("securePass");
        req.setCif("B12345678");
        return req;
    }

    // ── POST /api/companies - create ──────────────────────────────────────────

    @Test
    @DisplayName("POST /api/companies - Should create company and return 201")
    @WithMockUser
    void shouldCreateCompany() throws Exception {
        Company saved = new Company(1L);
        saved.setName("Parking Corp");
        when(companyService.create(any(Company.class))).thenReturn(saved);

        mockMvc.perform(post("/api/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buildValidRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("POST /api/companies - Should return 400 when required fields missing")
    @WithMockUser
    void shouldReturn400WhenInvalidCompanyRequest() throws Exception {
        mockMvc.perform(post("/api/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    // ── GET /api/companies/{id} - findById ────────────────────────────────────

    @Test
    @DisplayName("GET /api/companies/{id} - Should return company when found")
    @WithMockUser
    void shouldReturnCompanyById() throws Exception {
        Company company = new Company(1L);
        company.setName("Parking Corp");
        when(companyService.findById(1L)).thenReturn(Optional.of(company));

        mockMvc.perform(get("/api/companies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("GET /api/companies/{id} - Should return 404 when not found")
    @WithMockUser
    void shouldReturn404WhenCompanyNotFound() throws Exception {
        when(companyService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/companies/99"))
                .andExpect(status().isNotFound());
    }

    // ── GET /api/companies/email/{email} - findByEmail ────────────────────────

    @Test
    @DisplayName("GET /api/companies/email/{email} - Should return company")
    @WithMockUser
    void shouldReturnCompanyByEmail() throws Exception {
        Company company = new Company(1L);
        when(companyService.findByEmail("parking@corp.com")).thenReturn(Optional.of(company));

        mockMvc.perform(get("/api/companies/email/parking@corp.com"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/companies/email/{email} - Should return 404 when not found")
    @WithMockUser
    void shouldReturn404WhenEmailNotFound() throws Exception {
        when(companyService.findByEmail("unknown@corp.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/companies/email/unknown@corp.com"))
                .andExpect(status().isNotFound());
    }

    // ── GET /api/companies/apikey/{apiKey} - findByApiKey ─────────────────────

    @Test
    @DisplayName("GET /api/companies/apikey/{apiKey} - Should return company")
    @WithMockUser
    void shouldReturnCompanyByApiKey() throws Exception {
        Company company = new Company(1L);
        when(companyService.findByApiKey("my-key")).thenReturn(Optional.of(company));

        mockMvc.perform(get("/api/companies/apikey/my-key"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/companies/apikey/{apiKey} - Should return 404 when not found")
    @WithMockUser
    void shouldReturn404WhenApiKeyNotFound() throws Exception {
        when(companyService.findByApiKey("bad-key")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/companies/apikey/bad-key"))
                .andExpect(status().isNotFound());
    }

    // ── PUT /api/companies/{id} - update ──────────────────────────────────────

    @Test
    @DisplayName("PUT /api/companies/{id} - Should update company")
    @WithMockUser
    void shouldUpdateCompany() throws Exception {
        Company updated = new Company(1L);
        updated.setName("Updated Corp");
        when(companyService.update(any(Company.class))).thenReturn(updated);

        mockMvc.perform(put("/api/companies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buildValidRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    // ── DELETE /api/companies/{id} - delete ───────────────────────────────────

    @Test
    @DisplayName("DELETE /api/companies/{id} - Should return 204")
    @WithMockUser
    void shouldDeleteCompany() throws Exception {
        doNothing().when(companyService).deleteById(1L);

        mockMvc.perform(delete("/api/companies/1"))
                .andExpect(status().isNoContent());
    }
}
