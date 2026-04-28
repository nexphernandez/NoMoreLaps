package com.nomorelaps.adapters.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nomorelaps.adapters.out.persistence.jpa.RoleJpaEntity;
/**
 * JPA Repository for {@link RoleJpaEntity}.
 * Provides database access and CRUD operations via Spring Data.
 * 
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface RoleJpaRepository extends JpaRepository<RoleJpaEntity, Long> {
    /**
     * Finds a role by its unique name (e.g., ROLE_USER, ROLE_ADMIN).
     * 
     * @param name The name of the role.
     * @return An Optional containing the role.
     */
    java.util.Optional<RoleJpaEntity> findByName(String name);
}
