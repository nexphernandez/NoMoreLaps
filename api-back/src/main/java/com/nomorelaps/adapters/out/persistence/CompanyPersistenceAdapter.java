package com.nomorelaps.adapters.out.persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nomorelaps.adapters.out.persistence.interfaces.ICompanyPersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.jpa.CompanyJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.CompanyJpaRepository;
import com.nomorelaps.adapters.out.persistence.repository.abstracta.BasePersistenceAdapter;

/**
 * Persistence implementation for CompanyJpaEntity via Spring Data repositories.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Component
public class CompanyPersistenceAdapter extends BasePersistenceAdapter<CompanyJpaEntity, 
        Long, CompanyJpaRepository> implements ICompanyPersistenceAdapter{

    @Autowired
    public CompanyPersistenceAdapter(CompanyJpaRepository repository) {
        super(repository);
    }

    @Override
    public Optional<CompanyJpaEntity> findByEmail(String email) {

        return repository.findByEmail(email);
    }

    @Override
    public Optional<CompanyJpaEntity> findByApiKey(String password) {
        return repository.findByApiKey(password);
    }
}
