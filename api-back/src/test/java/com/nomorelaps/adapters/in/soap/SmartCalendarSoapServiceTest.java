package com.nomorelaps.adapters.in.soap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.nomorelaps.adapters.in.api.SmartGeocodeRequest;
import com.nomorelaps.adapters.in.api.SmartGeocodeResponse;
import com.nomorelaps.adapters.in.api.SmartRecommendationRequest;
import com.nomorelaps.adapters.in.api.SmartRecommendationResponse;
import com.nomorelaps.business.SmartCalendarService;

/**
 * Unit tests for SmartCalendarSoapService.
 * Validates geocoding and recommendation operations via SOAP adapter.
 * 
 * @author nexphernandez
 * @version 1.1.0
 */
@SpringBootTest
class SmartCalendarSoapServiceTest {

    @MockitoBean
    private SmartCalendarService smartCalendarService;

    @Autowired
    private SmartCalendarSoapService smartCalendarSoapService;

    private SmartGeocodeRequest validGeocodeRequest;
    private SmartRecommendationRequest validRecommendRequest;

    @BeforeEach
    void setUp() {
        validGeocodeRequest = new SmartGeocodeRequest();
        validGeocodeRequest.setQuery("Madrid, Spain");

        validRecommendRequest = new SmartRecommendationRequest();
        validRecommendRequest.setLatitude(40.4168);
        validRecommendRequest.setLongitude(-3.7038);
    }

    @Test
    @DisplayName("geocode - Success: Should return coordinates for a valid query")
    void shouldGeocodeSuccessfully() {
        SmartGeocodeResponse response = new SmartGeocodeResponse();
        response.setLatitude(40.4168);
        response.setLongitude(-3.7038);
        when(smartCalendarService.geocode("Madrid, Spain")).thenReturn(response);

        SmartGeocodeResponse result = smartCalendarSoapService.geocode(validGeocodeRequest);

        assertNotNull(result);
        assertEquals(40.4168, result.getLatitude());
        verify(smartCalendarService).geocode("Madrid, Spain");
    }

    @Test
    @DisplayName("geocode - Failure: Should throw exception when query is missing")
    void shouldThrowExceptionWhenGeocodeQueryIsNull() {
        SmartGeocodeRequest invalidRequest = new SmartGeocodeRequest();
        assertThrows(IllegalArgumentException.class, () -> smartCalendarSoapService.geocode(null));
        assertThrows(IllegalArgumentException.class, () -> smartCalendarSoapService.geocode(invalidRequest));
    }

    @Test
    @DisplayName("recommend - Success: Should return recommendations for a valid request")
    void shouldRecommendSuccessfully() {
        SmartRecommendationResponse response = new SmartRecommendationResponse();
        when(smartCalendarService.recommend(validRecommendRequest)).thenReturn(response);

        SmartRecommendationResponse result = smartCalendarSoapService.recommend(validRecommendRequest);

        assertNotNull(result);
        verify(smartCalendarService).recommend(validRecommendRequest);
    }

    @Test
    @DisplayName("recommend - Failure: Should throw exception when request is null")
    void shouldThrowExceptionWhenRecommendRequestIsNull() {
        assertThrows(IllegalArgumentException.class, () -> smartCalendarSoapService.recommend(null));
    }
}
