package com.nomorelaps.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Collections;
import java.util.function.Function;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Unit tests for JwtAuthenticationFilter.
 * Adheres to the New Backend Test Refactoring Plan for granularity and business naming.
 * Verifies that Bearer tokens correctly authenticate users and handle edge cases.
 */
@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock private JwtService jwtService;
    @Mock private UserDetailsService userDetailsService;
    @Mock private HttpServletRequest mockRequest;
    @Mock private HttpServletResponse mockResponse;
    @Mock private FilterChain mockFilterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private String jwtToken;
    private String userEmail;
    private UserDetails testUserDetails;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
        jwtToken = "valid-jwt";
        userEmail = "test@test.com";
        testUserDetails = mock(UserDetails.class);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }


    @Test
    @DisplayName("doFilter - No Header: Should skip authentication")
    void doFilter_NoHeader_ShouldSkipAuthentication() throws ServletException, IOException {
        when(mockRequest.getHeader("Authorization")).thenReturn(null);
        jwtAuthenticationFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("doFilter - Wrong Prefix: Should skip authentication")
    void doFilter_WrongPrefix_ShouldSkipAuthentication() throws ServletException, IOException {
        when(mockRequest.getHeader("Authorization")).thenReturn("ApiKey 123");
        jwtAuthenticationFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }


    @Test
    @DisplayName("doFilter - Valid Token: Should set security context")
    @SuppressWarnings("unchecked")
    void doFilter_ValidToken_ShouldSetSecurityContext() throws ServletException, IOException {
        when(testUserDetails.getAuthorities()).thenReturn(Collections.emptyList());
        when(mockRequest.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtService.extractUsername(jwtToken)).thenReturn(userEmail);
        when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(testUserDetails);
        when(jwtService.isTokenValid(jwtToken, testUserDetails)).thenReturn(true);
        when(jwtService.extractClaim(eq(jwtToken), any())).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals(testUserDetails, authentication.getPrincipal());
    }

    @Test
    @DisplayName("doFilter - SecurityUser mapping: Should re-instantiate with companyId")
    @SuppressWarnings("unchecked")
    void doFilter_SecurityUser_ShouldMapCompanyId() throws ServletException, IOException {
        SecurityUser existingSecurityUser = new SecurityUser(new UserJpaEntity());
        
        when(mockRequest.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtService.extractUsername(jwtToken)).thenReturn(userEmail);
        when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(existingSecurityUser);
        when(jwtService.isTokenValid(jwtToken, existingSecurityUser)).thenReturn(true);
        when(jwtService.extractClaim(eq(jwtToken), any())).thenReturn(99L);

        jwtAuthenticationFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        SecurityUser authenticatedUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assertEquals(99L, authenticatedUser.getCompanyId());
    }

    @Test
    @DisplayName("doFilter - Token Invalid: Should not authenticate")
    void doFilter_TokenInvalid_ShouldNotSetAuthentication() throws ServletException, IOException {
        when(mockRequest.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtService.extractUsername(jwtToken)).thenReturn(userEmail);
        when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(testUserDetails);
        when(jwtService.isTokenValid(jwtToken, testUserDetails)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("doFilter - UserEmail Null: Should skip and continue chain")
    void doFilter_UserEmailNull_ShouldSkipAndContinue() throws ServletException, IOException {
        String jwtToken = "token-without-subject";
        when(mockRequest.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtService.extractUsername(jwtToken)).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(mockFilterChain).doFilter(mockRequest, mockResponse);
    }

    @Test
    @DisplayName("doFilter - Parsing Error: Should skip and continue chain")
    void doFilter_ParsingError_ShouldSkipAndContinue() throws ServletException, IOException {
        when(mockRequest.getHeader("Authorization")).thenReturn("Bearer corrupted");
        when(jwtService.extractUsername(anyString())).thenThrow(new RuntimeException("Bad JWT"));

        jwtAuthenticationFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(mockFilterChain).doFilter(mockRequest, mockResponse);
    }

    @Test
    @DisplayName("doFilter - Already Authenticated with Email: Should skip user loading")
    void doFilter_AlreadyAuthenticated_WithValidEmail_ShouldSkipLoading() throws ServletException, IOException {
        Authentication existingAuthentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(existingAuthentication);
        
        when(mockRequest.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtService.extractUsername(jwtToken)).thenReturn(userEmail);

        jwtAuthenticationFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        verify(userDetailsService, never()).loadUserByUsername(anyString());
        assertEquals(existingAuthentication, SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("doFilter - ExtractClaim: Should verify lambda searches for companyId")
    @SuppressWarnings("unchecked")
    void doFilter_ExtractClaim_ShouldVerifyLambdaLogic() throws ServletException, IOException {
        UserDetails regularUser = mock(UserDetails.class);
        when(regularUser.getAuthorities()).thenReturn(Collections.emptyList());
        
        when(mockRequest.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtService.extractUsername(jwtToken)).thenReturn(userEmail);
        when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(regularUser);
        when(jwtService.isTokenValid(jwtToken, regularUser)).thenReturn(true);

        jwtAuthenticationFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        ArgumentCaptor<Function> lambdaCaptor = ArgumentCaptor.forClass(Function.class);
        verify(jwtService).extractClaim(eq(jwtToken), lambdaCaptor.capture());

        Claims mockClaims = mock(Claims.class);
        lambdaCaptor.getValue().apply(mockClaims);
        verify(mockClaims).get("companyId", Long.class);
    }

    @Test
    @DisplayName("doFilter - Not SecurityUser: Should still call extractClaim")
    @SuppressWarnings("unchecked")
    void doFilter_NotSecurityUser_ShouldStillCallExtractClaim() throws ServletException, IOException {
        UserDetails regularUser = mock(UserDetails.class);
        when(regularUser.getAuthorities()).thenReturn(Collections.emptyList());
        
        when(mockRequest.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtService.extractUsername(jwtToken)).thenReturn(userEmail);
        when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(regularUser);
        when(jwtService.isTokenValid(jwtToken, regularUser)).thenReturn(true);

        jwtAuthenticationFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        verify(jwtService).extractClaim(eq(jwtToken), any());
    }

    @Test
    @DisplayName("doFilter - Already Authenticated: Should not override existing auth")
    void doFilter_AlreadyAuthenticated_ShouldNotOverride() throws ServletException, IOException {
        Authentication existingAuthentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(existingAuthentication);
        when(mockRequest.getHeader("Authorization")).thenReturn("Bearer any-token");

        jwtAuthenticationFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        assertEquals(existingAuthentication, SecurityContextHolder.getContext().getAuthentication());
        verify(userDetailsService, never()).loadUserByUsername(anyString());
    }
}
