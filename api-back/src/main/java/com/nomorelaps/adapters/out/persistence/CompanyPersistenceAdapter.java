package com.nomorelaps.adapters.out.persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nomorelaps.adapters.mapper.CompanyMapper;
import com.nomorelaps.adapters.out.persistence.abstracta.BasePersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.interfaces.ICompanyPersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.jpa.CompanyJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.CompanyJpaRepository;
import com.nomorelaps.domain.models.Company;
import com.nomorelaps.domain.models.User;

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

    @Override
    protected CompanyJpaEntity toEntity(Company domain) {
        CompanyJpaEntity entity = mapper.toJpaEntity(domain);
        if (domain.getUser() != null && domain.getUser().getId() != null) {
            entity.setUser(new UserJpaEntity(domain.getUser().getId()));
        }
        return entity;
    }

    /**
     * Converts a Company JPA Entity to its domain model equivalent.
     */
    @Override
    protected Company toDomain(CompanyJpaEntity entity) {
        Company domain = mapper.toDomain(entity);
        if (entity.getUser() != null && entity.getUser().getId() != null) {
            domain.setUser(new User(entity.getUser().getId()));
        }
        return domain;
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
