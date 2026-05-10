package com.nomorelaps.infrastructure.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nomorelaps.business.interfaces.ICompanyService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter to authenticate requests using an API Key.
 * Checks for the 'X-API-KEY' header and validates it against the database.
 * This allows companies to integrate our services into their own systems.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    private final ICompanyService companyService;

    public ApiKeyFilter(ICompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String apiKey = request.getHeader("X-API-KEY");

        // If an API Key is provided and the request is not yet authenticated
        if (apiKey != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            companyService.findByApiKey(apiKey).ifPresent(company -> {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        company.getEmail(),
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_COMPANY"))
                );
                // Store the company object in details for easy access in controllers if needed
                authentication.setDetails(company);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            });
        }

        filterChain.doFilter(request, response);
    }
}
