package com.nomorelaps.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.nomorelaps.business.interfaces.ICompanyService;
import com.nomorelaps.domain.models.Company;

@ExtendWith(MockitoExtension.class)
class ApiKeyFilterTest {

    @Mock
    private ICompanyService companyService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private ApiKeyFilter filter;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Should skip filter when API key header is missing")
    void shouldSkipWhenHeaderMissing() throws ServletException, IOException {
        when(request.getHeader("X-API-KEY")).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(companyService);
    }

    @Test
    @DisplayName("Should skip authentication when already authenticated")
    void shouldSkipWhenAlreadyAuthenticated() throws ServletException, IOException {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("user", "pass"));
        when(request.getHeader("X-API-KEY")).thenReturn("valid-api-key");

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(companyService);
    }

    @Test
    @DisplayName("Should skip setting auth context when API key is invalid")
    void shouldSkipWhenApiKeyInvalid() throws ServletException, IOException {
        when(request.getHeader("X-API-KEY")).thenReturn("invalid-api-key");
        when(companyService.findByApiKey("invalid-api-key")).thenReturn(Optional.empty());

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("Should authenticate company when API key is valid")
    void shouldAuthenticateWhenApiKeyValid() throws ServletException, IOException {
        Company company = new Company();
        company.setEmail("company@test.com");
        
        when(request.getHeader("X-API-KEY")).thenReturn("valid-api-key");
        when(companyService.findByApiKey("valid-api-key")).thenReturn(Optional.of(company));

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("company@test.com", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        assertTrue(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_COMPANY")));
        assertEquals(company, SecurityContextHolder.getContext().getAuthentication().getDetails());
    }
}
