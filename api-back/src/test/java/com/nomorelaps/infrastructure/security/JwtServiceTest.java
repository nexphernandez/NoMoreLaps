package com.nomorelaps.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyString;

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

    @Test
    @DisplayName("isTokenValid - Should return false for expired token")
    void shouldReturnFalseForExpiredToken() {
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", -60L); 
        when(userDetails.getUsername()).thenReturn("user@test.com");
        
        String token = jwtService.generateToken(userDetails);
        
        assertFalse(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    @DisplayName("isTokenValid - Should return false when username does not match")
    void shouldReturnFalseWhenUsernameMismatch() {
        // Arrange
        when(userDetails.getUsername()).thenReturn("user@test.com");
        String token = jwtService.generateToken(userDetails);
        
        UserDetails otherUser = org.mockito.Mockito.mock(UserDetails.class);
        when(otherUser.getUsername()).thenReturn("wrong@test.com");
        
        // Act & Assert
        assertFalse(jwtService.isTokenValid(token, otherUser));
    }

    @Test
    @DisplayName("isTokenValid - Should return false when token is expired (covering the && second part)")
    void shouldReturnFalseWhenExpiredPartTwo() {
        // Arrange
        JwtService spyService = spy(jwtService);
        ReflectionTestUtils.setField(spyService, "secretKey", SECRET);
        ReflectionTestUtils.setField(spyService, "jwtExpiration", 60L);
        
        when(userDetails.getUsername()).thenReturn("user@test.com");
        String token = jwtService.generateToken(userDetails);
        
        doReturn("user@test.com").when(spyService).extractUsername(anyString());
        doReturn(true).when(spyService).isTokenExpired(anyString());
        
        boolean isValid = spyService.isTokenValid(token, userDetails);
        
        assertFalse(isValid, "Should be false because it is expired, even if username matches");
    }

    @Test
    @DisplayName("isTokenValid - Should return false on any exception")
    void shouldReturnFalseOnException() {
        
        boolean isValid = jwtService.isTokenValid("invalid-token", userDetails);
        
        assertFalse(isValid);
    }
}
