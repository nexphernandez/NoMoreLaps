package com.nomorelaps.adapters.in.soap;

import com.nomorelaps.adapters.in.api.DynamicPriceResponse;
import com.nomorelaps.adapters.mapper.DynamicPriceMapper;
import com.nomorelaps.business.interfaces.IDynamicPriceService;
import com.nomorelaps.domain.models.DynamicPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DynamicPriceSoapServiceTest {

    private IDynamicPriceService dynamicPriceService;
    private DynamicPriceMapper dynamicPriceMapper;
    private DynamicPriceSoapService dynamicPriceSoapService;

    @BeforeEach
    void setUp() {
        dynamicPriceService = mock(IDynamicPriceService.class);
        dynamicPriceMapper = mock(DynamicPriceMapper.class);
        dynamicPriceSoapService = new DynamicPriceSoapService(dynamicPriceService, dynamicPriceMapper);
    }

    @Test
    @DisplayName("findAll - Should return list of dynamic price responses")
    void shouldReturnListOfDynamicPriceResponses() {
        DynamicPrice price = new DynamicPrice(1L);
        DynamicPriceResponse response = new DynamicPriceResponse();
        response.setId(1L);

        when(dynamicPriceService.findAll()).thenReturn(Collections.singletonList(price));
        when(dynamicPriceMapper.toResponse(price)).thenReturn(response);

        List<DynamicPriceResponse> result = dynamicPriceSoapService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());

        verify(dynamicPriceService, times(1)).findAll();
        verify(dynamicPriceMapper, times(1)).toResponse(price);
    }

    @Test
    @DisplayName("findById - Should return dynamic price response when found")
    void shouldReturnDynamicPriceResponseWhenFound() {
        DynamicPrice price = new DynamicPrice(1L);
        DynamicPriceResponse response = new DynamicPriceResponse();
        response.setId(1L);

        when(dynamicPriceService.findById(1L)).thenReturn(Optional.of(price));
        when(dynamicPriceMapper.toResponse(price)).thenReturn(response);

        DynamicPriceResponse result = dynamicPriceSoapService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(dynamicPriceService, times(1)).findById(1L);
        verify(dynamicPriceMapper, times(1)).toResponse(price);
    }

    @Test
    @DisplayName("findById - Should return null when not found")
    void shouldReturnNullWhenNotFound() {
        when(dynamicPriceService.findById(1L)).thenReturn(Optional.empty());

        DynamicPriceResponse result = dynamicPriceSoapService.findById(1L);

        assertNull(result);

        verify(dynamicPriceService, times(1)).findById(1L);
        verify(dynamicPriceMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("findByParkingId - Should return list of responses")
    void shouldReturnFindByParkingId() {
        DynamicPrice price = new DynamicPrice(1L);
        DynamicPriceResponse response = new DynamicPriceResponse();
        response.setId(1L);

        when(dynamicPriceService.findByParkingId(2L)).thenReturn(Collections.singletonList(price));
        when(dynamicPriceMapper.toResponse(price)).thenReturn(response);

        List<DynamicPriceResponse> result = dynamicPriceSoapService.findByParkingId(2L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(dynamicPriceService, times(1)).findByParkingId(2L);
    }
}
