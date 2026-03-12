package com.nomorelaps.adapters.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nomorelaps.adapters.out.persistence.jpa.DynamicPriceJpaEntity;
/**
 * @author nexphernandez
 * @version 1.0.0
 * Dynamic price jpa interface
 */
public interface DynamicPriceJpaRepository extends JpaRepository<DynamicPriceJpaEntity,Long> {

}
