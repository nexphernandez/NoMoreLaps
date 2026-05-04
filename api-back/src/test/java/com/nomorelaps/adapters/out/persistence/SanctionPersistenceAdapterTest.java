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
}
