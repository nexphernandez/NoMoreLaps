package com.nomorelaps.adapters.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nomorelaps.adapters.out.persistence.jpa.CompanyJpaEntity;

/**
 * JPA Repository for {@link CompanyJpaEntity}.
 * Provides database access and CRUD operations via Spring Data.
 * 
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface CompanyJpaRepository extends JpaRepository<CompanyJpaEntity, Long> {
    /**
     * Finds a company by its corporate email address.
     * 
     * @param email The email to search for.
     * @return An Optional containing the company.
     */
    Optional<CompanyJpaEntity> findByEmail(String email);

    /**
     * Finds a company using its unique API key.
     * 
     * @param apiKey The API key to search for.
     * @return An Optional containing the company.
     */
    Optional<CompanyJpaEntity> findByApiKey(String apiKey);

    /**
     * Finds a company by its owner user ID.
     * 
     * @param userId The ID of the owner user.
     * @return An Optional containing the company.
     */
    Optional<CompanyJpaEntity> findByUserId(Long userId);
}
