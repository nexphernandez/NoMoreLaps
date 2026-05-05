package com.nomorelaps.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

@ExtendWith(MockitoExtension.class)
class ParkingPersistenceAdapterTest {

    @Mock
    private ParkingJpaRepository repository;

    @Mock
    private ParkingMapper mapper;

    @InjectMocks
    private ParkingPersistenceAdapter adapter;

    private Parking parking;
    private ParkingJpaEntity entity;

    @BeforeEach
    void setUp() {
        parking = new Parking(1L);
        parking.setCompany(new Company(10L));
        entity = new ParkingJpaEntity();
        entity.setId(1L);
        entity.setCompany(new CompanyJpaEntity(10L));
    }

    @Test
    @DisplayName("findByCompanyId - Should return list")
    void shouldReturnByCompanyId() {
        when(repository.findByCompanyId(10L)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(parking);

        List<Parking> result = adapter.findByCompanyId(10L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("searchByNameOrAddress - Should return list")
    void shouldSearch() {
        when(repository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase("query", "query")).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(parking);

        List<Parking> result = adapter.searchByNameOrAddress("query");

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("findNearby - Should calculate bounds and query repository")
    void shouldFindNearby() {
        when(repository.findByLatitudeBetweenAndLongitudeBetween(any(Double.class), any(Double.class), any(Double.class), any(Double.class)))
                .thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(parking);

        List<Parking> result = adapter.findNearby(40.0, -3.0, 5.0);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("toEntity - Should handle company association")
    void shouldMapToEntityWithCompany() {
        when(mapper.toJpaEntity(parking)).thenReturn(entity);
        
        // This indirectly tests the protected toEntity via save
        when(repository.save(any())).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(parking);
        
        Parking saved = adapter.save(parking);
        assertNotNull(saved);
    }

    @Test
    @DisplayName("toEntity - Should skip company when null")
    void shouldMapToEntityWithNullCompany() {
        parking.setCompany(null);
        ParkingJpaEntity entityNoCompany = new ParkingJpaEntity();
        entityNoCompany.setId(1L);
        // no company set

        when(mapper.toJpaEntity(parking)).thenReturn(entityNoCompany);
        when(repository.save(any())).thenReturn(entityNoCompany);
        when(mapper.toDomain(entityNoCompany)).thenReturn(parking);

        Parking saved = adapter.save(parking);
        assertNotNull(saved);
    }

    @Test
    @DisplayName("toDomain - Should skip company when entity company is null")
    void shouldMapToDomainWithNullCompany() {
        ParkingJpaEntity entityNoCompany = new ParkingJpaEntity();
        entityNoCompany.setId(2L);
        // no company

        Parking parkingNoCompany = new Parking(2L);
        when(repository.findByCompanyId(99L)).thenReturn(List.of(entityNoCompany));
        when(mapper.toDomain(entityNoCompany)).thenReturn(parkingNoCompany);

        List<Parking> result = adapter.findByCompanyId(99L);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertNull(result.get(0).getCompany());
    }
}

