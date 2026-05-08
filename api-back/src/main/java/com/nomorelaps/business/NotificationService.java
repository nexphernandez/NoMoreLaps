package com.nomorelaps.business;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomorelaps.adapters.out.persistence.interfaces.INotificationPersistenceAdapter;
import com.nomorelaps.business.interfaces.INotificationService;
import com.nomorelaps.domain.models.Notification;

/**
 * Business Service implementation for Notification management.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Service
public class NotificationService implements INotificationService {

    private final INotificationPersistenceAdapter persistencePort;

    /**
     * Constructor for NotificationService.
     * 
     * @param persistencePort the persistence adapter for notification operations.
     */
    @Autowired
    public NotificationService(INotificationPersistenceAdapter persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public Notification create(Notification notification) {
        if (notification.getCreatedAt() == null) {
            notification.setCreatedAt(java.time.LocalDateTime.now());
        }
        return persistencePort.save(notification);
    }

    @Override
    public Notification markAsRead(Long id) {
        Notification notification = persistencePort.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + id));
        notification.setIsRead(true);
        return persistencePort.save(notification);
    }

    @Override
    public List<Notification> findByCompanyId(Long companyId) {
        return persistencePort.findByCompanyId(companyId);
    }

    @Override
    public void markAllAsRead(Long companyId) {
        List<Notification> notifications = persistencePort.findByCompanyId(companyId);
        notifications.forEach(n -> {
            if (!n.getIsRead()) {
                n.setIsRead(true);
                persistencePort.save(n);
            }
        });
    }

    @Override
    public void deleteById(Long id) {
        persistencePort.deleteById(id);
    }
}
