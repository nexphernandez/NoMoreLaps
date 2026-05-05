package com.nomorelaps.adapters.out.persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nomorelaps.adapters.mapper.CompanyMapper;
import com.nomorelaps.adapters.out.persistence.abstracta.BasePersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.interfaces.ICompanyPersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.jpa.CompanyJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.CompanyJpaRepository;
import com.nomorelaps.domain.models.Company;

/**
 * Persistence implementation for Company via Spring Data repositories.
 * Uses the Mapper internally to isolate the JpaEntity.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Component
public class CompanyPersistenceAdapter 
        extends BasePersistenceAdapter<Company, CompanyJpaEntity, Long, CompanyJpaRepository> 
        implements ICompanyPersistenceAdapter {

    private final CompanyMapper mapper;

    @Autowired
    public CompanyPersistenceAdapter(CompanyJpaRepository repository, CompanyMapper mapper) {
        super(repository);
        this.mapper = mapper;
    }

    /**
     * Converts a Company domain object to its JPA Entity equivalent.
     */
    @Override
    protected CompanyJpaEntity toEntity(Company domain) {
        return mapper.toJpaEntity(domain);
    }

    /**
     * Converts a Company JPA Entity to its domain model equivalent.
     */
    @Override
    protected Company toDomain(CompanyJpaEntity entity) {
        return mapper.toDomain(entity);
    }

    @Override
    public Optional<Company> findByEmail(String email) {
        return repository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public Optional<Company> findByApiKey(String password) {
        return repository.findByApiKey(password).map(this::toDomain);
    }
}
