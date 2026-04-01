package com.nomorelaps.adapters.out.persistence.abstracta;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nomorelaps.adapters.out.persistence.interfaces.IBasePersistenceAdapter;

/**
 * Abstract implementation of the base persistence adapter.
 * Handles the bidirectional mapping between Domain <-> JpaEntity cleanly.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public abstract class BasePersistenceAdapter<Domain, Entity, ID, Repository extends JpaRepository<Entity, ID>> implements IBasePersistenceAdapter<Domain, ID> {

    protected final Repository repository;

    protected BasePersistenceAdapter(Repository repository) {
        this.repository = repository;
    }

    protected abstract Entity toEntity(Domain domain);
    protected abstract Domain toDomain(Entity entity);

    @Override
    public Domain save(Domain domain) {
        Entity entity = toEntity(domain);
        Entity saved = repository.save(entity);
        return toDomain(saved);
    }
    
    @Override
    public Optional<Domain> findById(ID id) {
        return repository.findById(id).map(this::toDomain);
    }
    
    @Override
    public List<Domain> findAll() {
        return repository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }
}
