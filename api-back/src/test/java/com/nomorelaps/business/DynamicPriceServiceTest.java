package com.nomorelaps.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.adapters.out.persistence.interfaces.IDynamicPricePersistenceAdapter;
import com.nomorelaps.domain.models.DynamicPrice;

/**
 * Unit tests for DynamicPriceService.
 * Verifies business logic for dynamic pricing, including range validation.
 */
@ExtendWith(MockitoExtension.class)
class DynamicPriceServiceTest {

    @Mock
    private IDynamicPricePersistenceAdapter persistencePort;

    @InjectMocks
    private DynamicPriceService dynamicPriceService;

    private DynamicPrice testPrice;

    @BeforeEach
    void setUp() {
        testPrice = new DynamicPrice();
        testPrice.setId(1L);
        testPrice.setMinPrice(1.0);
        testPrice.setMaxPrice(5.0);
    }

    @Test
    @DisplayName("create - Should save dynamic price when range is valid")
    void shouldCreateWhenPriceRangeIsValid() {
        when(persistencePort.save(testPrice)).thenReturn(testPrice);

        DynamicPrice result = dynamicPriceService.create(testPrice);

        assertNotNull(result);
        verify(persistencePort).save(testPrice);
    }

    @Test
    @DisplayName("create - Should throw exception when min price > max price")
    void shouldThrowWhenMinPriceIsGreaterThanMax() {
        testPrice.setMinPrice(10.0);
        testPrice.setMaxPrice(5.0);

        assertThrows(IllegalArgumentException.class, () -> dynamicPriceService.create(testPrice));
        verify(persistencePort, never()).save(any());
    }

    @Test
    @DisplayName("findById - Should return price if exists")
    void shouldReturnPriceById() {
        when(persistencePort.findById(1L)).thenReturn(Optional.of(testPrice));
        assertTrue(dynamicPriceService.findById(1L).isPresent());
    }

    @Test
    @DisplayName("findAll - Should return all prices")
    void shouldReturnAllPrices() {
        when(persistencePort.findAll()).thenReturn(List.of(testPrice));
        assertFalse(dynamicPriceService.findAll().isEmpty());
    }

    @Test
    @DisplayName("findByParkingId - Should return prices for parking")
    void shouldReturnPricesByParkingId() {
        when(persistencePort.findByParkingId(55L)).thenReturn(List.of(testPrice));
        assertEquals(1, dynamicPriceService.findByParkingId(55L).size());
    }

    @Test
    @DisplayName("update - Should call persistence port")
    void shouldUpdatePrice() {
        when(persistencePort.save(testPrice)).thenReturn(testPrice);
        DynamicPrice result = dynamicPriceService.update(testPrice);
        assertNotNull(result);
        verify(persistencePort).save(testPrice);
    }

    @Test
    @DisplayName("deleteById - Should call persistence port")
    void shouldDeleteById() {
        dynamicPriceService.deleteById(1L);
        verify(persistencePort).deleteById(1L);
    }
}
