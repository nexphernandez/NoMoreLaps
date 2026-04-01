package com.nomorelaps.adapters.out.persistence.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * High-level generic interface acting as the base contract for all persistence output ports.
 * Defines the essential CRUD operations ensuring uniform persistence behavior.
 *
 * @param <Domain> The generic Domain model type corresponding to the concrete entity.
 * @param <ID> The type of the unique identifier key for the entity.
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface IBasePersistenceAdapter<Domain, ID> {

    /**
     * Saves or updates an entity in the database.
     * 
     * @param domain The entity to correctly save.
     * @return The correctly saved entity.
     */
    Domain save(Domain domain);

    /**
     * Finds an entity based on its unique identifier.
     * 
     * @param id The ID to look for.
     * @return An {@link Optional} containing the entity if it properly exists, or empty otherwise.
     */
    Optional<Domain> findById(ID id);

    /**
     * Recovers absolutely all existing entities of this type.
     * 
     * @return A proper list of all entities.
     */
    List<Domain> findAll();

    /**
     * Physically or logically deletes an entity in the database.
     * 
     * @param id The identifier key corresponding to the database row to delete.
     */
    void deleteById(ID id);

}
