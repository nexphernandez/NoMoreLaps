package com.nomorelaps.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.adapters.mapper.DynamicPriceMapper;
import com.nomorelaps.adapters.out.persistence.jpa.DynamicPriceJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.DynamicPriceJpaRepository;
import com.nomorelaps.domain.models.DynamicPrice;
import com.nomorelaps.domain.models.Parking;

@ExtendWith(MockitoExtension.class)
class DynamicPricePersistenceAdapterTest {

    @Mock
    private DynamicPriceJpaRepository repository;

    @Mock
    private DynamicPriceMapper mapper;

    @InjectMocks
    private DynamicPricePersistenceAdapter adapter;

    private DynamicPrice price;
    private DynamicPriceJpaEntity entity;

    @BeforeEach
    void setUp() {
        price = new DynamicPrice(1L);
        entity = new DynamicPriceJpaEntity();
        entity.setId(1L);
    }

    @Test
    @DisplayName("findByParkingId - Should return list")
    void shouldReturnByParkingId() {
        when(repository.findByParkingId(1L)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(price);

        List<DynamicPrice> result = adapter.findByParkingId(1L);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("save - Should map parking in toEntity")
    void shouldSaveWithParking() {
        // Arrange
        Parking parking = new Parking(10L);
        price.setParking(parking);

        when(mapper.toJpaEntity(price)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(price);

        // Act
        adapter.save(price);

        // Assert
        assertNotNull(entity.getParking());
        assertEquals(10L, entity.getParking().getId());
    }

    @Test
    @DisplayName("toDomain - Should map parking from entity")
    void shouldMapParkingToDomain() {
        // Arrange
        ParkingJpaEntity parkingEntity = new ParkingJpaEntity(10L);
        entity.setParking(parkingEntity);

        when(repository.findByParkingId(1L)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(price);

        // Act
        List<DynamicPrice> result = adapter.findByParkingId(1L);

        // Assert
        assertFalse(result.isEmpty());
        assertNotNull(result.get(0).getParking());
        assertEquals(10L, result.get(0).getParking().getId());
    }

    @Test
    @DisplayName("toEntity/toDomain - Should handle null parking or null parking ID")
    void shouldHandleNullParkingOrId() {
        // Case 1: Parking is null (already tested, but grouping here)
        price.setParking(null);
        entity.setParking(null);
        when(mapper.toJpaEntity(price)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(price);
        
        adapter.save(price);
        assertNull(entity.getParking());

        // Case 2: Parking is NOT null but ID IS null (The missing branch)
        Parking parkingNoId = new Parking();
        parkingNoId.setId(null);
        price.setParking(parkingNoId);
        
        adapter.save(price);
        assertNull(entity.getParking(), "Should not map parking if ID is null");
    }
}

