package com.nomorelaps.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

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

@ExtendWith(MockitoExtension.class)
class NotificationPersistenceAdapterTest {

    @Mock
    private NotificationJpaRepository repository;

    @Mock
    private NotificationMapper mapper;

    @InjectMocks
    private NotificationPersistenceAdapter adapter;

    @Test
    @DisplayName("findById - Should return domain object when found")
    void shouldFindById() {
        NotificationJpaEntity entity = new NotificationJpaEntity();
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(new Notification(1L));

        Optional<Notification> result = adapter.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    @DisplayName("findAll - Should return list of domain objects")
    void shouldFindAll() {
        NotificationJpaEntity entity = new NotificationJpaEntity();
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(new Notification(1L));

        List<Notification> results = adapter.findAll();

        assertEquals(1, results.size());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("deleteById - Should call repository delete")
    void shouldDeleteById() {
        adapter.deleteById(10L);
        verify(repository).deleteById(10L);
    }
}
