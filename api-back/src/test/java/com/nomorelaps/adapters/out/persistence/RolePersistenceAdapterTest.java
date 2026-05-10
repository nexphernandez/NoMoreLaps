package com.nomorelaps.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.adapters.mapper.RoleMapper;
import com.nomorelaps.adapters.out.persistence.jpa.RoleJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.RoleJpaRepository;
import com.nomorelaps.domain.models.Role;

/**
 * Unit tests for RolePersistenceAdapter.
 * Verifies persistence logic for Role, including mapping and repository interaction.
 */
@ExtendWith(MockitoExtension.class)
class RolePersistenceAdapterTest {

    @Mock
    private RoleJpaRepository repository;

    @Mock
    private RoleMapper mapper;

    @InjectMocks
    private RolePersistenceAdapter adapter;

    private Role testRole;
    private RoleJpaEntity testEntity;

    @BeforeEach
    void setUp() {
        testRole = new Role(1L);
        testRole.setName("ADMIN");
        testEntity = new RoleJpaEntity();
        testEntity.setId(1L);
        testEntity.setName("ADMIN");
    }

    @Test
    @DisplayName("save - Should map to entity, save and return domain")
    void shouldSaveRole() {
        when(mapper.toJpaEntity(testRole)).thenReturn(testEntity);
        when(repository.save(testEntity)).thenReturn(testEntity);
        when(mapper.toDomain(testEntity)).thenReturn(testRole);

        Role result = adapter.save(testRole);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository).save(testEntity);
    }

    @Test
    @DisplayName("findById - Should return domain object when found")
    void shouldReturnRoleWhenIdExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(testEntity));
        when(mapper.toDomain(testEntity)).thenReturn(testRole);

        Optional<Role> result = adapter.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    @DisplayName("findById - Should return empty when not found")
    void shouldReturnEmptyWhenIdDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<Role> result = adapter.findById(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("findAll - Should return list of domain objects")
    void shouldReturnAllRoles() {
        when(repository.findAll()).thenReturn(List.of(testEntity));
        when(mapper.toDomain(testEntity)).thenReturn(testRole);

        List<Role> result = adapter.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("deleteById - Should call repository delete")
    void shouldDeleteRoleById() {
        Long id = 10L;
        adapter.deleteById(id);
        verify(repository).deleteById(id);
    }
}
