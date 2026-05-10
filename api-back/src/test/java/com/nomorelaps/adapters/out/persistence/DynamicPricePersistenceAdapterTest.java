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

/**
 * Unit tests for DynamicPricePersistenceAdapter.
 * Verifies persistence logic for DynamicPrice, including mapping and repository interaction.
 */
@ExtendWith(MockitoExtension.class)
class DynamicPricePersistenceAdapterTest {

    @Mock
    private DynamicPriceJpaRepository repository;

    @Mock
    private DynamicPriceMapper mapper;

    @InjectMocks
    private DynamicPricePersistenceAdapter adapter;

    private DynamicPrice testPrice;
    private DynamicPriceJpaEntity testEntity;

    @BeforeEach
    void setUp() {
        testPrice = new DynamicPrice(1L);
        testEntity = new DynamicPriceJpaEntity();
        testEntity.setId(1L);
    }

    @Test
    @DisplayName("findByParkingId - Should return list of dynamic prices")
    void shouldReturnByParkingId() {
        Long parkingId = 100L;
        when(repository.findByParkingId(parkingId)).thenReturn(List.of(testEntity));
        when(mapper.toDomain(testEntity)).thenReturn(testPrice);

        List<DynamicPrice> result = adapter.findByParkingId(parkingId);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    @DisplayName("findByParkingId - Should return empty list when no prices found")
    void shouldReturnEmptyListWhenNoPricesFound() {
        Long parkingId = 999L;
        when(repository.findByParkingId(parkingId)).thenReturn(List.of());

        List<DynamicPrice> result = adapter.findByParkingId(parkingId);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("toEntity - Should map User with valid ID to UserJpaEntity")
    void toEntityShouldMapParkingWithId() {
        Parking parking = new Parking(10L);
        testPrice.setParking(parking);
        when(mapper.toJpaEntity(testPrice)).thenReturn(testEntity);

        DynamicPriceJpaEntity result = adapter.toEntity(testPrice);

        assertNotNull(result);
        assertNotNull(result.getParking());
        assertEquals(10L, result.getParking().getId());
    }

    @Test
    @DisplayName("toEntity - Should handle null parking")
    void toEntityShouldHandleNullParking() {
        testPrice.setParking(null);
        when(mapper.toJpaEntity(testPrice)).thenReturn(testEntity);

        DynamicPriceJpaEntity result = adapter.toEntity(testPrice);

        assertNotNull(result);
        assertNull(result.getParking());
    }

    @Test
    @DisplayName("toEntity - Should handle parking with null ID")
    void toEntityShouldHandleParkingWithNullId() {
        testPrice.setParking(new Parking()); // ID is null
        when(mapper.toJpaEntity(testPrice)).thenReturn(testEntity);

        DynamicPriceJpaEntity result = adapter.toEntity(testPrice);

        assertNotNull(result);
        assertNull(result.getParking());
    }

    @Test
    @DisplayName("toDomain - Should map ParkingJpaEntity with valid ID to domain Parking")
    void toDomainShouldMapParkingWithId() {
        ParkingJpaEntity parkingEntity = new ParkingJpaEntity(20L);
        testEntity.setParking(parkingEntity);
        when(mapper.toDomain(testEntity)).thenReturn(testPrice);

        DynamicPrice result = adapter.toDomain(testEntity);

        assertNotNull(result);
        assertNotNull(result.getParking());
        assertEquals(20L, result.getParking().getId());
    }

    @Test
    @DisplayName("toDomain - Should handle JPA entity with null Parking")
    void toDomainShouldHandleNullParking() {
        testEntity.setParking(null);
        when(mapper.toDomain(testEntity)).thenReturn(testPrice);

        DynamicPrice result = adapter.toDomain(testEntity);

        assertNotNull(result);
        assertNull(result.getParking());
    }
}
