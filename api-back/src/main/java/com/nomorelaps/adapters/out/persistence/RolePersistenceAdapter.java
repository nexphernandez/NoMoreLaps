package com.nomorelaps.adapters.out.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nomorelaps.adapters.out.persistence.interfaces.IRolePersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.jpa.RoleJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.RoleJpaRepository;
import com.nomorelaps.adapters.out.persistence.repository.abstracta.BasePersistenceAdapter;

/**
 * Persistence implementation for RoleJpaEntity via Spring Data repositories.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Component
public class RolePersistenceAdapter extends BasePersistenceAdapter<RoleJpaEntity, 
        Long, RoleJpaRepository> implements IRolePersistenceAdapter {

    @Autowired
    public RolePersistenceAdapter(RoleJpaRepository repository) {
        super(repository);
    }
    
}
