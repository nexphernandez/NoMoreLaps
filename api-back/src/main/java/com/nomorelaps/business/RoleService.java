package com.nomorelaps.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomorelaps.adapters.out.persistence.interfaces.IRolePersistenceAdapter;
import com.nomorelaps.business.interfaces.IRoleService;
import com.nomorelaps.domain.models.Role;

/**
 * Use Case implementation for Role operations.
 * Connects the API layer with the Domain and Persistence layers.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Service
public class RoleService implements IRoleService {

    private final IRolePersistenceAdapter persistencePort;

    @Autowired
    public RoleService(IRolePersistenceAdapter persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public Role create(Role role) {
        return persistencePort.save(role);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return persistencePort.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return persistencePort.findAll();
    }

    @Override
    public void deleteById(Long id) {
        persistencePort.deleteById(id);
    }
}
