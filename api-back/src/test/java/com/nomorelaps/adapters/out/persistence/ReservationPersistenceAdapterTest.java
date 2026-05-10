package com.nomorelaps.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.adapters.mapper.ReservationMapper;
import com.nomorelaps.adapters.mapper.SanctionMapper;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingSpotJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.ReservationJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.SanctionJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.ReservationJpaRepository;
import com.nomorelaps.domain.models.ParkingSpot;
import com.nomorelaps.domain.models.Reservation;
import com.nomorelaps.domain.models.Sanction;
import com.nomorelaps.domain.models.User;

/**
 * Unit tests for ReservationPersistenceAdapter.
 * Verifies all branches of manual mapping and repository delegations with granular tests.
 */
@ExtendWith(MockitoExtension.class)
class ReservationPersistenceAdapterTest {

    @Mock
    private ReservationJpaRepository repository;

    @Mock
    private ReservationMapper mapper;

    @Mock
    private SanctionMapper sanctionMapper;

    @InjectMocks
    private ReservationPersistenceAdapter adapter;

    private Reservation domain;
    private ReservationJpaEntity entity;

    @BeforeEach
    void setUp() {
        domain = new Reservation(1L);
        entity = new ReservationJpaEntity(1L);
    }

    @Test
    @DisplayName("toEntity - Should map User when present with ID")
    void toEntityWithUser() {
        User user = new User(); user.setId(10L);
        domain.setUser(user);
        when(mapper.toJpaEntity(domain)).thenReturn(entity);
        assertEquals(10L, adapter.toEntity(domain).getUser().getId());
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
    @DisplayName("toEntity - Should map ParkingSpot when present with ID")
    void toEntityWithSpot() {
        ParkingSpot spot = new ParkingSpot(); spot.setId(20L);
        domain.setParkingSpot(spot);
        when(mapper.toJpaEntity(domain)).thenReturn(entity);
        assertEquals(20L, adapter.toEntity(domain).getParkingSpot().getId());
    }

    @Test
    @DisplayName("toEntity - Should not map ParkingSpot when null or ID null")
    void toEntityWithSpotNull() {
        domain.setParkingSpot(null);
        when(mapper.toJpaEntity(domain)).thenReturn(entity);
        assertNull(adapter.toEntity(domain).getParkingSpot());
        
        domain.setParkingSpot(new ParkingSpot());
        assertNull(adapter.toEntity(domain).getParkingSpot());
    }

    @Test
    @DisplayName("toEntity - Should map Sanctions when present")
    void toEntityWithSanctions() {
        Sanction sanction = new Sanction(30L);
        domain.setSanctions(Set.of(sanction));
        when(mapper.toJpaEntity(domain)).thenReturn(entity);
        when(sanctionMapper.toJpaEntity(sanction)).thenReturn(new SanctionJpaEntity(30L));
        assertEquals(1, adapter.toEntity(domain).getSanctions().size());
    }

    @Test
    @DisplayName("toEntity - Should handle null Sanctions")
    void toEntityWithSanctionsNull() {
        domain.setSanctions(null);
        when(mapper.toJpaEntity(domain)).thenReturn(entity);
        assertNull(adapter.toEntity(domain).getSanctions());
    }

    @Test
    @DisplayName("toDomain - Should map User when present in entity")
    void toDomainWithUser() {
        UserJpaEntity uJpa = new UserJpaEntity(10L); uJpa.setName("User");
        entity.setUser(uJpa);
        when(mapper.toDomain(entity)).thenReturn(domain);
        assertEquals(10L, adapter.toDomain(entity).getUser().getId());
    }

    @Test
    @DisplayName("toDomain - Should handle null User in entity")
    void toDomainWithUserNull() {
        entity.setUser(null);
        when(mapper.toDomain(entity)).thenReturn(domain);
        assertNull(adapter.toDomain(entity).getUser());
    }

    @Test
    @DisplayName("toDomain - Should map ParkingSpot and nested Parking")
    void toDomainWithParkingChain() {
        ParkingJpaEntity pJpa = new ParkingJpaEntity(50L); pJpa.setName("P");
        ParkingSpotJpaEntity sJpa = new ParkingSpotJpaEntity(100L); sJpa.setParking(pJpa);
        entity.setParkingSpot(sJpa);
        when(mapper.toDomain(entity)).thenReturn(domain);
        assertEquals(50L, adapter.toDomain(entity).getParkingSpot().getParking().getId());
    }

    @Test
    @DisplayName("toDomain - Should handle null Parking in chain")
    void toDomainWithParkingNull() {
        ParkingSpotJpaEntity sJpa = new ParkingSpotJpaEntity(100L); // Parking is null
        entity.setParkingSpot(sJpa);
        when(mapper.toDomain(entity)).thenReturn(domain);
        assertNull(adapter.toDomain(entity).getParkingSpot().getParking());
    }

    @Test
    @DisplayName("toDomain - Should map Sanctions when present")
    void toDomainWithSanctions() {
        SanctionJpaEntity sJpa = new SanctionJpaEntity(5L);
        entity.setSanctions(Set.of(sJpa));
        when(mapper.toDomain(entity)).thenReturn(domain);
        when(sanctionMapper.toDomain(sJpa)).thenReturn(new Sanction(5L));
        assertEquals(1, adapter.toDomain(entity).getSanctions().size());
    }

    @Test
    @DisplayName("findByUserId - Should delegate and map results")
    void findByUserId() {
        when(repository.findByUserId(10L)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);
        List<Reservation> results = adapter.findByUserId(10L);
        assertEquals(1, results.size());
        verify(repository).findByUserId(10L);
    }

    @Test
    @DisplayName("findByParkingSpotId - Should delegate and map results")
    void findByParkingSpotId() {
        when(repository.findByParkingSpotId(20L)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);
        List<Reservation> results = adapter.findByParkingSpotId(20L);
        assertEquals(1, results.size());
        verify(repository).findByParkingSpotId(20L);
    }

    @Test
    @DisplayName("findByParkingId - Should delegate and map results")
    void findByParkingId() {
        when(repository.findByParkingSpotParkingId(50L)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);
        List<Reservation> results = adapter.findByParkingId(50L);
        assertEquals(1, results.size());
        verify(repository).findByParkingSpotParkingId(50L);
    }

    @Test
    @DisplayName("hasOverlappingReservations - Should return true if not empty")
    void hasOverlappingTrue() {
        when(repository.findByParkingSpotIdAndStateAndStartTimeBeforeAndEndTimeAfter(anyLong(), anyString(), any(), any()))
            .thenReturn(List.of(new ReservationJpaEntity()));
        assertTrue(adapter.hasOverlappingReservations(1L, LocalDateTime.now(), LocalDateTime.now()));
    }

    @Test
    @DisplayName("hasOverlappingReservations - Should return false if empty")
    void hasOverlappingFalse() {
        when(repository.findByParkingSpotIdAndStateAndStartTimeBeforeAndEndTimeAfter(anyLong(), anyString(), any(), any()))
            .thenReturn(Collections.emptyList());
        assertFalse(adapter.hasOverlappingReservations(1L, LocalDateTime.now(), LocalDateTime.now()));
    }

    @Test
    @DisplayName("hasOverlappingExcluding - Should return true if not empty")
    void hasOverlappingExcludingTrue() {
        when(repository.findByParkingSpotIdAndStateAndStartTimeBeforeAndEndTimeAfterAndIdNot(anyLong(), anyString(), any(), any(), anyLong()))
            .thenReturn(List.of(new ReservationJpaEntity()));
        assertTrue(adapter.hasOverlappingReservationsExcluding(1L, LocalDateTime.now(), LocalDateTime.now(), 1L));
    }

    @Test
    @DisplayName("hasOverlappingExcluding - Should return false if empty")
    void hasOverlappingExcludingFalse() {
        when(repository.findByParkingSpotIdAndStateAndStartTimeBeforeAndEndTimeAfterAndIdNot(anyLong(), anyString(), any(), any(), anyLong()))
            .thenReturn(Collections.emptyList());
        assertFalse(adapter.hasOverlappingReservationsExcluding(1L, LocalDateTime.now(), LocalDateTime.now(), 1L));
    }
}
