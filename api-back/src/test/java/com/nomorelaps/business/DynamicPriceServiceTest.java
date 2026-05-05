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

@ExtendWith(MockitoExtension.class)
class DynamicPriceServiceTest {

    @Mock
    private IDynamicPricePersistenceAdapter persistencePort;

    @InjectMocks
    private DynamicPriceService dynamicPriceService;

    private DynamicPrice validPrice;

    @BeforeEach
    void setUp() {
        validPrice = new DynamicPrice();
        validPrice.setMinPrice(1.0);
        validPrice.setMaxPrice(5.0);
    }

    @Test
    @DisplayName("Should find dynamic price by id")
    void shouldFindById() {
        when(persistencePort.findById(1L)).thenReturn(Optional.of(validPrice));
        Optional<DynamicPrice> found = dynamicPriceService.findById(1L);
        assertTrue(found.isPresent());
        assertEquals(validPrice, found.get());
    }

    @Test
    @DisplayName("Should find all dynamic prices")
    void shouldFindAll() {
        when(persistencePort.findAll()).thenReturn(List.of(validPrice));
        List<DynamicPrice> found = dynamicPriceService.findAll();
        assertEquals(1, found.size());
    }

    @Test
    @DisplayName("Should find dynamic prices by parking id")
    void shouldFindByParkingId() {
        when(persistencePort.findByParkingId(1L)).thenReturn(List.of(validPrice));
        List<DynamicPrice> found = dynamicPriceService.findByParkingId(1L);
        assertEquals(1, found.size());
    }

    @Test
    @DisplayName("Should update dynamic price")
    void shouldUpdate() {
        when(persistencePort.save(any(DynamicPrice.class))).thenReturn(validPrice);
        DynamicPrice updated = dynamicPriceService.update(validPrice);
        assertNotNull(updated);
        verify(persistencePort).save(validPrice);
    }

    @Test
    @DisplayName("Should delete dynamic price by id")
    void shouldDeleteById() {
        doNothing().when(persistencePort).deleteById(1L);
        dynamicPriceService.deleteById(1L);
        verify(persistencePort).deleteById(1L);
    }

    @Test
    @DisplayName("Should create dynamic price when valid")
    void shouldCreateWhenValid() {
        when(persistencePort.save(any(DynamicPrice.class))).thenReturn(validPrice);
        DynamicPrice created = dynamicPriceService.create(validPrice);
        assertNotNull(created);
        verify(persistencePort).save(validPrice);
    }

    @Test
    @DisplayName("Should throw exception when min price is greater than max price")
    void shouldThrowExceptionWhenMinPriceIsInvalid() {
        validPrice.setMinPrice(10.0);
        validPrice.setMaxPrice(5.0);
        assertThrows(IllegalArgumentException.class, () -> dynamicPriceService.create(validPrice));
        verify(persistencePort, never()).save(any());
    }
}
