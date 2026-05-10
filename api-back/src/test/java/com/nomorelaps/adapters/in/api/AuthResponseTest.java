package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for AuthResponse DTO.
 * Verifies that the authentication response payload is correctly mapped.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class AuthResponseTest {

    private AuthResponse authResponse;

    @BeforeEach
    void setUp() {
        authResponse = new AuthResponse();
    }

    @Test
    @DisplayName("Constructor - Should correctly initialize all fields")
    void shouldInitializeWithFullConstructor() {
        
        AuthResponse fullResponse = new AuthResponse("jwt-token-xyz", "Success", 100L, "user@test.com", "John Doe", 500L);

        assertEquals("jwt-token-xyz", fullResponse.getToken());
        assertEquals("Success", fullResponse.getMessage());
        assertEquals(100L, fullResponse.getCompanyId());
        assertEquals("user@test.com", fullResponse.getEmail());
        assertEquals("John Doe", fullResponse.getName());
        assertEquals(500L, fullResponse.getUserId());
    }

    @Test
    @DisplayName("token - Should set and get the JWT token")
    void shouldSetAndGetToken() {
        authResponse.setToken("some-secure-token");
        assertEquals("some-secure-token", authResponse.getToken());
    }

    @Test
    @DisplayName("message - Should set and get the status message")
    void shouldSetAndGetMessage() {
        authResponse.setMessage("Authentication failed");
        assertEquals("Authentication failed", authResponse.getMessage());
    }

    @Test
    @DisplayName("companyId - Should set and get the company ID")
    void shouldSetAndGetCompanyId() {
        authResponse.setCompanyId(99L);
        assertEquals(99L, authResponse.getCompanyId());
    }

    @Test
    @DisplayName("email - Should set and get the email")
    void shouldSetAndGetEmail() {
        authResponse.setEmail("test@domain.com");
        assertEquals("test@domain.com", authResponse.getEmail());
    }

    @Test
    @DisplayName("name - Should set and get the name")
    void shouldSetAndGetName() {
        authResponse.setName("Alice Smith");
        assertEquals("Alice Smith", authResponse.getName());
    }

    @Test
    @DisplayName("userId - Should set and get the user ID")
    void shouldSetAndGetUserId() {
        authResponse.setUserId(12345L);
        assertEquals(12345L, authResponse.getUserId());
    }
}
