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

    Optional<UserJpaEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}