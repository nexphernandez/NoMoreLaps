package com.nomorelaps.business.interfaces;

import java.util.List;
import java.util.Optional;

import com.nomorelaps.domain.models.User;

/**
 * Inbound port (Use Case) for User operations.
 * Defines the contract that the API presentation layer will consume.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface IUserService {
    /**
     * Creates a new user in the system.
     * 
     * @param user The user data.
     * @return The created user.
     */
    User create(User user);

    /**
     * Finds a user by ID.
     * 
     * @param id The user ID.
     * @return Optional user.
     */
    Optional<User> findById(Long id);

    /**
     * Finds a user by their email address.
     * 
     * @param email The user email.
     * @return Optional user.
     */
    Optional<User> findByEmail(String email);

    /**
     * Lists all registered users.
     * 
     * @return List of users.
     */
    List<User> findAll();

    /**
     * Updates an existing user's profile.
     * 
     * @param user Updated user data.
     * @return Updated user.
     */
    User update(User user);

    /**
     * Deletes a user account.
     * 
     * @param id The user ID.
     */
    void deleteById(Long id);
}
