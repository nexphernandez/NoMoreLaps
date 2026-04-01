package com.nomorelaps.adapters.in.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nomorelaps.adapters.in.api.RoleRequest;
import com.nomorelaps.adapters.in.api.RoleResponse;
import com.nomorelaps.adapters.mapper.RoleMapper;
import com.nomorelaps.business.interfaces.IRoleService;
import com.nomorelaps.domain.models.Role;

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
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
