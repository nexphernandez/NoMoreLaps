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

/**
 * Unit tests for ParkingSpotPersistenceAdapter.
 * Verifies persistence logic for ParkingSpot, including relationship preservation during updates.
 */
@ExtendWith(MockitoExtension.class)
class ParkingSpotPersistenceAdapterTest {

    @Mock
    private ParkingSpotJpaRepository repository;

    @Mock
    private ParkingSpotMapper mapper;

    @InjectMocks
    private ParkingSpotPersistenceAdapter adapter;

    private ParkingSpot testSpot;
    private ParkingSpotJpaEntity testEntity;

    @BeforeEach
    void setUp() {
        testSpot = new ParkingSpot(1L);
        testEntity = new ParkingSpotJpaEntity();
        testEntity.setId(1L);
    }

    @Test
    @DisplayName("findByParkingId - Should return list of parking spots")
    void shouldReturnByParkingId() {
        Long parkingId = 10L;
        when(repository.findByParkingId(parkingId)).thenReturn(List.of(testEntity));
        when(mapper.toDomain(testEntity)).thenReturn(testSpot);

        List<ParkingSpot> result = adapter.findByParkingId(parkingId);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("findByParkingIdAndStateTrue - Should return list of available spots")
    void shouldReturnAvailableByParkingId() {
        Long parkingId = 10L;
        when(repository.findByParkingIdAndStateTrue(parkingId)).thenReturn(List.of(testEntity));
        when(mapper.toDomain(testEntity)).thenReturn(testSpot);

        List<ParkingSpot> result = adapter.findByParkingIdAndStateTrue(parkingId);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("toEntity - Should map Parking with valid ID to ParkingJpaEntity")
    void toEntityShouldMapParkingWithId() {
        testSpot.setParking(new Parking(55L));
        when(mapper.toJpaEntity(testSpot)).thenReturn(testEntity);

        ParkingSpotJpaEntity result = adapter.toEntity(testSpot);

        assertNotNull(result);
        assertNotNull(result.getParking());
        assertEquals(55L, result.getParking().getId());
    }

    @Test
    @DisplayName("toEntity - Should preserve Parking from existing entity when domain parking is null")
    void toEntityShouldPreserveExistingParkingFromDb() {
        testSpot.setParking(null); 
        when(mapper.toJpaEntity(testSpot)).thenReturn(testEntity);
        
        ParkingSpotJpaEntity existingEntity = new ParkingSpotJpaEntity();
        existingEntity.setParking(new ParkingJpaEntity(99L));
        when(repository.findById(1L)).thenReturn(Optional.of(existingEntity));

        ParkingSpotJpaEntity result = adapter.toEntity(testSpot);

        assertNotNull(result);
        assertNotNull(result.getParking());
        assertEquals(99L, result.getParking().getId());
    }

    @Test
    @DisplayName("toEntity - Should handle null Parking and null spot ID")
    void toEntityShouldHandleNullIdAndNullParking() {
        ParkingSpot spotNoId = new ParkingSpot();
        spotNoId.setId(null);
        spotNoId.setParking(null);

        ParkingSpotJpaEntity entityNoParking = new ParkingSpotJpaEntity();
        when(mapper.toJpaEntity(spotNoId)).thenReturn(entityNoParking);

        ParkingSpotJpaEntity result = adapter.toEntity(spotNoId);

        assertNotNull(result);
        assertNull(result.getParking());
    }

    @Test
    @DisplayName("toEntity - Should handle Parking with null ID")
    void toEntityShouldHandleParkingWithNullId() {
        testSpot.setParking(new Parking()); 
        when(mapper.toJpaEntity(testSpot)).thenReturn(testEntity);
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ParkingSpotJpaEntity result = adapter.toEntity(testSpot);

        assertNotNull(result);
        assertNull(result.getParking());
    }

    @Test
    @DisplayName("toDomain - Should map entity to domain via mapper")
    void toDomainShouldMapViaMapper() {
        when(mapper.toDomain(testEntity)).thenReturn(testSpot);

        ParkingSpot result = adapter.toDomain(testEntity);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
}
