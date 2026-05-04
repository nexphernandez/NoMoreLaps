package com.nomorelaps.adapters.in.soap;

import com.nomorelaps.adapters.in.api.SmartGeocodeRequest;
import com.nomorelaps.adapters.in.api.SmartGeocodeResponse;
import com.nomorelaps.adapters.in.api.SmartRecommendationRequest;
import com.nomorelaps.adapters.in.api.SmartRecommendationResponse;
import com.nomorelaps.business.SmartCalendarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SmartCalendarSoapServiceTest {

    private SmartCalendarService smartCalendarService;
    private SmartCalendarSoapService smartCalendarSoapService;

    @BeforeEach
    void setUp() {
        smartCalendarService = mock(SmartCalendarService.class);
        smartCalendarSoapService = new SmartCalendarSoapService(smartCalendarService);
    }

    @Test
    @DisplayName("geocode - Should return response")
    void shouldReturnGeocode() {
        SmartGeocodeRequest request = new SmartGeocodeRequest();
        request.setQuery("Madrid");

        SmartGeocodeResponse response = new SmartGeocodeResponse();
        response.setLatitude(40.0);

        when(smartCalendarService.geocode("Madrid")).thenReturn(response);

        SmartGeocodeResponse result = smartCalendarSoapService.geocode(request);

        assertNotNull(result);
        assertEquals(40.0, result.getLatitude());
        verify(smartCalendarService, times(1)).geocode("Madrid");
    }

    @Test
    @DisplayName("geocode - Should throw exception on null input")
    void shouldThrowOnNullGeocodeInput() {
        assertThrows(IllegalArgumentException.class, () -> smartCalendarSoapService.geocode(null));
        
        SmartGeocodeRequest request = new SmartGeocodeRequest();
        assertThrows(IllegalArgumentException.class, () -> smartCalendarSoapService.geocode(request));
    }

    @Test
    @DisplayName("recommend - Should return response")
    void shouldReturnRecommend() {
        SmartRecommendationRequest request = new SmartRecommendationRequest();
        SmartRecommendationResponse response = new SmartRecommendationResponse();
        
        when(smartCalendarService.recommend(request)).thenReturn(response);

        SmartRecommendationResponse result = smartCalendarSoapService.recommend(request);

        assertNotNull(result);
        verify(smartCalendarService, times(1)).recommend(request);
    }

    @Test
    @DisplayName("recommend - Should throw exception on null input")
    void shouldThrowOnNullRecommendInput() {
        assertThrows(IllegalArgumentException.class, () -> smartCalendarSoapService.recommend(null));
    }
}
