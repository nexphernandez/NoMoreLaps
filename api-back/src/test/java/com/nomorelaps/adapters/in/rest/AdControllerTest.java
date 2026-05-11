package com.nomorelaps.adapters.in.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nomorelaps.adapters.in.rest.dto.AdResponseDTO;
import com.nomorelaps.infrastructure.odoo.OdooClient;

/**
 * Unit tests for AdController.
 * Adheres to the New Backend Test Refactoring Plan for granularity and business naming.
 * Verifies the integration with OdooClient and response mapping logic.
 */
@ExtendWith(MockitoExtension.class)
class AdControllerTest {

    @Mock
    private OdooClient odooClient;

    @InjectMocks
    private AdController adController;

    private Map<String, Object> validAd;

    @BeforeEach
    void setUp() {
        validAd = new HashMap<>();
        validAd.put("name", "Promo 2026");
        validAd.put("ad_type", "banner");
        validAd.put("image_url", "http://odoo.com/img.png");
        validAd.put("target_url", "http://odoo.com/promo");
    }

    @Test
    @DisplayName("getActiveAds - Success: Should return list of mapped DTOs")
    void getActiveAds_Success_ShouldReturnMappedList() throws Exception {
        when(odooClient.authenticate()).thenReturn(1);
        when(odooClient.getActiveAds(1)).thenReturn(List.of(validAd));

        ResponseEntity<List<AdResponseDTO>> response = adController.getActiveAds();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Promo 2026", response.getBody().get(0).name());
    }

    @Test
    @DisplayName("getActiveAds - Odoo Exception: Should return 500 Internal Server Error")
    void getActiveAds_Exception_ShouldReturn500() throws Exception {
        when(odooClient.authenticate()).thenThrow(new RuntimeException("Odoo Connection Failed"));

        ResponseEntity<List<AdResponseDTO>> response = adController.getActiveAds();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @DisplayName("getActiveAds - Null Values in Map: Should map to empty strings")
    void getActiveAds_NullValues_ShouldMapToEmptyStrings() throws Exception {
        Map<String, Object> adWithNulls = new HashMap<>();
        adWithNulls.put("name", null);
        adWithNulls.put("ad_type", false); // MapStruct/Odoo often returns false for empty strings
        
        when(odooClient.authenticate()).thenReturn(1);
        when(odooClient.getActiveAds(1)).thenReturn(List.of(adWithNulls));

        ResponseEntity<List<AdResponseDTO>> response = adController.getActiveAds();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        AdResponseDTO dto = response.getBody().get(0);
        assertEquals("", dto.name());
        assertEquals("", dto.adType());
    }

    @Test
    @DisplayName("getActiveAds - Empty List: Should return OK with empty list")
    void getActiveAds_EmptyList_ShouldReturnOk() throws Exception {
        when(odooClient.authenticate()).thenReturn(1);
        when(odooClient.getActiveAds(1)).thenReturn(Collections.emptyList());

        ResponseEntity<List<AdResponseDTO>> response = adController.getActiveAds();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }
}
