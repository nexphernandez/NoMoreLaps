package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for AuthRequest DTO.
 * Verifies that email and password credentials are correctly handled.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class AuthRequestTest {

    private AuthRequest authRequest;

    @BeforeEach
    void setUp() {
        authRequest = new AuthRequest();
    }

    @Test
    @DisplayName("Constructor - Should correctly initialize email and password")
    void shouldInitializeWithParameterizedConstructor() {
        AuthRequest loginRequest = new AuthRequest("user@test.com", "secret123");

        assertEquals("user@test.com", loginRequest.getEmail(), "Email should match constructor argument");
        assertEquals("secret123", loginRequest.getPassword(), "Password should match constructor argument");
    }

    @Test
    @DisplayName("email - Should set and get the email address")
    void shouldSetAndGetEmail() {
        authRequest.setEmail("admin@nomorelaps.com");
        assertEquals("admin@nomorelaps.com", authRequest.getEmail());
    }

    @Test
    @DisplayName("password - Should set and get the password")
    void shouldSetAndGetPassword() {
        authRequest.setPassword("securePass!01");
        assertEquals("securePass!01", authRequest.getPassword());
    }
}
