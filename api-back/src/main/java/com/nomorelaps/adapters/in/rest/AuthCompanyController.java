package com.nomorelaps.adapters.in.rest;

import com.nomorelaps.adapters.in.api.AuthRequest;
import com.nomorelaps.adapters.in.api.AuthResponse;
import com.nomorelaps.adapters.in.api.CompanyRequest;
import com.nomorelaps.infrastructure.security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to handle company-specific authentication requests.
 * Separates company logic from standard user logic.
 *
 * @author nexphernandez
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/auth/company")
@Tag(name = "Company Authentication", description = "Endpoints for company registration and login")
public class AuthCompanyController {

    private final AuthService authService;

    @Autowired
    public AuthCompanyController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registers a new company in the system.
     * 
     * @param request The company data to register.
     * @return AuthResponse with JWT.
     */
    @PostMapping("/register")
    @Operation(summary = "Register a new company", description = "Creates a new company account and its admin user.")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody CompanyRequest request) {
        return ResponseEntity.ok(authService.registerCompany(request));
    }

    /**
     * Authenticates a company and generates a security token.
     * 
     * @param request The login credentials (email and password).
     * @return An authentication response containing the JWT token.
     */
    @PostMapping("/login")
    @Operation(summary = "Login as a company", description = "Validates credentials and returns a bearer token.")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
