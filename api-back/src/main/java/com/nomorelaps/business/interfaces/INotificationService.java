package com.nomorelaps.business.interfaces;

import java.util.List;
import com.nomorelaps.domain.models.Notification;

/**
 * Domain Service interface for Notification operations.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface INotificationService {
    /**
     * Creates a new notification in the system.
     * 
     * @param notification the notification to create
     * @return the created notification
     */
    Notification create(Notification notification);

    /**
     * Marks a notification as read.
     * 
     * @param id the identifier of the notification
     * @return the updated notification
     */
    Notification markAsRead(Long id);

    /**
     * Finds all notifications for a specific company.
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
