package com.nomorelaps.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.adapters.mapper.SanctionMapper;
import com.nomorelaps.adapters.out.persistence.jpa.SanctionJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.SanctionJpaRepository;
import com.nomorelaps.domain.models.Sanction;

@ExtendWith(MockitoExtension.class)
class SanctionPersistenceAdapterTest {

    @Mock
    private SanctionJpaRepository repository;

    @Mock
    private SanctionMapper mapper;

    @InjectMocks
    private SanctionPersistenceAdapter adapter;

    private Sanction sanction;
    private SanctionJpaEntity entity;

    @BeforeEach
    void setUp() {
        sanction = new Sanction(1L);
        entity = new SanctionJpaEntity();
        entity.setId(1L);
    }

    @Test
    @DisplayName("findByUserId - Should return list")
    void shouldReturnByUserId() {
        when(repository.findByUserId(1L)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(sanction);

        List<Sanction> result = adapter.findByUserId(1L);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("findByReservationId - Should return list")
    void shouldReturnByReservationId() {
        when(repository.findByReservationId(1L)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(sanction);

        List<Sanction> result = adapter.findByReservationId(1L);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("save - Should map associations in toEntity")
    void shouldSaveWithAssociations() {
        // Arrange
        com.nomorelaps.domain.models.User user = new com.nomorelaps.domain.models.User(10L);
        com.nomorelaps.domain.models.Reservation res = new com.nomorelaps.domain.models.Reservation(20L);
        sanction.setUser(user);
        sanction.setReservation(res);

        when(mapper.toJpaEntity(sanction)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(sanction);

        // Act
        adapter.save(sanction);

        // Assert
        assertNotNull(entity.getUser());
        assertEquals(10L, entity.getUser().getId());
        assertNotNull(entity.getReservation());
        assertEquals(20L, entity.getReservation().getId());
    }

    @Test
    @DisplayName("toDomain - Should map associations from entity")
    void shouldMapAssociationsToDomain() {
        // Arrange
        com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity userEntity = 
            new com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity(10L);
        com.nomorelaps.adapters.out.persistence.jpa.ReservationJpaEntity resEntity = 
            new com.nomorelaps.adapters.out.persistence.jpa.ReservationJpaEntity(20L);
        
        entity.setUser(userEntity);
        entity.setReservation(resEntity);

        when(repository.findByUserId(1L)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(sanction);

        // Act
        List<Sanction> result = adapter.findByUserId(1L);

        // Assert
        assertFalse(result.isEmpty());
        Sanction resultSanction = result.get(0);
        assertNotNull(resultSanction.getUser());
        assertEquals(10L, resultSanction.getUser().getId());
        assertNotNull(resultSanction.getReservation());
        assertEquals(20L, resultSanction.getReservation().getId());
    }

    @Test
    @DisplayName("toEntity/toDomain - Should handle null associations")
    void shouldHandleNullAssociations() {
        // Arrange
        sanction.setUser(null);
        sanction.setReservation(null);
        entity.setUser(null);
        entity.setReservation(null);

        when(mapper.toJpaEntity(sanction)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(sanction);

        // Act
        Sanction saved = adapter.save(sanction);

        // Assert
        assertNotNull(saved);
        assertNull(entity.getUser());
        assertNull(entity.getReservation());
    }

    @Test
    @DisplayName("toEntity - Should handle null association IDs")
    void shouldHandleNullAssociationIds() {
        // Arrange
        com.nomorelaps.domain.models.User userNoId = new com.nomorelaps.domain.models.User();
        com.nomorelaps.domain.models.Reservation resNoId = new com.nomorelaps.domain.models.Reservation();
        sanction.setUser(userNoId);
        sanction.setReservation(resNoId);

        when(mapper.toJpaEntity(sanction)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(sanction);

        // Act
        adapter.save(sanction);

        // Assert
        assertNull(entity.getUser());
        assertNull(entity.getReservation());
    }
}

