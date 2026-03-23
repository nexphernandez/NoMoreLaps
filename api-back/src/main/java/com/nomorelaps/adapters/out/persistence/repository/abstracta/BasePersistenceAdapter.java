
package com.nomorelaps.adapters.out.persistence.repository.abstracta;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Abstract implementation of the base persistence adapter.
 * Facilitates clean code by providing the most repetitive CRUD operations.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public abstract class BasePersistenceAdapter<E, ID, R extends JpaRepository<E, ID>> {

    protected final R repository;

    protected BasePersistenceAdapter(R repository) {
        this.repository = repository;
    }

    public E save(E entity) {
        return repository.save(entity);
    }
    public Optional<E> findById(ID id) {
        return repository.findById(id);
    }
    public List<E> findAll() {
        return repository.findAll();
    }
    public void deleteById(ID id) {
        repository.deleteById(id);
    }
    
}
