package com.nomorelaps.adapters.in.soap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import com.nomorelaps.adapters.in.api.SanctionResponse;
import com.nomorelaps.adapters.mapper.SanctionMapper;
import com.nomorelaps.business.interfaces.ISanctionService;
import com.nomorelaps.domain.models.Sanction;

/**
 * Unit tests for SanctionSoapService.
 * Validates the retrieval and payment of sanctions via SOAP adapter.
 * 
 * @author nexphernandez
 * @version 1.1.0
 */
@SpringBootTest
class SanctionSoapServiceTest {

    @MockitoBean
    private ISanctionService sanctionService;

    @MockitoSpyBean
    private SanctionMapper sanctionMapper;

    @Autowired
    private SanctionSoapService sanctionSoapService;

    private Sanction sampleSanction;

    @BeforeEach
    void setUp() {
        sampleSanction = new Sanction(1L);
        sampleSanction.setReason("Late return");
        sampleSanction.setAmount(15.0);
        sampleSanction.setPaid(false);
    }

    @Test
    @DisplayName("findByUserId - Success: Should return list of user sanctions")
    void shouldReturnSanctionsByUserIdSuccessfully() {
        when(sanctionService.findByUserId(5L)).thenReturn(List.of(sampleSanction));

        List<SanctionResponse> result = sanctionSoapService.findByUserId(5L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Late return", result.get(0).getReason());
        verify(sanctionService).findByUserId(5L);
    }

    @Test
    @DisplayName("findByReservationId - Success: Should return list of reservation sanctions")
    void shouldReturnSanctionsByReservationIdSuccessfully() {
        when(sanctionService.findByReservationId(10L)).thenReturn(List.of(sampleSanction));

        List<SanctionResponse> result = sanctionSoapService.findByReservationId(10L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(15.0, result.get(0).getAmount());
        verify(sanctionService).findByReservationId(10L);
    }

    @Test
    @DisplayName("pay - Success: Should mark sanction as paid and return response")
    void shouldPaySanctionSuccessfully() {
        sampleSanction.setPaid(true);
        when(sanctionService.paySanction(1L)).thenReturn(sampleSanction);

        SanctionResponse result = sanctionSoapService.pay(1L);

        assertNotNull(result);
        assertTrue(result.isPaid());
        verify(sanctionService).paySanction(1L);
    }
}
