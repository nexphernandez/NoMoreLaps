package com.nomorelaps.business.interfaces;

import java.util.List;
import java.util.Optional;
import com.nomorelaps.domain.models.Role;

/**
 * Inbound port (Use Case) for Role operations.
 * Defines the contract that the API presentation layer will consume.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface IRoleService {
    /**
     * Creates a new user role.
     * 
     * @param role The role data.
     * @return The created role.
     */
    Role create(Role role);

    /**
     * Finds a role by ID.
     * 
     * @param id The ID.
     * @return Optional role.
     */
    Optional<Role> findById(Long id);

    /**
     * Lists all available roles.
     * 
     * @return List of roles.
     */
    List<Role> findAll();

    /**
     * Deletes a role.
     * 
     * @param id The role ID.
     */
    void deleteById(Long id);
}
