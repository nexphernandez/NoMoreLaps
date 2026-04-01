package com.nomorelaps.adapters.out.persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nomorelaps.adapters.mapper.UserMapper;
import com.nomorelaps.adapters.out.persistence.abstracta.BasePersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.interfaces.IUserPersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.UserJpaRepository;
import com.nomorelaps.domain.models.User;

/**
 * Persistence implementation for User via Spring Data repositories.
 * Uses the Mapper internally to isolate the JpaEntity.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Component
public class UserPersistenceAdapter 
        extends BasePersistenceAdapter<User, UserJpaEntity, Long, UserJpaRepository> 
        implements IUserPersistenceAdapter {

    private final UserMapper userMapper;

    @Autowired
    public UserPersistenceAdapter(UserJpaRepository repository, UserMapper userMapper) {
        super(repository);
        this.userMapper = userMapper;
    }

    @Override
    protected UserJpaEntity toEntity(User domain) {
        return userMapper.toJpaEntity(domain);
    }

    @Override
    protected User toDomain(UserJpaEntity entity) {
        return userMapper.toDomain(entity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email).map(this::toDomain);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
