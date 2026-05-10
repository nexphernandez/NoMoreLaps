package com.nomorelaps.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.*;
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

import com.nomorelaps.adapters.mapper.NotificationMapper;
import com.nomorelaps.adapters.out.persistence.jpa.NotificationJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.NotificationJpaRepository;
import com.nomorelaps.domain.models.Notification;

/**
 * Unit tests for NotificationPersistenceAdapter.
 * Verifies persistence logic for Notification, including mapping and repository interaction.
 */
@ExtendWith(MockitoExtension.class)
class NotificationPersistenceAdapterTest {

    @Mock
    private NotificationJpaRepository repository;

    @Mock
    private NotificationMapper mapper;

    @InjectMocks
    private NotificationPersistenceAdapter adapter;

    private Notification testNotification;
    private NotificationJpaEntity testEntity;

    @BeforeEach
    void setUp() {
        testNotification = new Notification(1L);
        testEntity = new NotificationJpaEntity();
        testEntity.setId(1L);
    }

    @Test
    @DisplayName("save - Should map to entity, save and return domain")
    void shouldSaveNotification() {
        when(mapper.toJpaEntity(testNotification)).thenReturn(testEntity);
        when(repository.save(testEntity)).thenReturn(testEntity);
        when(mapper.toDomain(testEntity)).thenReturn(testNotification);

        Notification result = adapter.save(testNotification);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository).save(testEntity);
    }

    @Test
    @DisplayName("findById - Should return domain object when found")
    void shouldReturnNotificationWhenIdExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(testEntity));
        when(mapper.toDomain(testEntity)).thenReturn(testNotification);

        Optional<Notification> result = adapter.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    @DisplayName("findById - Should return empty when not found")
    void shouldReturnEmptyWhenIdDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<Notification> result = adapter.findById(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("findByCompanyId - Should return list of notifications")
    void shouldReturnNotificationsByCompanyId() {
        Long companyId = 10L;
        when(repository.findByCompanyIdOrderByCreatedAtDesc(companyId)).thenReturn(List.of(testEntity));
        when(mapper.toDomain(testEntity)).thenReturn(testNotification);

        List<Notification> result = adapter.findByCompanyId(companyId);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("findAll - Should return all domain objects")
    void shouldReturnAllNotifications() {
        when(repository.findAll()).thenReturn(List.of(testEntity));
        when(mapper.toDomain(testEntity)).thenReturn(testNotification);

        List<Notification> result = adapter.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("deleteById - Should call repository delete")
    void shouldDeleteNotificationById() {
        Long id = 10L;
        adapter.deleteById(id);
        verify(repository).deleteById(id);
    }
}
