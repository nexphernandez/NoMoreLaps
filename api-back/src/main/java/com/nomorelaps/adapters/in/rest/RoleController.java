package com.nomorelaps.adapters.in.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nomorelaps.adapters.in.api.RoleRequest;
import com.nomorelaps.adapters.in.api.RoleResponse;
import com.nomorelaps.adapters.mapper.RoleMapper;
import com.nomorelaps.business.interfaces.IRoleService;
import com.nomorelaps.domain.models.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

/**
 * REST Controller for Role endpoints.
 * Exposes CRUD operations for the Role entity via the API layer.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/roles")
@Tag(name = "Role", description = "Operations related to system roles management")
public class RoleController {

    private final IRoleService roleService;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleController(IRoleService roleService, RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    /**
     * Creates a new role.
     *
     * @param request The role data from the API.
     * @return The created role as a response DTO.
     */
    @PostMapping
    @Operation(summary = "Create a new role", description = "Defines a new user role in the system (e.g., ADMIN, USER).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Role created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<RoleResponse> create(@Valid @RequestBody RoleRequest request) {
        Role domain = roleMapper.toDomainFromRequest(request);
        Role saved = roleService.create(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(roleMapper.toResponse(saved));
    }

    /**
     * Finds a role by its unique identifier.
     *
     * @param id The role ID.
     * @return The found role or 404 if not found.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Find role by ID", description = "Retrieves role details by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role found"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<RoleResponse> findById(@PathVariable Long id) {
        return roleService.findById(id)
                .map(role -> ResponseEntity.ok(roleMapper.toResponse(role)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deletes a role by its ID.
     *
     * @param id The role ID to delete.
     * @return 204 No Content.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a role", description = "Removes a role definition from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Role deleted"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
