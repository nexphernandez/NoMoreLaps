package com.nomorelaps.adapters.in.rest;

import com.nomorelaps.adapters.in.api.AuthRequest;
import com.nomorelaps.adapters.in.api.AuthResponse;
import com.nomorelaps.adapters.in.api.UserRequest;
import com.nomorelaps.adapters.in.api.UserResponse;
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
 * Controller to handle authentication requests like login and register.
 * Delegates logic to AuthService.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registers a new user in the system.
     * 
     * @param request The user data to register.
     * @return The registered user information.
     */
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account in the system database.")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Authenticates a user and generates a security token.
     * 
     * @param request The login credentials (email and password).
     * @return An authentication response containing the JWT token.
     */
    @PostMapping("/login")
    @Operation(summary = "Login to the system", description = "Validates credentials and returns a bearer token for subsequent requests.")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
