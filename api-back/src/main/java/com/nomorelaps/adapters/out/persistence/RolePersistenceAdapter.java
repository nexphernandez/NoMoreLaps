package com.nomorelaps.adapters.out.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nomorelaps.adapters.mapper.RoleMapper;
import com.nomorelaps.adapters.out.persistence.abstracta.BasePersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.interfaces.IRolePersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.jpa.RoleJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.RoleJpaRepository;
import com.nomorelaps.domain.models.Role;

/**
 * Persistence implementation for Role via Spring Data repositories.
 * Uses the Mapper internally to isolate the JpaEntity.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Component
public class RolePersistenceAdapter 
        extends BasePersistenceAdapter<Role, RoleJpaEntity, Long, RoleJpaRepository> 
        implements IRolePersistenceAdapter {

    private final RoleMapper mapper;

    @Autowired
    public RolePersistenceAdapter(RoleJpaRepository repository, RoleMapper mapper) {
        super(repository);
        this.mapper = mapper;
    }

    @Override
    protected RoleJpaEntity toEntity(Role domain) {
        return mapper.toJpaEntity(domain);
    }

    @Override
    protected Role toDomain(RoleJpaEntity entity) {
        return mapper.toDomain(entity);
    }
}
