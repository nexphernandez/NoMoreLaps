package com.nomorelaps.adapters.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nomorelaps.adapters.mapper.NotificationMapper;
import com.nomorelaps.adapters.out.persistence.interfaces.INotificationPersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.jpa.NotificationJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.NotificationJpaRepository;
import com.nomorelaps.domain.models.Notification;

/**
 * Persistence secondary adapter for Notification.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Component
public class NotificationPersistenceAdapter implements INotificationPersistenceAdapter {

    private final NotificationJpaRepository repository;
    private final NotificationMapper mapper;

    /**
     * Constructor for NotificationPersistenceAdapter.
     * 
     * @param repository Spring Data repository for JPA operations
     * @param mapper mapper for domain-entity conversion
     */
    @Autowired
    public NotificationPersistenceAdapter(NotificationJpaRepository repository, NotificationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Notification save(Notification notification) {
        NotificationJpaEntity entity = mapper.toJpaEntity(notification);
        NotificationJpaEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Notification> findByCompanyId(Long companyId) {
        return repository.findByCompanyIdOrderByCreatedAtDesc(companyId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Notification> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
