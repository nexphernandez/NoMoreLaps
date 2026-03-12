package com.nomorelaps.adapters.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nomorelaps.adapters.out.persistence.jpa.CompanyJpaEntity;
/**
 * @author nexphernandez
 * @version 1.0.0
 * Company jpa interface
 */
public interface CompanyJpaRepository extends JpaRepository<CompanyJpaEntity,Long> {

}
