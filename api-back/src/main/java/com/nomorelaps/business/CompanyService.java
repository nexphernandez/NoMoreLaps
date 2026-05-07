package com.nomorelaps.business;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomorelaps.adapters.out.persistence.interfaces.ICompanyPersistenceAdapter;
import com.nomorelaps.business.interfaces.ICompanyService;
import com.nomorelaps.domain.models.Company;

/**
 * Use Case implementation for Company operations.
 * Connects the API layer with the Domain and Persistence layers.
 * 
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Service
public class CompanyService implements ICompanyService {

    private final ICompanyPersistenceAdapter persistencePort;

    @Autowired
    public CompanyService(ICompanyPersistenceAdapter persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public Company create(Company company) {
        if (persistencePort.findByEmail(company.getEmail()).isPresent()) {
            throw new IllegalArgumentException("BusinessRuleException: El email de la empresa ya está registrado.");
        }
        if (company.getApiKey() == null || company.getApiKey().isEmpty()) {
            company.setApiKey("nml_live_" + UUID.randomUUID().toString().replace("-", ""));
        }
        return persistencePort.save(company);
    }

    @Override
    public Optional<Company> findById(Long id) {
        return persistencePort.findById(id);
    }

    @Override
    public List<Company> findAll() {
        return persistencePort.findAll();
    }

    @Override
    public Optional<Company> findByEmail(String email) {
        return persistencePort.findByEmail(email);
    }

    @Override
    public Optional<Company> findByApiKey(String apiKey) {
        return persistencePort.findByApiKey(apiKey);
    }

    @Override
    public Company update(Company company) {
        return persistencePort.save(company);
    }

    @Override
    public void deleteById(Long id) {
        persistencePort.deleteById(id);
    }

    @Override
    public Company regenerateApiKey(Long id) {
        Company company = persistencePort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));
        
        company.setApiKey("nml_live_" + UUID.randomUUID().toString().replace("-", ""));
        return persistencePort.save(company);
    }
}
