package com.nomorelaps.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.adapters.mapper.ReservationMapper;
import com.nomorelaps.adapters.mapper.SanctionMapper;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingSpotJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.ReservationJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.ReservationJpaRepository;
import com.nomorelaps.domain.models.Reservation;
import com.nomorelaps.domain.models.User;
import com.nomorelaps.domain.models.ParkingSpot;

@ExtendWith(MockitoExtension.class)
class ReservationPersistenceAdapterMapperTest {

    @Mock
    private ReservationJpaRepository repository;

    @Mock
    private ReservationMapper mapper;

    @Mock
    private SanctionMapper sanctionMapper;

    @InjectMocks
    private ReservationPersistenceAdapter adapter;

    @Test
    @DisplayName("Should handle null user and spot on toEntity")
    void shouldHandleNullsOnToEntity() {
        Reservation domain = new Reservation(1L);

        ReservationJpaEntity entity = new ReservationJpaEntity();
        entity.setId(1L);
        when(mapper.toJpaEntity(domain)).thenReturn(entity);

        ReservationJpaEntity result = adapter.toEntity(domain);
        assertNull(result.getUser());
        assertNull(result.getParkingSpot());

        domain.setUser(new User()); 
        domain.setParkingSpot(new ParkingSpot()); 
        result = adapter.toEntity(domain);
        assertNull(result.getUser());
        assertNull(result.getParkingSpot());
    }

    @Test
    @DisplayName("Should handle null relations on toDomain")
    void shouldHandleNullsOnToDomain() {
        ReservationJpaEntity entity = new ReservationJpaEntity();
        entity.setId(1L);

        Reservation domain = new Reservation(1L);
        when(mapper.toDomain(entity)).thenReturn(domain);

        Reservation result = adapter.toDomain(entity);
        assertNull(result.getUser());
        assertNull(result.getParkingSpot());

        
        ParkingSpotJpaEntity spotEntity = new ParkingSpotJpaEntity();
        spotEntity.setId(2L);
        spotEntity.setParking(null);
        entity.setParkingSpot(spotEntity);

        result = adapter.toDomain(entity);
        assertNotNull(result.getParkingSpot());
        assertEquals(2L, result.getParkingSpot().getId());
        assertNull(result.getParkingSpot().getParking());
    }
}
