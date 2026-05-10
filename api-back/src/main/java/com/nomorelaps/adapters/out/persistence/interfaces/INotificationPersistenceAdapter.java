package com.nomorelaps.adapters.out.persistence.interfaces;

import java.util.List;
import java.util.Optional;

import com.nomorelaps.domain.models.Notification;

/**
 * Persistence secondary port for Notification operations.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface INotificationPersistenceAdapter extends IBasePersistenceAdapter<Notification, Long> {
    /**
     * Saves a notification in the persistence layer.
     * 
     * @param notification the notification to save
     * @return the saved notification
     */
    Notification save(Notification notification);

    /**
     * Finds a notification by its identifier.
     * 
     * @param id the identifier of the notification
     * @return an Optional containing the notification if found
     */
    Optional<Notification> findById(Long id);

    /**
     * Finds notifications associated with a company, ordered by creation date.
     * 
     * @param companyId the identifier of the company
     * @return a list of notifications
     */
    List<Notification> findByCompanyId(Long companyId);

    /**
     * Deletes a notification by its identifier.
     * 
     * @param id the identifier of the notification to delete
     */
    void deleteById(Long id);
}
