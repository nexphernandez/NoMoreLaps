package com.nomorelaps.adapters.in.soap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import com.nomorelaps.adapters.in.api.DynamicPriceResponse;
import com.nomorelaps.adapters.mapper.DynamicPriceMapper;
import com.nomorelaps.business.interfaces.IDynamicPriceService;
import com.nomorelaps.domain.models.DynamicPrice;

/**
 * Unit tests for DynamicPriceSoapService.
 * Validates the retrieval of dynamic pricing information via SOAP adapter.
 * 
 * @author nexphernandez
 * @version 1.1.0
 */
@SpringBootTest
class DynamicPriceSoapServiceTest {

    @MockitoBean
    private IDynamicPriceService dynamicPriceService;

    @MockitoSpyBean
    private DynamicPriceMapper dynamicPriceMapper;

    @Autowired
    private DynamicPriceSoapService dynamicPriceSoapService;

    private DynamicPrice samplePrice;

    @BeforeEach
    void setUp() {
        samplePrice = new DynamicPrice(1L);
        samplePrice.setMinPrice(1.0);
        samplePrice.setMaxPrice(2.0);
    }

    @Test
    @DisplayName("findAll - Success: Should return list of all dynamic prices")
    void shouldReturnAllDynamicPricesSuccessfully() {
        when(dynamicPriceService.findAll()).thenReturn(List.of(samplePrice));

        List<DynamicPriceResponse> result = dynamicPriceSoapService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1.0, result.get(0).getMinPrice());
        verify(dynamicPriceService).findAll();
    }

    @Test
    @DisplayName("findById - Success: Should return dynamic price details when found")
    void shouldReturnDynamicPriceByIdSuccessfully() {
        when(dynamicPriceService.findById(1L)).thenReturn(Optional.of(samplePrice));

        DynamicPriceResponse result = dynamicPriceSoapService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1.0, result.getMinPrice());
        verify(dynamicPriceService).findById(1L);
    }

    @Test
    @DisplayName("findById - Failure: Should return null when dynamic price does not exist")
    void shouldReturnNullWhenDynamicPriceNotFound() {
        when(dynamicPriceService.findById(99L)).thenReturn(Optional.empty());

        DynamicPriceResponse result = dynamicPriceSoapService.findById(99L);

        assertNull(result);
        verify(dynamicPriceService).findById(99L);
        verify(dynamicPriceMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("findByParkingId - Success: Should return prices associated with a parking")
    void shouldReturnPricesByParkingIdSuccessfully() {
        when(dynamicPriceService.findByParkingId(10L)).thenReturn(List.of(samplePrice));

        List<DynamicPriceResponse> result = dynamicPriceSoapService.findByParkingId(10L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(dynamicPriceService).findByParkingId(10L);
    }
}
