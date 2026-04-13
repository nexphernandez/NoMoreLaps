package com.nomorelaps.adapters.out.persistence.interfaces;

import java.util.Optional;

import com.nomorelaps.domain.models.User;

/**
 * Persistence secondary port for {@link User}.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface IUserPersistenceAdapter extends IBasePersistenceAdapter<User,Long> {
    /**
     * Finds a user on the platform using their email address.
     * Commonly used in "Login" or access processes.
     * 
     * @param email The email address linked to the user account.
     * @return {@link Optional} containing the found {@link User} entity, or empty if it does not exist.
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Quickly verifies if an email address is already registered in the system,
     * optimized for registration validations without bringing the whole user object.
     * 
     * @param email The email to be validated in the database.
     * @return {@code true} if the email already exists and is in use, otherwise {@code false}.
     */
    boolean existsByEmail(String email);
}
