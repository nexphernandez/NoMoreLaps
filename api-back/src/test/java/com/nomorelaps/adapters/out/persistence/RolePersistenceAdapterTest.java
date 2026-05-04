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
}
