package com.nomorelaps.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    private final String SECRET = "mysecretkeythatislongenoughforhmacsha256";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", SECRET);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 60L);
    }

    @Test
    @DisplayName("generateToken - Should generate a valid token")
    void shouldGenerateToken() {
        when(userDetails.getUsername()).thenReturn("user@test.com");
        
        String token = jwtService.generateToken(userDetails);
        
        assertNotNull(token);
        assertEquals("user@test.com", jwtService.extractUsername(token));
    }

    @Test
    @DisplayName("isTokenValid - Should return true for valid token and user")
    void shouldValidateToken() {
        when(userDetails.getUsername()).thenReturn("user@test.com");
        String token = jwtService.generateToken(userDetails);
        
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    @DisplayName("isTokenValid - Should return false for different user")
    void shouldFailForDifferentUser() {
        when(userDetails.getUsername()).thenReturn("user@test.com");
        String token = jwtService.generateToken(userDetails);
        
        UserDetails otherUser = org.mockito.Mockito.mock(UserDetails.class);
        when(otherUser.getUsername()).thenReturn("other@test.com");
        
        assertFalse(jwtService.isTokenValid(token, otherUser));
    }
}
