package com.nomorelaps.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Collections;

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
import com.nomorelaps.domain.models.Sanction;
import com.nomorelaps.domain.models.User;
import com.nomorelaps.domain.models.Reservation;

@ExtendWith(MockitoExtension.class)
class SanctionPersistenceAdapterTest {

    @Mock
    private SanctionJpaRepository repository;

    @Mock
    private SanctionMapper mapper;

    @InjectMocks
    private SanctionPersistenceAdapter adapter;

    @Test
    @DisplayName("toEntity - Should map user and reservation IDs")
    void shouldMapIdsInToEntity() {
        Sanction domain = new Sanction();
        User user = new User(1L);
        domain.setUser(user);
        Reservation res = new Reservation(10L);
        domain.setReservation(res);

        SanctionJpaEntity entity = new SanctionJpaEntity();
        when(mapper.toJpaEntity(domain)).thenReturn(entity);

        SanctionJpaEntity result = adapter.toEntity(domain);

        assertNotNull(result.getUser());
        assertEquals(1L, result.getUser().getId());
        assertNotNull(result.getReservation());
        assertEquals(10L, result.getReservation().getId());
    }

    @Test
    @DisplayName("toEntity - Should handle partial nulls (objects exist but IDs are null)")
    void shouldHandlePartialNullsInToEntity() {
        Sanction domain = new Sanction();
        domain.setUser(new User(null));
        domain.setReservation(new Reservation(null));
        
        SanctionJpaEntity entity = new SanctionJpaEntity();
        when(mapper.toJpaEntity(domain)).thenReturn(entity);

        SanctionJpaEntity result = adapter.toEntity(domain);

        assertNull(result.getUser());
        assertNull(result.getReservation());
    }

    @Test
    @DisplayName("toEntity - Should handle full nulls (objects are null)")
    void shouldHandleFullNullsInToEntity() {
        Sanction domain = new Sanction();
        domain.setUser(null);
        domain.setReservation(null);
        
        SanctionJpaEntity entity = new SanctionJpaEntity();
        when(mapper.toJpaEntity(domain)).thenReturn(entity);

        SanctionJpaEntity result = adapter.toEntity(domain);

        assertNull(result.getUser());
        assertNull(result.getReservation());
    }

    @Test
    @DisplayName("toDomain - Should handle nested objects when present")
    void shouldMapNestedObjectsInToDomain() {
        SanctionJpaEntity entity = new SanctionJpaEntity();
        UserJpaEntity u = new UserJpaEntity(); u.setId(1L); u.setName("Test");
        entity.setUser(u);
        
        ReservationJpaEntity res = new ReservationJpaEntity();
        res.setId(10L);
        ParkingSpotJpaEntity spot = new ParkingSpotJpaEntity();
        spot.setId(20L);
        ParkingJpaEntity park = new ParkingJpaEntity();
        park.setId(30L);
        park.setName("Park Name");
        spot.setParking(park);
        res.setParkingSpot(spot);
        entity.setReservation(res);

        when(mapper.toDomain(entity)).thenReturn(new Sanction());

        Sanction domain = adapter.toDomain(entity);

        assertEquals(1L, domain.getUser().getId());
        assertEquals(20L, domain.getReservation().getParkingSpot().getId());
        assertEquals("Park Name", domain.getReservation().getParkingSpot().getParking().getName());
    }

    @Test
    @DisplayName("toDomain - Should handle partial nulls in nested chain")
    void shouldHandlePartialNullsInToDomain() {
        SanctionJpaEntity entity = new SanctionJpaEntity();
        ReservationJpaEntity res = new ReservationJpaEntity();
        res.setId(10L);
        
        // Case 1: ParkingSpot is null
        res.setParkingSpot(null);
        entity.setReservation(res);
        when(mapper.toDomain(entity)).thenReturn(new Sanction());
        Sanction result1 = adapter.toDomain(entity);
        assertNull(result1.getReservation().getParkingSpot());

        // Case 2: Parking is null
        ParkingSpotJpaEntity spot = new ParkingSpotJpaEntity();
        spot.setId(20L);
        spot.setParking(null);
        res.setParkingSpot(spot);
        Sanction result2 = adapter.toDomain(entity);
        assertNull(result2.getReservation().getParkingSpot());
    }

    @Test
    @DisplayName("findByUserId - Should return list of sanctions")
    void shouldFindByUserId() {
        when(repository.findByUserId(1L)).thenReturn(List.of(new SanctionJpaEntity()));
        when(mapper.toDomain(any())).thenReturn(new Sanction());

        List<Sanction> results = adapter.findByUserId(1L);

        assertEquals(1, results.size());
        verify(repository).findByUserId(1L);
    }

    @Test
    @DisplayName("findByReservationId - Should return list of sanctions")
    void shouldFindByReservationId() {
        when(repository.findByReservationId(10L)).thenReturn(List.of(new SanctionJpaEntity()));
        when(mapper.toDomain(any())).thenReturn(new Sanction());

        List<Sanction> results = adapter.findByReservationId(10L);

        assertEquals(1, results.size());
        verify(repository).findByReservationId(10L);
    }

    @Test
    @DisplayName("findByCompanyId - Should return list of sanctions")
    void shouldFindByCompanyId() {
        when(repository.findByReservationParkingSpotParkingCompanyId(100L)).thenReturn(List.of(new SanctionJpaEntity()));
        when(mapper.toDomain(any())).thenReturn(new Sanction());

        List<Sanction> results = adapter.findByCompanyId(100L);

        assertEquals(1, results.size());
        verify(repository).findByReservationParkingSpotParkingCompanyId(100L);
    }
}
