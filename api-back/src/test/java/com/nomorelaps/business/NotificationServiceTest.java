package com.nomorelaps.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.adapters.out.persistence.interfaces.INotificationPersistenceAdapter;
import com.nomorelaps.domain.models.Notification;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private INotificationPersistenceAdapter persistencePort;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    @DisplayName("markAsRead - Should set isRead to true and save")
    void shouldMarkAsRead() {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setIsRead(false);

        when(persistencePort.findById(1L)).thenReturn(Optional.of(notification));
        when(persistencePort.save(any(Notification.class))).thenAnswer(i -> i.getArguments()[0]);

        Notification result = notificationService.markAsRead(1L);

        assertTrue(result.getIsRead());
        verify(persistencePort, times(1)).save(notification);
    }

    @Test
    @DisplayName("markAsRead - Should throw exception if not found")
    void shouldThrowExceptionWhenNotificationNotFound() {
        when(persistencePort.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> notificationService.markAsRead(1L));
    }

    @Test
    @DisplayName("markAllAsRead - Should mark all company notifications as read")
    void shouldMarkAllAsRead() {
        Notification n1 = new Notification();
        n1.setIsRead(false);
        Notification n2 = new Notification();
        n2.setIsRead(true);
        Notification n3 = new Notification();
        n3.setIsRead(false);

        List<Notification> list = Arrays.asList(n1, n2, n3);
        when(persistencePort.findByCompanyId(10L)).thenReturn(list);

        notificationService.markAllAsRead(10L);

        assertTrue(n1.getIsRead());
        assertTrue(n2.getIsRead());
        assertTrue(n3.getIsRead());
        verify(persistencePort, times(2)).save(any(Notification.class));
    }

    @Test
    @DisplayName("deleteById - Should call persistencePort")
    void shouldDeleteById() {
        doNothing().when(persistencePort).deleteById(1L);
        notificationService.deleteById(1L);
        verify(persistencePort, times(1)).deleteById(1L);
    }
}
