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
import com.nomorelaps.adapters.in.api.CompanyRequest;
import com.nomorelaps.business.interfaces.ICompanyService;
import com.nomorelaps.domain.models.Company;
import com.nomorelaps.infrastructure.security.SecurityService;

/**
 * Integration tests for CompanyController.
 * Ensures CRUD operations and special endpoints like API key regeneration work as expected.
 * 
 * @author nexphernandez
 * @version 1.1.0
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ICompanyService companyService;

    @MockitoBean(name = "securityService")
    private SecurityService securityService;

    private CompanyRequest validRequest;
    private Company sampleCompany;

    @BeforeEach
    void setUp() {
        validRequest = new CompanyRequest();
        validRequest.setName("Global Parking");
        validRequest.setEmail("contact@global.com");
        validRequest.setPassword("pass123");
        validRequest.setCif("B98765432");

        sampleCompany = new Company(1L);
        sampleCompany.setName("Global Parking");
        sampleCompany.setEmail("contact@global.com");
        sampleCompany.setCif("B98765432");
        sampleCompany.setApiKey("old-key");

        when(securityService.isCompanyOwner(any())).thenReturn(true);
    }

    @Test
    @DisplayName("POST /api/companies - Success: Should create company")
    @WithMockUser(roles = "ADMIN")
    void shouldCreateCompanySuccessfully() throws Exception {
        when(companyService.create(any(Company.class))).thenReturn(sampleCompany);

        mockMvc.perform(post("/api/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Global Parking"));
    }

    @Test
    @DisplayName("POST /api/companies - Failure: Should return 400 for empty request")
    @WithMockUser(roles = "ADMIN")
    void shouldReturnBadRequestForEmptyCreation() throws Exception {
        mockMvc.perform(post("/api/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/companies/{id} - Success: Should return company data")
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCompanyWhenExists() throws Exception {
        when(companyService.findById(1L)).thenReturn(Optional.of(sampleCompany));

        mockMvc.perform(get("/api/companies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cif").value("B98765432"));
    }

    @Test
    @DisplayName("GET /api/companies/{id} - Failure: Should return 404 when missing")
    @WithMockUser(roles = "ADMIN")
    void shouldReturnNotFoundWhenCompanyIsMissing() throws Exception {
        when(companyService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/companies/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/companies/email/{email} - Success: Should return company")
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCompanyByEmailSuccessfully() throws Exception {
        when(companyService.findByEmail("contact@global.com")).thenReturn(Optional.of(sampleCompany));

        mockMvc.perform(get("/api/companies/email/contact@global.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("contact@global.com"));
    }

    @Test
    @DisplayName("GET /api/companies/apikey/{apiKey} - Success: Should return company")
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCompanyByApiKeySuccessfully() throws Exception {
        when(companyService.findByApiKey("old-key")).thenReturn(Optional.of(sampleCompany));

        mockMvc.perform(get("/api/companies/apikey/old-key"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apiKey").value("old-key"));
    }

    @Test
    @DisplayName("PUT /api/companies/{id} - Success: Should update and return 200")
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateCompanySuccessfully() throws Exception {
        Company updated = new Company(1L);
        updated.setName("Global Parking Updated");
        when(companyService.update(any(Company.class))).thenReturn(updated);

        mockMvc.perform(put("/api/companies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Global Parking Updated"));
    }

    @Test
    @DisplayName("DELETE /api/companies/{id} - Success: Should return 204")
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteCompanySuccessfully() throws Exception {
        doNothing().when(companyService).deleteById(1L);

        mockMvc.perform(delete("/api/companies/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("POST /api/companies/{id}/regenerate-api-key - Success: Should return 200 with new key")
    @WithMockUser(roles = "COMPANY")
    void shouldRegenerateApiKeySuccessfully() throws Exception {
        sampleCompany.setApiKey("brand-new-key-777");
        when(companyService.regenerateApiKey(1L)).thenReturn(sampleCompany);

        mockMvc.perform(post("/api/companies/1/regenerate-api-key"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apiKey").value("brand-new-key-777"));
    }
}
