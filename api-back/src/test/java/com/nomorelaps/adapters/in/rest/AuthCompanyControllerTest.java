package com.nomorelaps.adapters.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nomorelaps.adapters.in.api.AuthRequest;
import com.nomorelaps.adapters.in.api.AuthResponse;
import com.nomorelaps.adapters.in.api.CompanyRequest;
import com.nomorelaps.infrastructure.security.AuthService;

/**
 * Integration tests for AuthCompanyController.
 *
 * @author nexphernandez
 * @version 1.0.0
 */
@SpringBootTest
@AutoConfigureMockMvc
class AuthCompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/auth/company/login - Should return token and company info")
    void shouldLoginSuccessfully() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setEmail("company@test.com");
        request.setPassword("password123");

        AuthResponse response = new AuthResponse("mock-jwt-token", "Login successful", 1L, "company@test.com", "Test Company", 1L);
        when(authService.login(any(AuthRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/company/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mock-jwt-token"))
                .andExpect(jsonPath("$.companyId").value(1))
                .andExpect(jsonPath("$.name").value("Test Company"));
    }

    @Test
    @DisplayName("POST /api/auth/company/register - Should register company and return token")
    void shouldRegisterSuccessfully() throws Exception {
        CompanyRequest request = new CompanyRequest();
        request.setName("New Company");
        request.setEmail("new@company.com");
        request.setPassword("password123");
        request.setCif("B12345678");

        AuthResponse response = new AuthResponse("new-jwt-token", "Login successful", 2L, "new@company.com", "New Company", 2L);
        when(authService.registerCompany(any(CompanyRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/company/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("new-jwt-token"))
                .andExpect(jsonPath("$.companyId").value(2));
    }

    @Test
    @DisplayName("POST /api/auth/company/register - Bad request when missing CIF")
    void shouldReturn400WhenRegisterMissingCif() throws Exception {
        CompanyRequest request = new CompanyRequest();
        request.setName("New Company");
        request.setEmail("new@company.com");
        request.setPassword("password123");

        mockMvc.perform(post("/api/auth/company/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
