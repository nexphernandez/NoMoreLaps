package com.nomorelaps.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomorelaps.adapters.out.persistence.interfaces.IUserPersistenceAdapter;
import com.nomorelaps.business.interfaces.IUserService;
import com.nomorelaps.domain.models.User;

/**
 * Use Case implementation for User operations.
 * Connects the API layer with the Domain and Persistence layers.
 * 
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Service
public class UserService implements IUserService {

    private final IUserPersistenceAdapter persistencePort;

    @Autowired
    public UserService(IUserPersistenceAdapter persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public User create(User user) {
        if (persistencePort.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("BusinessRuleException: El email del usuario ya está registrado en el sistema.");
        }
        return persistencePort.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return persistencePort.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return persistencePort.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return persistencePort.findAll();
    }

    @Override
    public User update(User user) {
        return persistencePort.save(user);
    }

    @Override
    public void deleteById(Long id) {
        persistencePort.deleteById(id);
    }
}
