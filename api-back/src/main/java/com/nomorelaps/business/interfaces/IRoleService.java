package com.nomorelaps.business.interfaces;

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
    Role create(Role role);
    Optional<Role> findById(Long id);
    void deleteById(Long id);
}
