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

import com.nomorelaps.adapters.mapper.SanctionMapper;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingSpotJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.ReservationJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.SanctionJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.SanctionJpaRepository;
import com.nomorelaps.domain.models.Reservation;
import com.nomorelaps.domain.models.Sanction;
import com.nomorelaps.domain.models.User;

/**
 * Unit tests for SanctionPersistenceAdapter.
 * Verifies mapping logic and repository delegations with granular branch coverage.
 */
@ExtendWith(MockitoExtension.class)
class SanctionPersistenceAdapterTest {

    @Mock
    private SanctionJpaRepository repository;

    @Mock
    private SanctionMapper mapper;

    @InjectMocks
    private SanctionPersistenceAdapter adapter;

    private Sanction domain;
    private SanctionJpaEntity entity;

    @BeforeEach
    void setUp() {
        domain = new Sanction(1L);
        entity = new SanctionJpaEntity(1L);
    }

    @Test
    @DisplayName("toEntity - Should map User when present with ID")
    void toEntityWithUser() {
        User user = new User(); user.setId(10L);
        domain.setUser(user);
        when(mapper.toJpaEntity(domain)).thenReturn(entity);
        SanctionJpaEntity result = adapter.toEntity(domain);
        assertEquals(10L, result.getUser().getId());
    }

    @Test
    @DisplayName("toEntity - Should not map User when null or ID null")
    void toEntityWithUserNull() {
        domain.setUser(null);
        when(mapper.toJpaEntity(domain)).thenReturn(entity);
        assertNull(adapter.toEntity(domain).getUser());
        
        domain.setUser(new User());
        assertNull(adapter.toEntity(domain).getUser());
    }

    @Test
    @DisplayName("toEntity - Should map Reservation when present with ID")
    void toEntityWithReservation() {
        Reservation res = new Reservation(); res.setId(20L);
        domain.setReservation(res);
        when(mapper.toJpaEntity(domain)).thenReturn(entity);
        SanctionJpaEntity result = adapter.toEntity(domain);
        assertEquals(20L, result.getReservation().getId());
    }

    @Test
    @DisplayName("toEntity - Should not map Reservation when null or ID null")
    void toEntityWithReservationNull() {
        domain.setReservation(null);
        when(mapper.toJpaEntity(domain)).thenReturn(entity);
        assertNull(adapter.toEntity(domain).getReservation());
        
        domain.setReservation(new Reservation());
        assertNull(adapter.toEntity(domain).getReservation());
    }

    @Test
    @DisplayName("toDomain - Should map User if present in entity")
    void toDomainWithUser() {
        UserJpaEntity uJpa = new UserJpaEntity(10L); uJpa.setName("Bob");
        entity.setUser(uJpa);
        when(mapper.toDomain(entity)).thenReturn(domain);
        assertEquals("Bob", adapter.toDomain(entity).getUser().getName());
    }

    @Test
    @DisplayName("toDomain - Should handle null User in entity")
    void toDomainWithUserNull() {
        entity.setUser(null);
        when(mapper.toDomain(entity)).thenReturn(domain);
        assertNull(adapter.toDomain(entity).getUser());
    }

    @Test
    @DisplayName("toDomain - Should map deep structure (Reservation->Spot->Parking)")
    void toDomainWithParkingChain() {
        ParkingJpaEntity pJpa = new ParkingJpaEntity(50L); pJpa.setName("P1");
        ParkingSpotJpaEntity sJpa = new ParkingSpotJpaEntity(100L); sJpa.setParking(pJpa);
        ReservationJpaEntity rJpa = new ReservationJpaEntity(200L); rJpa.setParkingSpot(sJpa);
        entity.setReservation(rJpa);
        when(mapper.toDomain(entity)).thenReturn(domain);
        Sanction result = adapter.toDomain(entity);
        assertNotNull(result.getReservation().getParkingSpot().getParking());
        assertEquals(50L, result.getReservation().getParkingSpot().getParking().getId());
    }

    @Test
    @DisplayName("toDomain - Should handle null Parking in Reservation chain")
    void toDomainWithParkingNull() {
        ReservationJpaEntity rJpa = new ReservationJpaEntity(200L);
        rJpa.setParkingSpot(new ParkingSpotJpaEntity(100L)); // Parking is null
        entity.setReservation(rJpa);
        when(mapper.toDomain(entity)).thenReturn(domain);
        assertNull(adapter.toDomain(entity).getReservation().getParkingSpot());
    }

    @Test
    @DisplayName("toDomain - Should handle null Spot in Reservation chain")
    void toDomainWithSpotNull() {
        ReservationJpaEntity rJpa = new ReservationJpaEntity(200L);
        rJpa.setParkingSpot(null);
        entity.setReservation(rJpa);
        when(mapper.toDomain(entity)).thenReturn(domain);
        assertNull(adapter.toDomain(entity).getReservation().getParkingSpot());
    }

    @Test
    @DisplayName("findByUserId - Should delegate and map results")
    void findByUserId() {
        when(repository.findByUserId(10L)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);
        List<Sanction> results = adapter.findByUserId(10L);
        assertEquals(1, results.size());
        verify(repository).findByUserId(10L);
    }

    @Test
    @DisplayName("findByReservationId - Should delegate and map results")
    void findByReservationId() {
        when(repository.findByReservationId(20L)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);
        List<Sanction> results = adapter.findByReservationId(20L);
        assertEquals(1, results.size());
        verify(repository).findByReservationId(20L);
    }

    @Test
    @DisplayName("findByCompanyId - Should delegate and map results")
    void findByCompanyId() {
        when(repository.findByReservationParkingSpotParkingCompanyId(5L)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);
        List<Sanction> results = adapter.findByCompanyId(5L);
        assertEquals(1, results.size());
        verify(repository).findByReservationParkingSpotParkingCompanyId(5L);
    }
}
