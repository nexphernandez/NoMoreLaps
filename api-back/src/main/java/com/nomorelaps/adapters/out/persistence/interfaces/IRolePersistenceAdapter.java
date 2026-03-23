package com.nomorelaps.adapters.out.persistence.interfaces;

import com.nomorelaps.adapters.out.persistence.jpa.RoleJpaEntity;

/**
 * Persistence secondary port for {@link RoleJpaEntity}.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface IRolePersistenceAdapter extends IBasePersistenceAdapter<RoleJpaEntity, Long> {
    // As it is just a basic user role, the base save/find/delete methods from IBasePersistenceAdapter are enough.
}
