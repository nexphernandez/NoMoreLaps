package com.nomorelaps.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nomorelaps.business.interfaces.ICompanyService;
import com.nomorelaps.domain.models.Company;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Unit tests for ApiKeyFilter.
 * Adheres to the New Backend Test Refactoring Plan for granularity and business naming.
 * Verifies that the 'X-API-KEY' header correctly handles company authentication.
 */
@ExtendWith(MockitoExtension.class)
class ApiKeyFilterTest {

    @Mock
    private ICompanyService companyService;

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private FilterChain mockFilterChain;

    @InjectMocks
    private ApiKeyFilter apiKeyFilter;

    private Company testCompany;
    private final String VALID_KEY = "valid-key";

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
        testCompany = new Company(1L);
        testCompany.setEmail("corp@test.com");
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }


    @Test
    @DisplayName("doFilter - Missing Header: Should not authenticate")
    void doFilter_NoHeader_ShouldNotAuthenticate() throws ServletException, IOException {
        when(mockRequest.getHeader("X-API-KEY")).thenReturn(null);
        apiKeyFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("doFilter - Already Authenticated: Should skip validation")
    void doFilter_AlreadyAuthenticated_ShouldSkipApiKeyCheck() throws ServletException, IOException {
        Authentication existingAuthentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(existingAuthentication);
        when(mockRequest.getHeader("X-API-KEY")).thenReturn(VALID_KEY);

        apiKeyFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        verify(companyService, never()).findByApiKey(anyString());
        assertEquals(existingAuthentication, SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("doFilter - Invalid Key: Should not set authentication")
    void doFilter_InvalidKey_ShouldNotSetAuthentication() throws ServletException, IOException {
        when(mockRequest.getHeader("X-API-KEY")).thenReturn("invalid");
        when(companyService.findByApiKey("invalid")).thenReturn(Optional.empty());

        apiKeyFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("doFilter - Valid Key: Should populate security context")
    void doFilter_ValidKey_ShouldSetAuthentication() throws ServletException, IOException {
        when(mockRequest.getHeader("X-API-KEY")).thenReturn(VALID_KEY);
        when(companyService.findByApiKey(VALID_KEY)).thenReturn(Optional.of(testCompany));

        apiKeyFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(currentAuthentication);
        assertEquals("corp@test.com", currentAuthentication.getPrincipal());
    }

    @Test
    @DisplayName("doFilter - Valid Key: Should attach company details")
    void doFilter_ValidKey_ShouldAttachDetails() throws ServletException, IOException {
        when(mockRequest.getHeader("X-API-KEY")).thenReturn(VALID_KEY);
        when(companyService.findByApiKey(VALID_KEY)).thenReturn(Optional.of(testCompany));

        apiKeyFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
        assertEquals(testCompany, currentAuthentication.getDetails());
    }
}
