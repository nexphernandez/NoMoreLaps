package com.nomorelaps.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.adapters.mapper.ParkingMapper;
import com.nomorelaps.adapters.out.persistence.jpa.CompanyJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.ParkingJpaRepository;
import com.nomorelaps.domain.models.Company;
import com.nomorelaps.domain.models.Parking;

/**
 * Unit tests for ParkingPersistenceAdapter.
 * Verifies mapping logic and repository delegations with granular branch coverage.
 */
@ExtendWith(MockitoExtension.class)
class ParkingPersistenceAdapterTest {

    @Mock
    private ParkingJpaRepository repository;

    @Mock
    private ParkingMapper mapper;

    @InjectMocks
    private ParkingPersistenceAdapter adapter;

    private Parking domain;
    private ParkingJpaEntity entity;

    @BeforeEach
    void setUp() {
        domain = new Parking(1L);
        entity = new ParkingJpaEntity(1L);
    }

    @Test
    @DisplayName("toEntity - Should map company when present with ID")
    void toEntityWithCompany() {
        domain.setCompany(new Company(10L));
        when(mapper.toJpaEntity(domain)).thenReturn(entity);
        assertEquals(10L, adapter.toEntity(domain).getCompany().getId());
    }

    @Test
    @DisplayName("toEntity - Should not map company when null or ID null")
    void toEntityWithCompanyNull() {
        domain.setCompany(null);
        when(mapper.toJpaEntity(domain)).thenReturn(entity);
        assertNull(adapter.toEntity(domain).getCompany());
        
        domain.setCompany(new Company());
        assertNull(adapter.toEntity(domain).getCompany());
    }

    @Test
    @DisplayName("toDomain - Should restore company if present in entity with ID")
    void toDomainWithCompany() {
        entity.setCompany(new CompanyJpaEntity(10L));
        when(mapper.toDomain(entity)).thenReturn(domain);
        assertEquals(10L, adapter.toDomain(entity).getCompany().getId());
    }

    @Test
    @DisplayName("toDomain - Should not restore company if null or ID null in entity")
    void toDomainWithCompanyNull() {
        entity.setCompany(null);
        when(mapper.toDomain(entity)).thenReturn(domain);
        assertNull(adapter.toDomain(entity).getCompany());
        
        entity.setCompany(new CompanyJpaEntity(null));
        assertNull(adapter.toDomain(entity).getCompany());
    }

    @Test
    @DisplayName("findByCompanyId - Should delegate to repository and map results")
    void findByCompanyId() {
        when(repository.findByCompanyId(10L)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);
        List<Parking> results = adapter.findByCompanyId(10L);
        assertEquals(1, results.size());
        verify(repository).findByCompanyId(10L);
    }

    @Test
    @DisplayName("searchByNameOrAddress - Should delegate to repository and map results")
    void searchByNameOrAddress() {
        when(repository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase("test", "test"))
            .thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);
        List<Parking> results = adapter.searchByNameOrAddress("test");
        assertEquals(1, results.size());
        verify(repository).findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase("test", "test");
    }

    @Test
    @DisplayName("findNearby - Should calculate bounds and call repository")
    void findNearby() {
        when(repository.findByLatitudeBetweenAndLongitudeBetween(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
            .thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);
        List<Parking> results = adapter.findNearby(40.0, -3.0, 5.0);
        assertEquals(1, results.size());
        verify(repository).findByLatitudeBetweenAndLongitudeBetween(anyDouble(), anyDouble(), anyDouble(), anyDouble());
    }
}
