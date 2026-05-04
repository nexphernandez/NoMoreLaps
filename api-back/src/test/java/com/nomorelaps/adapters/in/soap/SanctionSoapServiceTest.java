package com.nomorelaps.adapters.in.soap;

import com.nomorelaps.adapters.in.api.SanctionResponse;
import com.nomorelaps.adapters.mapper.SanctionMapper;
import com.nomorelaps.business.interfaces.ISanctionService;
import com.nomorelaps.domain.models.Sanction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SanctionSoapServiceTest {

    private ISanctionService sanctionService;
    private SanctionMapper sanctionMapper;
    private SanctionSoapService sanctionSoapService;

    @BeforeEach
    void setUp() {
        sanctionService = mock(ISanctionService.class);
        sanctionMapper = mock(SanctionMapper.class);
        sanctionSoapService = new SanctionSoapService(sanctionService, sanctionMapper);
    }

    @Test
    @DisplayName("findByUserId - Should return list")
    void shouldReturnFindByUserId() {
        Sanction sanction = new Sanction(1L);
        SanctionResponse response = new SanctionResponse();
        response.setId(1L);

        when(sanctionService.findByUserId(2L)).thenReturn(Collections.singletonList(sanction));
        when(sanctionMapper.toResponse(sanction)).thenReturn(response);

        List<SanctionResponse> result = sanctionSoapService.findByUserId(2L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(sanctionService, times(1)).findByUserId(2L);
    }

    @Test
    @DisplayName("findByReservationId - Should return list")
    void shouldReturnFindByReservationId() {
        Sanction sanction = new Sanction(1L);
        SanctionResponse response = new SanctionResponse();
        response.setId(1L);

        when(sanctionService.findByReservationId(2L)).thenReturn(Collections.singletonList(sanction));
        when(sanctionMapper.toResponse(sanction)).thenReturn(response);

        List<SanctionResponse> result = sanctionSoapService.findByReservationId(2L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(sanctionService, times(1)).findByReservationId(2L);
    }

    @Test
    @DisplayName("pay - Should pay and return response")
    void shouldPayAndReturnResponse() {
        Sanction sanction = new Sanction(1L);
        SanctionResponse response = new SanctionResponse();
        response.setId(1L);

        when(sanctionService.paySanction(2L)).thenReturn(sanction);
        when(sanctionMapper.toResponse(sanction)).thenReturn(response);

        SanctionResponse result = sanctionSoapService.pay(2L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(sanctionService, times(1)).paySanction(2L);
    }
}
