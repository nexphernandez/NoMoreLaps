package com.nomorelaps.adapters.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
/**
 * JPA Repository for {@link UserJpaEntity}.
 * Provides database access and CRUD operations via Spring Data.
 * 
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface UserJpaRepository extends JpaRepository<UserJpaEntity,Long>{

    /**
     * Finds a user by their email address.
     * 
     * @param email The email to search for.
     * @return An Optional containing the found user.
     */
    Optional<UserJpaEntity> findByEmail(String email);

    /**
     * Checks if a user already exists with the given email.
     * 
     * @param email The email to check.
     * @return true if it exists, false otherwise.
     */
    boolean existsByEmail(String email);
}