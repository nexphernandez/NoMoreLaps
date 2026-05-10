package com.nomorelaps.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nomorelaps.domain.models.Company;

/**
 * Unit tests for SecurityService.
 * Adheres to the New Backend Test Refactoring Plan for granularity and business naming.
 * Verifies complex authorization logic for company ownership and resource access.
 */
class SecurityServiceTest {

    private SecurityService securityService;
    private Authentication mockAuthentication;
    private SecurityContext mockSecurityContext;

    @BeforeEach
    void setUp() {
        securityService = new SecurityService();
        mockAuthentication = mock(Authentication.class);
        mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }


    @Test
    @DisplayName("isCompanyOwner - Null auth: Should deny access")
    void isCompanyOwner_NullAuth_ShouldReturnFalse() {
        when(mockSecurityContext.getAuthentication()).thenReturn(null);
        assertFalse(securityService.isCompanyOwner(1L));
    }


    @Test
    @DisplayName("isCompanyOwner - Company Match: Should allow access for API Key owner")
    void isCompanyOwner_CompanyMatch_ShouldReturnTrue() {
        Company company = new Company(1L);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getDetails()).thenReturn(company);

        assertTrue(securityService.isCompanyOwner(1L));
    }

    @Test
    @DisplayName("isCompanyOwner - Company Mismatch: Should deny access for API Key from other company")
    void isCompanyOwner_CompanyMismatch_ShouldReturnFalse() {
        Company company = new Company(2L);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getDetails()).thenReturn(company);

        assertFalse(securityService.isCompanyOwner(1L));
    }


    @Test
    @DisplayName("isCompanyOwner - Admin bypass: Should always allow access for ROLE_ADMIN")
    void isCompanyOwner_AdminRole_ShouldReturnTrue() {
        SecurityUser adminUser = mock(SecurityUser.class);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getPrincipal()).thenReturn(adminUser);
        doReturn(List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))).when(mockAuthentication).getAuthorities();

        assertTrue(securityService.isCompanyOwner(999L));
    }

    @Test
    @DisplayName("isCompanyOwner - User Match: Should allow access for user belonging to target company")
    void isCompanyOwner_UserMatch_ShouldReturnTrue() {
        SecurityUser securityUser = mock(SecurityUser.class);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getPrincipal()).thenReturn(securityUser);
        doReturn(Collections.emptyList()).when(mockAuthentication).getAuthorities();
        when(securityUser.getCompanyId()).thenReturn(1L);

        assertTrue(securityService.isCompanyOwner(1L));
    }

    @Test
    @DisplayName("isCompanyOwner - User Mismatch: Should deny access for user from other company")
    void isCompanyOwner_UserMismatch_ShouldReturnFalse() {
        SecurityUser securityUser = mock(SecurityUser.class);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getPrincipal()).thenReturn(securityUser);
        doReturn(Collections.emptyList()).when(mockAuthentication).getAuthorities();
        when(securityUser.getCompanyId()).thenReturn(2L);

        assertFalse(securityService.isCompanyOwner(1L));
    }

    @Test
    @DisplayName("isCompanyOwner - User No Company: Should deny access when user has no companyId")
    void isCompanyOwner_UserNoCompany_ShouldReturnFalse() {
        SecurityUser securityUser = mock(SecurityUser.class);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getPrincipal()).thenReturn(securityUser);
        doReturn(Collections.emptyList()).when(mockAuthentication).getAuthorities();
        when(securityUser.getCompanyId()).thenReturn(null);

        assertFalse(securityService.isCompanyOwner(1L));
    }

    @Test
    @DisplayName("isCompanyOwner - Invalid Principal: Should deny access for unknown principals")
    void isCompanyOwner_GenericPrincipal_ShouldReturnFalse() {
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getPrincipal()).thenReturn("anonymousUser");

        assertFalse(securityService.isCompanyOwner(1L));
    }
}
