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
public interface CompanyJpaRepository extends JpaRepository<CompanyJpaEntity,Long> {
    Optional<CompanyJpaEntity> findByEmail(String email);

    Optional<CompanyJpaEntity> findByApiKey(String password);
}
