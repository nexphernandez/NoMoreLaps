package com.nomorelaps.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.adapters.out.persistence.interfaces.INotificationPersistenceAdapter;
import com.nomorelaps.domain.models.Notification;

/**
 * Unit tests for NotificationService.
 * Verifies business logic for notifications, including read status management
 * and creation defaults.
 */
@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private INotificationPersistenceAdapter persistencePort;

    @InjectMocks
    private NotificationService notificationService;

    private Notification testNotification;

    @BeforeEach
    void setUp() {
        testNotification = new Notification();
        testNotification.setId(1L);
        testNotification.setIsRead(false);
        testNotification.setMessage("Test Alert");
    }

    @Test
    @DisplayName("create - Should set createdAt if null and save")
    void shouldCreateWithTimestamp() {
        testNotification.setCreatedAt(null);
        when(persistencePort.save(any(Notification.class))).thenAnswer(i -> i.getArguments()[0]);

        Notification result = notificationService.create(testNotification);

        assertNotNull(result.getCreatedAt());
        verify(persistencePort).save(testNotification);
    }

    @Test
    @DisplayName("markAsRead - Should update status to read")
    void shouldMarkSingleAsRead() {
        when(persistencePort.findById(1L)).thenReturn(Optional.of(testNotification));
        when(persistencePort.save(any(Notification.class))).thenAnswer(i -> i.getArguments()[0]);

        Notification result = notificationService.markAsRead(1L);

        assertTrue(result.getIsRead());
        verify(persistencePort).save(testNotification);
    }

    @Test
    @DisplayName("markAsRead - Should throw exception if notification not found")
    void shouldThrowIfNotFoundOnMark() {
        when(persistencePort.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> notificationService.markAsRead(99L));
    }

    @Test
    @DisplayName("markAllAsRead - Should update all unread notifications for company")
    void shouldMarkAllUnreadAsRead() {
        Notification n1 = new Notification(); n1.setIsRead(false);
        Notification n2 = new Notification(); n2.setIsRead(true); // Already read
        Notification n3 = new Notification(); n3.setIsRead(false);

        when(persistencePort.findByCompanyId(10L)).thenReturn(List.of(n1, n2, n3));

        notificationService.markAllAsRead(10L);

        assertTrue(n1.getIsRead());
        assertTrue(n2.getIsRead());
        assertTrue(n3.getIsRead());
        verify(persistencePort, times(2)).save(any(Notification.class)); // Only n1 and n3 were updated
    }

    @Test
    @DisplayName("findByCompanyId - Should return notifications for company")
    void shouldReturnNotificationsByCompanyId() {
        when(persistencePort.findByCompanyId(10L)).thenReturn(List.of(testNotification));
        List<Notification> result = notificationService.findByCompanyId(10L);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("deleteById - Should call persistence port")
    void shouldDeleteById() {
        notificationService.deleteById(1L);
        verify(persistencePort).deleteById(1L);
    }
}
