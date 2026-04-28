package com.nomorelaps.business.interfaces;

import java.util.List;
import java.util.Optional;

import com.nomorelaps.domain.models.Company;

/**
 * Inbound port (Use Case) for Company operations.
 * Defines the contract that the API presentation layer will consume.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface ICompanyService {
    /**
     * Registers a new company.
     * 
     * @param company The company data.
     * @return The created company.
     */
    Company create(Company company);

    /**
     * Finds a company by ID.
     * 
     * @param id The company ID.
     * @return Optional company.
     */
    Optional<Company> findById(Long id);

    /**
     * Lists all companies.
     * 
     * @return List of companies.
     */
    List<Company> findAll();

    /**
     * Finds a company by email.
     * 
     * @param email The corporate email.
     * @return Optional company.
     */
    Optional<Company> findByEmail(String email);

    /**
     * Finds a company by its unique API Key.
     * 
     * @param apiKey The API key.
     * @return Optional company.
     */
    Optional<Company> findByApiKey(String apiKey);

    /**
     * Updates company details.
     * 
     * @param company Updated data.
     * @return Updated company.
     */
    Company update(Company company);

    /**
     * Deletes a company.
     * 
     * @param id Company ID.
     */
    void deleteById(Long id);
}
