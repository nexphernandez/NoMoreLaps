package com.nomorelaps.business.interfaces;

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
    Company create(Company company);
    Optional<Company> findById(Long id);
    Optional<Company> findByEmail(String email);
    Optional<Company> findByApiKey(String apiKey);
    Company update(Company company);
    void deleteById(Long id);
}
