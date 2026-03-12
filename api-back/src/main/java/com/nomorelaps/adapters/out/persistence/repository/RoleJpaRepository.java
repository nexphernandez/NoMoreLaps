package com.nomorelaps.adapters.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nomorelaps.adapters.out.persistence.jpa.RoleJpaEntity;
/**
 * @author nexphernandez
 * @version 1.0.0
 * Role jpa interface
 */
public interface RoleJpaRepository extends JpaRepository<RoleJpaEntity,Long>{

}
