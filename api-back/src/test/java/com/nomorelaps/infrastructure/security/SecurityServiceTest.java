package com.nomorelaps.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nomorelaps.domain.models.Company;
import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;

class SecurityServiceTest {

    private SecurityService securityService;

    @BeforeEach
    void setUp() {
        securityService = new SecurityService();
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("isCompanyOwner - Should return false if no authentication")
    void shouldReturnFalseIfNoAuth() {
        assertFalse(securityService.isCompanyOwner(1L));
    }

    @Test
    @DisplayName("isCompanyOwner - Should return true if Company API Key matches")
    void shouldReturnTrueIfCompanyApiKeyMatches() {
        Company company = new Company(1L);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("key", "pass");
        auth.setDetails(company);
        
        SecurityContext ctx = mock(SecurityContext.class);
        when(ctx.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(ctx);

        assertTrue(securityService.isCompanyOwner(1L));
        assertFalse(securityService.isCompanyOwner(2L));
    }

    @Test
    @DisplayName("isCompanyOwner - Should return true if User has matching companyId")
    void shouldReturnTrueIfUserMatchesCompany() {
        UserJpaEntity user = new UserJpaEntity();
        SecurityUser su = new SecurityUser(user, 100L);
        
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(su, null, Collections.emptyList());
        
        SecurityContext ctx = mock(SecurityContext.class);
        when(ctx.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(ctx);

        assertTrue(securityService.isCompanyOwner(100L));
        assertFalse(securityService.isCompanyOwner(999L));
    }

    @Test
    @DisplayName("isCompanyOwner - Should return true if User is ADMIN")
    void shouldReturnTrueIfAdmin() {
        UserJpaEntity user = new UserJpaEntity();
        SecurityUser su = new SecurityUser(user, 50L);
        
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(su, null, 
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        
        SecurityContext ctx = mock(SecurityContext.class);
        when(ctx.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(ctx);

        assertTrue(securityService.isCompanyOwner(999L)); 
    }

    @Test
    @DisplayName("isCompanyOwner - Should return false if principal is not SecurityUser")
    void shouldReturnFalseIfPrincipalNotSecurityUser() {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("someone", null, Collections.emptyList());
        SecurityContext ctx = mock(SecurityContext.class);
        when(ctx.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(ctx);

        assertFalse(securityService.isCompanyOwner(1L));
    }

    @Test
    @DisplayName("isCompanyOwner - Should return false if SecurityUser has no companyId")
    void shouldReturnFalseIfNoCompanyIdInUser() {
        UserJpaEntity user = new UserJpaEntity();
        SecurityUser su = new SecurityUser(user, null); 
        
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(su, null, Collections.emptyList());
        SecurityContext ctx = mock(SecurityContext.class);
        when(ctx.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(ctx);

        assertFalse(securityService.isCompanyOwner(1L));
    }
}
