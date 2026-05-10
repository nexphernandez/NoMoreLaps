package com.nomorelaps.adapters.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nomorelaps.adapters.in.api.AuthRequest;
import com.nomorelaps.adapters.in.api.AuthResponse;
import com.nomorelaps.adapters.in.api.CompanyRequest;
import com.nomorelaps.infrastructure.security.AuthService;

/**
 * Integration tests for AuthCompanyController.
 * Verifies company registration and login endpoints with validation rules.
 * 
 * @author nexphernandez
 * @version 1.1.0
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AuthCompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    private CompanyRequest validCompanyRequest;
    private AuthRequest validAuthRequest;

    @BeforeEach
    void setUp() {
        validCompanyRequest = new CompanyRequest();
        validCompanyRequest.setName("Tech Solutions");
        validCompanyRequest.setEmail("admin@tech.com");
        validCompanyRequest.setPassword("strongPass123");
        validCompanyRequest.setCif("A12345678");

        validAuthRequest = new AuthRequest();
        validAuthRequest.setEmail("admin@tech.com");
        validAuthRequest.setPassword("strongPass123");
    }

    @Test
    @DisplayName("POST /api/auth/company/register - Success: Should return 200 and auth response")
    void shouldRegisterCompanySuccessfully() throws Exception {
        AuthResponse expectedResponse = new AuthResponse("jwt-company-token", "Welcome", 1L, "admin@tech.com", "Tech Solutions", 50L);
        when(authService.registerCompany(any(CompanyRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/api/auth/company/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validCompanyRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-company-token"))
                .andExpect(jsonPath("$.companyId").value(1L))
                .andExpect(jsonPath("$.name").value("Tech Solutions"));
    }

    @Test
    @DisplayName("POST /api/auth/company/register - Failure: Should return 400 when CIF is missing")
    void shouldReturnBadRequestWhenCifIsMissing() throws Exception {
        validCompanyRequest.setCif("");

        mockMvc.perform(post("/api/auth/company/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validCompanyRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/company/login - Success: Should return 200 and token")
    void shouldLoginSuccessfully() throws Exception {
        AuthResponse expectedResponse = new AuthResponse("jwt-login-token", "Login OK", 1L, "admin@tech.com", "Tech Solutions", 50L);
        when(authService.login(any(AuthRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/api/auth/company/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validAuthRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-login-token"))
                .andExpect(jsonPath("$.companyId").value(1L));
    }

    @Test
    @DisplayName("POST /api/auth/company/login - Failure: Should return 400 when credentials invalid format")
    void shouldReturnBadRequestWhenEmailFormatIsInvalid() throws Exception {
        validAuthRequest.setEmail("bad-email");

        mockMvc.perform(post("/api/auth/company/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validAuthRequest)))
                .andExpect(status().isBadRequest());
    }
}
