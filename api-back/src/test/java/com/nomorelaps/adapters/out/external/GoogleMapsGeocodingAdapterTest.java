package com.nomorelaps.adapters.out.external;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.nomorelaps.adapters.in.api.SmartGeocodeResponse;
import com.nomorelaps.business.interfaces.IParkingService;
import com.nomorelaps.domain.models.Parking;

/**
 * Unit tests for GoogleMapsGeocodingAdapter.
 * Verifies external API interaction and local fallback logic for geocoding queries.
 */
@ExtendWith(MockitoExtension.class)
class GoogleMapsGeocodingAdapterTest {

    @Mock
    private IParkingService parkingService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GoogleMapsGeocodingAdapter adapter;

    private Parking testParking;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(adapter, "restTemplate", restTemplate);
        
        testParking = new Parking(1L);
        testParking.setAddress("Calle Mayor 1");
        testParking.setLatitude(40.4167);
        testParking.setLongitude(-3.7033);
    }

    @Test
    @DisplayName("geocode - Should return API result when API key is valid and response is successful")
    void shouldReturnApiResultWhenKeyIsValid() {
        ReflectionTestUtils.setField(adapter, "googleMapsApiKey", "valid-api-key");
        
        Map<String, Object> location = Map.of("lat", 41.3851, "lng", 2.1734);
        Map<String, Object> geometry = Map.of("location", location);
        Map<String, Object> result = Map.of(
            "formatted_address", "Barcelona, Spain",
            "geometry", geometry
        );
        Map<String, Object> apiResponse = Map.of("results", List.of(result));

        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(apiResponse);

        SmartGeocodeResponse response = adapter.geocode("Barcelona");

        assertNotNull(response);
        assertEquals("Barcelona, Spain", response.getFormattedAddress());
        assertEquals(41.3851, response.getLatitude());
        assertEquals(2.1734, response.getLongitude());
    }

    @Test
    @DisplayName("geocode - Should fallback to local search when API key is missing")
    void shouldFallbackWhenApiKeyIsMissing() {
        ReflectionTestUtils.setField(adapter, "googleMapsApiKey", null);
        when(parkingService.searchByNameOrAddress("Madrid")).thenReturn(List.of(testParking));

        SmartGeocodeResponse response = adapter.geocode("Madrid");

        assertNotNull(response);
        assertEquals("Calle Mayor 1", response.getFormattedAddress());
        verify(restTemplate, never()).getForObject(anyString(), any());
    }

    @Test
    @DisplayName("geocode - Should fallback to local search when API key is blank")
    void shouldFallbackWhenApiKeyIsBlank() {
        ReflectionTestUtils.setField(adapter, "googleMapsApiKey", "  ");
        when(parkingService.searchByNameOrAddress("Madrid")).thenReturn(List.of(testParking));

        SmartGeocodeResponse response = adapter.geocode("Madrid");

        assertNotNull(response);
        assertEquals("Calle Mayor 1", response.getFormattedAddress());
    }

    @Test
    @DisplayName("geocode - Should fallback to local search when API response is null")
    void shouldFallbackWhenApiResponseIsNull() {
        ReflectionTestUtils.setField(adapter, "googleMapsApiKey", "some-key");
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(null);
        when(parkingService.searchByNameOrAddress("Madrid")).thenReturn(List.of(testParking));

        SmartGeocodeResponse response = adapter.geocode("Madrid");

        assertEquals("Calle Mayor 1", response.getFormattedAddress());
    }

    @Test
    @DisplayName("geocode - Should fallback to local search when API results are empty")
    void shouldFallbackWhenApiResultsAreEmpty() {
        ReflectionTestUtils.setField(adapter, "googleMapsApiKey", "some-key");
        Map<String, Object> apiResponse = Map.of("results", Collections.emptyList());
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(apiResponse);
        when(parkingService.searchByNameOrAddress("Madrid")).thenReturn(List.of(testParking));

        SmartGeocodeResponse response = adapter.geocode("Madrid");

        assertEquals("Calle Mayor 1", response.getFormattedAddress());
    }

    @Test
    @DisplayName("geocode - Should fallback to local search when API results are missing in response")
    void shouldFallbackWhenResultsAreMissing() {
        ReflectionTestUtils.setField(adapter, "googleMapsApiKey", "some-key");
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(Collections.emptyMap());
        when(parkingService.searchByNameOrAddress("Madrid")).thenReturn(List.of(testParking));

        SmartGeocodeResponse response = adapter.geocode("Madrid");

        assertEquals("Calle Mayor 1", response.getFormattedAddress());
    }

    @Test
    @DisplayName("geocode - Should fallback to local search when API call throws exception")
    void shouldFallbackWhenApiThrowsException() {
        ReflectionTestUtils.setField(adapter, "googleMapsApiKey", "some-key");
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenThrow(new RuntimeException("API Error"));
        when(parkingService.searchByNameOrAddress("Madrid")).thenReturn(List.of(testParking));

        SmartGeocodeResponse response = adapter.geocode("Madrid");

        assertEquals("Calle Mayor 1", response.getFormattedAddress());
    }

    @Test
    @DisplayName("geocode - Should throw exception when both API and local search fail")
    void shouldThrowExceptionWhenBothFail() {
        ReflectionTestUtils.setField(adapter, "googleMapsApiKey", null);
        when(parkingService.searchByNameOrAddress("Unknown")).thenReturn(Collections.emptyList());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> adapter.geocode("Unknown"));
        assertEquals("Destination could not be resolved locally or via API.", ex.getMessage());
    }
}
