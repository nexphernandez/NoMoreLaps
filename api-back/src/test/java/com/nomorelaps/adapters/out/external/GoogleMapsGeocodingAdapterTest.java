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

@ExtendWith(MockitoExtension.class)
class GoogleMapsGeocodingAdapterTest {

    @Mock
    private IParkingService parkingService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GoogleMapsGeocodingAdapter adapter;

    private Parking localParking;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(adapter, "restTemplate", restTemplate);
        
        localParking = new Parking(1L);
        localParking.setAddress("Local Street 1");
        localParking.setLatitude(40.0);
        localParking.setLongitude(-3.0);
    }

    @Test
    @DisplayName("Should fallback to local when API key is null")
    void shouldFallbackWhenKeyIsNull() {
        ReflectionTestUtils.setField(adapter, "googleMapsApiKey", null);
        when(parkingService.searchByNameOrAddress("query")).thenReturn(List.of(localParking));

        SmartGeocodeResponse response = adapter.geocode("query");

        assertEquals("Local Street 1", response.getFormattedAddress());
        assertEquals(40.0, response.getLatitude());
        assertEquals(-3.0, response.getLongitude());
        verify(restTemplate, never()).getForObject(anyString(), eq(Map.class));
    }

    @Test
    @DisplayName("Should fallback to local when API key is blank")
    void shouldFallbackWhenKeyIsBlank() {
        ReflectionTestUtils.setField(adapter, "googleMapsApiKey", "   ");
        when(parkingService.searchByNameOrAddress("query")).thenReturn(List.of(localParking));

        SmartGeocodeResponse response = adapter.geocode("query");

        assertEquals("Local Street 1", response.getFormattedAddress());
    }

    @Test
    @DisplayName("Should parse successful API response")
    void shouldParseSuccessfulResponse() {
        ReflectionTestUtils.setField(adapter, "googleMapsApiKey", "fake-key");
        
        Map<String, Object> location = Map.of("lat", 41.0, "lng", -4.0);
        Map<String, Object> geometry = Map.of("location", location);
        Map<String, Object> result = Map.of(
            "formatted_address", "API Street 2",
            "geometry", geometry
        );
        Map<String, Object> apiResponse = Map.of("results", List.of(result));

        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(apiResponse);

        SmartGeocodeResponse response = adapter.geocode("Madrid");

        assertEquals("API Street 2", response.getFormattedAddress());
        assertEquals(41.0, response.getLatitude());
        assertEquals(-4.0, response.getLongitude());
    }

    @Test
    @DisplayName("Should fallback when API response is null")
    void shouldFallbackWhenResponseNull() {
        ReflectionTestUtils.setField(adapter, "googleMapsApiKey", "fake-key");
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(null);
        when(parkingService.searchByNameOrAddress("query")).thenReturn(List.of(localParking));

        SmartGeocodeResponse response = adapter.geocode("query");

        assertEquals("Local Street 1", response.getFormattedAddress());
    }

    @Test
    @DisplayName("Should fallback when API response has no results key")
    void shouldFallbackWhenNoResultsKey() {
        ReflectionTestUtils.setField(adapter, "googleMapsApiKey", "fake-key");
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(Collections.emptyMap());
        when(parkingService.searchByNameOrAddress("query")).thenReturn(List.of(localParking));

        SmartGeocodeResponse response = adapter.geocode("query");

        assertEquals("Local Street 1", response.getFormattedAddress());
    }

    @Test
    @DisplayName("Should fallback when API response results are empty")
    void shouldFallbackWhenResultsEmpty() {
        ReflectionTestUtils.setField(adapter, "googleMapsApiKey", "fake-key");
        Map<String, Object> apiResponse = Map.of("results", Collections.emptyList());
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(apiResponse);
        when(parkingService.searchByNameOrAddress("query")).thenReturn(List.of(localParking));

        SmartGeocodeResponse response = adapter.geocode("query");

        assertEquals("Local Street 1", response.getFormattedAddress());
    }

    @Test
    @DisplayName("Should fallback when API throws exception")
    void shouldFallbackWhenExceptionThrown() {
        ReflectionTestUtils.setField(adapter, "googleMapsApiKey", "fake-key");
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenThrow(new RuntimeException("API down"));
        when(parkingService.searchByNameOrAddress("query")).thenReturn(List.of(localParking));

        SmartGeocodeResponse response = adapter.geocode("query");

        assertEquals("Local Street 1", response.getFormattedAddress());
    }

    @Test
    @DisplayName("Should throw exception when local fallback is empty")
    void shouldThrowExceptionWhenFallbackEmpty() {
        ReflectionTestUtils.setField(adapter, "googleMapsApiKey", null);
        when(parkingService.searchByNameOrAddress("query")).thenReturn(Collections.emptyList());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> adapter.geocode("query"));
        assertEquals("Destination could not be resolved locally or via API.", ex.getMessage());
    }
}
