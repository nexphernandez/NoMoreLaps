package com.nomorelaps.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit tests for JwtService.
 * Adheres to the New Backend Test Refactoring Plan for granularity and business naming.
 * Verifies JWT token lifecycle, parsing logic, and security validation.
 */
class JwtServiceTest {

    private JwtService jwtService;
    private UserDetails testUserDetails;
    private final String TEST_SECRET = "my-test-secret-key-that-needs-to-be-sufficiently-long";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        testUserDetails = mock(UserDetails.class);
        when(testUserDetails.getUsername()).thenReturn("alice@test.com");
        
        ReflectionTestUtils.setField(jwtService, "secretKey", TEST_SECRET);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 60L);
    }


    @Test
    @DisplayName("generateToken - Output: Should produce a non-empty string")
    void generateToken_ShouldReturnNonEmptyString() {
        String jwtToken = jwtService.generateToken(testUserDetails);
        assertFalse(jwtToken.isEmpty());
    }

    @Test
    @DisplayName("generateToken - Claims: Should include custom data")
    void generateToken_WithExtraClaims_ShouldIncludeCustomData() {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("app", "NoMoreLaps");
        
        String jwtToken = jwtService.generateToken(extraClaims, testUserDetails);
        String appClaimValue = jwtService.extractClaim(jwtToken, claims -> claims.get("app", String.class));
        
        assertEquals("NoMoreLaps", appClaimValue);
    }


    @Test
    @DisplayName("isTokenValid - Case 1 (T && T): Should return true for valid token")
    void isTokenValid_Valid_ShouldReturnTrue() {
        String jwtToken = jwtService.generateToken(testUserDetails);
        assertTrue(jwtService.isTokenValid(jwtToken, testUserDetails));
    }

    @Test
    @DisplayName("isTokenValid - Case 2 (F && ?): Should return false for user mismatch")
    void isTokenValid_UserMismatch_ShouldReturnFalse() {
        String jwtToken = jwtService.generateToken(testUserDetails);
        UserDetails otherUserDetails = mock(UserDetails.class);
        when(otherUserDetails.getUsername()).thenReturn("bob@test.com");
        
        assertFalse(jwtService.isTokenValid(jwtToken, otherUserDetails));
    }

    @Test
    @DisplayName("isTokenValid - Case 3 (T && F): Should return false for expired token")
    void isTokenValid_Expired_ShouldReturnFalse() {
        JwtService spyService = spy(jwtService);
        String jwtToken = jwtService.generateToken(testUserDetails);
        
        doReturn(true).when(spyService).isTokenExpired(anyString());
        
        assertFalse(spyService.isTokenValid(jwtToken, testUserDetails));
    }

    @Test
    @DisplayName("isTokenValid - Exception: Should return false on parsing error")
    void isTokenValid_Exception_ShouldReturnFalse() {
        assertFalse(jwtService.isTokenValid("corrupted.token", testUserDetails));
    }


    @Test
    @DisplayName("isTokenExpired - Not Expired: Should return false for new tokens")
    void isTokenExpired_NotExpired_ShouldReturnFalse() {
        String jwtToken = jwtService.generateToken(testUserDetails);
        assertFalse(jwtService.isTokenExpired(jwtToken));
    }
}
