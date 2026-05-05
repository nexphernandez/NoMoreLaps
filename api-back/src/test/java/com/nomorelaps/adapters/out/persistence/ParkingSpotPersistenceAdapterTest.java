package com.nomorelaps.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.adapters.mapper.ParkingSpotMapper;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingSpotJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.ParkingSpotJpaRepository;
import com.nomorelaps.domain.models.Parking;
import com.nomorelaps.domain.models.ParkingSpot;

@ExtendWith(MockitoExtension.class)
class ParkingSpotPersistenceAdapterTest {

    @Mock
    private ParkingSpotJpaRepository repository;

    @Mock
    private ParkingSpotMapper mapper;

    @InjectMocks
    private ParkingSpotPersistenceAdapter adapter;

    private ParkingSpot spot;
    private ParkingSpotJpaEntity entity;

    @BeforeEach
    void setUp() {
        spot = new ParkingSpot(1L);
        entity = new ParkingSpotJpaEntity();
        entity.setId(1L);
    }

    @Test
    @DisplayName("findByParkingId - Should return list")
    void shouldReturnByParkingId() {
        when(repository.findByParkingId(1L)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(spot);

        List<ParkingSpot> result = adapter.findByParkingId(1L);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("findByParkingIdAndStateTrue - Should return list")
    void shouldReturnAvailableByParkingId() {
        when(repository.findByParkingIdAndStateTrue(1L)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(spot);

        List<ParkingSpot> result = adapter.findByParkingIdAndStateTrue(1L);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("toEntity - Should preserve parking reference from domain")
    void shouldPreserveParkingFromDomain() {
        spot.setParking(new Parking(10L));
        when(mapper.toJpaEntity(spot)).thenReturn(entity);
        
        // Save calls toEntity
        when(repository.save(any())).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(spot);
        
        adapter.save(spot);
        assertNotNull(entity.getParking());
        assertEquals(10L, entity.getParking().getId());
    }

    @Test
    @DisplayName("toEntity - Should preserve parking reference from database if missing in domain")
    void shouldPreserveParkingFromDb() {
        spot.setParking(null); // Explicitly null
        when(mapper.toJpaEntity(spot)).thenReturn(entity);
        
        ParkingSpotJpaEntity existing = new ParkingSpotJpaEntity();
        existing.setParking(new ParkingJpaEntity(20L));
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        
        when(repository.save(any())).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(spot);
        
        adapter.save(spot);
        assertNotNull(entity.getParking());
        assertEquals(20L, entity.getParking().getId());
    }

    @Test
    @DisplayName("toEntity - Should skip parking lookup when both parking and id are null")
    void shouldHandleNullParkingAndNullId() {
        ParkingSpot spotNoId = new ParkingSpot();
        spotNoId.setId(null);
        spotNoId.setParking(null);

        ParkingSpotJpaEntity entityNoParking = new ParkingSpotJpaEntity();
        when(mapper.toJpaEntity(spotNoId)).thenReturn(entityNoParking);
        when(repository.save(any())).thenReturn(entityNoParking);
        when(mapper.toDomain(entityNoParking)).thenReturn(spotNoId);

        ParkingSpot saved = adapter.save(spotNoId);
        assertNotNull(saved);
        assertNull(entityNoParking.getParking());
    }

    @Test
    @DisplayName("toEntity - Should skip parking when parking ID is null")
    void shouldHandleNullParkingId() {
        // Arrange
        spot.setParking(new Parking()); // ID is null
        when(mapper.toJpaEntity(spot)).thenReturn(entity);
        when(repository.save(any())).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(spot);

        // Act
        adapter.save(spot);

        // Assert
        // Since spot has an ID (1L from setUp), it will go to the 'else if' 
        // and try to find existing parking in DB. In this test, findById(1L) 
        // is not stubbed to return anything, so it won't set parking.
        assertNull(entity.getParking());
    }
}

