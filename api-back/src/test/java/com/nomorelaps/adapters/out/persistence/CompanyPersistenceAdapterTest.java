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

import com.nomorelaps.adapters.mapper.CompanyMapper;
import com.nomorelaps.adapters.out.persistence.jpa.CompanyJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.CompanyJpaRepository;
import com.nomorelaps.domain.models.Company;

@ExtendWith(MockitoExtension.class)
class CompanyPersistenceAdapterTest {

    @Mock
    private CompanyJpaRepository repository;

    @Mock
    private CompanyMapper mapper;

    @InjectMocks
    private CompanyPersistenceAdapter adapter;

    private Company company;
    private CompanyJpaEntity entity;

    @BeforeEach
    void setUp() {
        company = new Company(1L);
        entity = new CompanyJpaEntity();
        entity.setId(1L);
    }

    @Test
    @DisplayName("findByEmail - Should return company")
    void shouldReturnByEmail() {
        when(repository.findByEmail("corp@test.com")).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(company);

        Optional<Company> result = adapter.findByEmail("corp@test.com");

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    @DisplayName("findByApiKey - Should return company")
    void shouldReturnByApiKey() {
        when(repository.findByApiKey("api-key")).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(company);

        Optional<Company> result = adapter.findByApiKey("api-key");

        assertTrue(result.isPresent());
    }
}
