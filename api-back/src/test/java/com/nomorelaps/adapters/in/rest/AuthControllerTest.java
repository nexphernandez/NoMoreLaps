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
import com.nomorelaps.adapters.in.api.UserRequest;
import com.nomorelaps.adapters.in.api.UserResponse;
import com.nomorelaps.infrastructure.security.AuthService;

/**
 * Integration tests for AuthController.
 * Uses @SpringBootTest to ensure proper context loading while mocking the service layer.
 * 
 * @author nexphernandez
 * @version 1.1.0
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    private UserRequest validUserRequest;
    private AuthRequest validAuthRequest;

    @BeforeEach
    void setUp() {
        validUserRequest = new UserRequest();
        validUserRequest.setName("John Doe");
        validUserRequest.setEmail("john.doe@example.com");
        validUserRequest.setPassword("securePassword123");

        validAuthRequest = new AuthRequest();
        validAuthRequest.setEmail("john.doe@example.com");
        validAuthRequest.setPassword("securePassword123");
    }

    @Test
    @DisplayName("POST /api/auth/register - Success: Should return 200 and user info")
    void shouldRegisterUserSuccessfully() throws Exception {
        UserResponse expectedResponse = new UserResponse(100L);
        when(authService.register(any(UserRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUserRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100L));
    }

    @Test
    @DisplayName("POST /api/auth/register - Failure: Should return 400 when name is missing")
    void shouldReturnBadRequestWhenRegisterNameIsMissing() throws Exception {
        validUserRequest.setName("");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUserRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/register - Failure: Should return 400 when email is invalid")
    void shouldReturnBadRequestWhenRegisterEmailIsInvalid() throws Exception {
        validUserRequest.setEmail("not-an-email");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUserRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/login - Success: Should return 200 and token")
    void shouldLoginSuccessfully() throws Exception {
        AuthResponse expectedResponse = new AuthResponse("token-123", "Login OK", null, "john.doe@example.com", "John Doe", 100L);
        when(authService.login(any(AuthRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validAuthRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token-123"))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    @DisplayName("POST /api/auth/login - Failure: Should return 400 when email is missing")
    void shouldReturnBadRequestWhenLoginEmailIsMissing() throws Exception {
        validAuthRequest.setEmail(null);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validAuthRequest)))
                .andExpect(status().isBadRequest());
    }
}
