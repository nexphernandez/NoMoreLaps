package com.nomorelaps.adapters.in.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nomorelaps.adapters.in.api.UserRequest;
import com.nomorelaps.adapters.in.api.UserResponse;
import com.nomorelaps.adapters.mapper.UserMapper;
import com.nomorelaps.business.interfaces.IUserService;
import com.nomorelaps.domain.models.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

/**
 * REST Controller for User endpoints.
 * Exposes CRUD operations for the User entity via the API layer.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "Operations related to user management")
public class UserController {

    private final IUserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(IUserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     * Creates a new user in the system.
     *
     * @param request The user data from the API.
     * @return The created user as a response DTO.
     */
    @PostMapping
    @Operation(summary = "Create a new user", description = "Registers a new user in the system and returns the created user data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "409", description = "User already exists (email duplicate)")
    })
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request) {
        User domain = userMapper.toDomainFromRequest(request);
        User saved = userService.create(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toResponse(saved));
    }

    /**
     * Finds a user by its unique identifier.
     *
     * @param id The user ID.
     * @return The found user or 404 if not found.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Find user by ID", description = "Retrieves a single user by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(userMapper.toResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Finds a user by its email address.
     *
     * @param email The email to search for.
     * @return The found user or 404 if not found.
     */
    @GetMapping("/email/{email}")
    @Operation(summary = "Find user by email", description = "Retrieves a user searching by their registered email address.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponse> findByEmail(@PathVariable String email) {
        return userService.findByEmail(email)
                .map(user -> ResponseEntity.ok(userMapper.toResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all users in the system.
     *
     * @return A list of all users.
     */
    @GetMapping
    @Operation(summary = "Retrieve all users", description = "Returns a complete list of all users registered in the system.")
    @ApiResponse(responseCode = "200", description = "List of users retrieved")
    public ResponseEntity<List<UserResponse>> findAll() {
        List<UserResponse> responses = userService.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Updates an existing user.
     *
     * @param id      The user ID to update.
     * @param request The updated user data.
     * @return The updated user.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing user", description = "Updates the information of an existing user based on the provided ID and request body.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        User domain = userMapper.toDomainFromRequest(request);
        domain.setId(id);
        User updated = userService.update(domain);
        return ResponseEntity.ok(userMapper.toResponse(updated));
    }

    /**
     * Deletes a user by its ID.
     *
     * @param id The user ID to delete.
     * @return 204 No Content.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Deletes a user from the system by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
