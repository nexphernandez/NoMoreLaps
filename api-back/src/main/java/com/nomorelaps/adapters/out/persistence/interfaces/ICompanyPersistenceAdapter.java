package com.nomorelaps.adapters.out.persistence.interfaces;

import java.util.Optional;

import com.nomorelaps.adapters.out.persistence.jpa.CompanyJpaEntity;

/**
 * Persistence secondary port for {@link CompanyJpaEntity}.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface ICompanyPersistenceAdapter extends IBasePersistenceAdapter<CompanyJpaEntity,Long> {
    /**
     * Finds a company in the database using its associated email address.
     * 
     * @param email The corporate email address of the company.
     * @return {@link Optional} containing the {@link CompanyJpaEntity} entity if it exists, or empty otherwise.
     */
    Optional<CompanyJpaEntity> findByEmail(String email);
    
    /**
     * Finds a company in the database by its API key.
     * 
     * @param password The API key of the company.
     * @return {@link Optional} containing the {@link CompanyJpaEntity} entity if it exists, or empty otherwise.
     */
    Optional<CompanyJpaEntity> findByApiKey(String password);
}
