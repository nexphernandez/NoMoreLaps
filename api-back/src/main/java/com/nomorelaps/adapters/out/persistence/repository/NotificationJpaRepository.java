package com.nomorelaps.adapters.out.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nomorelaps.adapters.out.persistence.jpa.NotificationJpaEntity;

/**
 * JPA Repository for NotificationJpaEntity.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Repository
public interface NotificationJpaRepository extends JpaRepository<NotificationJpaEntity, Long> {
    /**
     * Finds notifications by company ID ordered by creation date descending.
     * 
     * @param companyId the identifier of the company
     * @return a list of notification entities
     */
    List<NotificationJpaEntity> findByCompanyIdOrderByCreatedAtDesc(Long companyId);
}
