package com.nomorelaps.adapters.out.persistence.interfaces;

import com.nomorelaps.domain.models.Role;

/**
 * Persistence secondary port for {@link Role}.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface IRolePersistenceAdapter extends IBasePersistenceAdapter<Role, Long> {
    // As it is just a basic user role, the base save/find/delete methods from IBasePersistenceAdapter are enough.
}
