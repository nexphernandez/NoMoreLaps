package com.nomorelaps.adapters.out.persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nomorelaps.adapters.out.persistence.interfaces.IUserPersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.UserJpaRepository;
import com.nomorelaps.adapters.out.persistence.repository.abstracta.BasePersistenceAdapter;

/**
 * Persistence implementation for UserJpaEntity via Spring Data repositories.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Component
public class UserPersistenceAdapter extends BasePersistenceAdapter<UserJpaEntity, 
        Long, UserJpaRepository> implements IUserPersistenceAdapter{

    @Autowired
    public UserPersistenceAdapter(UserJpaRepository repository) {
        super(repository);
    }

    @Override
    public Optional<UserJpaEntity> findByEmail(String email) {
        return repository.findByEmail(email);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

}
