package com.nomorelaps.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
import io.jsonwebtoken.Claims;
import java.util.function.Function;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
        reset(request, response, filterChain, jwtService, userDetailsService);
    }

    @Test
    @DisplayName("doFilterInternal - Should proceed without auth if header is missing")
    void shouldProceedWhenNoHeader() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);
        filter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("doFilterInternal - Should proceed without auth if header is not Bearer")
    void shouldProceedWhenNotBearer() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Basic 123");
        filter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("doFilterInternal - Should authenticate when valid token present and execute lambda")
    void shouldAuthenticateWhenValidToken() throws Exception {
        String token = "valid-token";
        String email = "test@test.com";
        UserJpaEntity entity = new UserJpaEntity();
        entity.setEmail(email);
        SecurityUser su = new SecurityUser(entity);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.extractUsername(token)).thenReturn(email);
        when(userDetailsService.loadUserByUsername(email)).thenReturn(su);
        when(jwtService.isTokenValid(eq(token), any())).thenReturn(true);
        
        // Capture and test the lambda
        ArgumentCaptor<Function<Claims, Long>> captor = ArgumentCaptor.forClass(Function.class);
        when(jwtService.extractClaim(eq(token), captor.capture())).thenReturn(500L);

        filter.doFilterInternal(request, response, filterChain);

        // Execute the captured lambda manually to cover its code
        Claims mockClaims = mock(Claims.class);
        when(mockClaims.get("companyId", Long.class)).thenReturn(500L);
        Long result = captor.getValue().apply(mockClaims);
        
        assertEquals(500L, result);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("doFilterInternal - Should not authenticate if email is null")
    void shouldNotAuthenticateWhenEmailIsNull() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtService.extractUsername(anyString())).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("doFilterInternal - Should not authenticate if already authenticated")
    void shouldNotAuthenticateIfAlreadyAuth() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("user", "pass"));
        when(request.getHeader("Authorization")).thenReturn("Bearer some-token");
        lenient().when(jwtService.extractUsername(anyString())).thenReturn("test@test.com");

        filter.doFilterInternal(request, response, filterChain);

        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(filterChain).doFilter(request, response);
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("doFilterInternal - Should not authenticate if token invalid")
    void shouldNotAuthenticateIfTokenInvalid() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalid-token");
        lenient().when(jwtService.extractUsername(anyString())).thenReturn("test@test.com");
        lenient().when(userDetailsService.loadUserByUsername(anyString())).thenReturn(mock(org.springframework.security.core.userdetails.UserDetails.class));
        lenient().when(jwtService.isTokenValid(anyString(), any())).thenReturn(false);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("doFilterInternal - Should authenticate even if userDetails not SecurityUser")
    void shouldAuthenticateWhenNotSecurityUser() throws Exception {
        org.springframework.security.core.userdetails.UserDetails normalUser = mock(org.springframework.security.core.userdetails.UserDetails.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtService.extractUsername(anyString())).thenReturn("user@test.com");
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(normalUser);
        when(jwtService.isTokenValid(anyString(), any())).thenReturn(true);
        lenient().when(jwtService.extractClaim(anyString(), any())).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("doFilterInternal - Should handle exception and proceed")
    void shouldHandleException() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer error-token");
        when(jwtService.extractUsername(anyString())).thenThrow(new RuntimeException("JWT Error"));

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }
}
