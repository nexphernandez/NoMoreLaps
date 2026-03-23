package com.nomorelaps.adapters.out.persistence.interfaces;

import java.util.Optional;
import java.util.List;

/**
 * General secondary database port that abstracts the application
 * from the underlying technology details and enforces basic CRUD methods.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface IBasePersistenceAdapter<E,ID> {
    /**
     * Saves or updates an entity in the database.
     * 
     * @param entity The generic entity to be persisted.
     * @return The persisted entity (with its generated ID).
     */
    E save(E entity);
    
    /**
     * Finds an element by its ID.
     * 
     * @param id Unique identifier of the entity.
     * @return An Optional wrapping the found entity, or empty if it does not exist.
     */
    Optional<E> findById(ID id);
    
    /**
     * Retrieves all occurrences stored in the database.
     * 
     * @return A constant list with all the entities.
     */
    List<E> findAll();
    
    /**
     * Permanently deletes an element from the database using its ID.
     * 
     * @param id The unique identifier of the entity to be deleted.
     */
    void deleteById(ID id);
}
