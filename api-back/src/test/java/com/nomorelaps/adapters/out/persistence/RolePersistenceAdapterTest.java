package com.nomorelaps.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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

@ExtendWith(MockitoExtension.class)
class RolePersistenceAdapterTest {

    @Mock
    private RoleJpaRepository repository;

    @Mock
    private RoleMapper mapper;

    @InjectMocks
    private RolePersistenceAdapter adapter;

    private Role role;
    private RoleJpaEntity entity;

    @BeforeEach
    void setUp() {
        role = new Role(1L);
        entity = new RoleJpaEntity();
        entity.setId(1L);
    }

    @Test
    @DisplayName("findById - Should delegate and map")
    void shouldFindById() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(role);

        Optional<Role> result = adapter.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    @DisplayName("deleteById - Should call repository deleteById")
    void shouldDeleteById() {
        adapter.deleteById(1L);
        org.mockito.Mockito.verify(repository, org.mockito.Mockito.times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("save - Should map to entity, save, and map back")
    void shouldSave() {
        when(mapper.toJpaEntity(role)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(role);

        Role savedRole = adapter.save(role);

        assertNotNull(savedRole);
        assertEquals(1L, savedRole.getId());
    }

    @Test
    @DisplayName("findAll - Should return mapped domain list")
    void shouldFindAll() {
        when(repository.findAll()).thenReturn(java.util.Collections.singletonList(entity));
        when(mapper.toDomain(entity)).thenReturn(role);

        java.util.List<Role> roles = adapter.findAll();

        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertEquals(1L, roles.get(0).getId());
    }
}

