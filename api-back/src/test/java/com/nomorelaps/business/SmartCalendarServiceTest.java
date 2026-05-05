package com.nomorelaps.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.adapters.in.api.SmartGeocodeResponse;
import com.nomorelaps.adapters.in.api.SmartRecommendationRequest;
import com.nomorelaps.adapters.in.api.SmartRecommendationResponse;
import com.nomorelaps.business.interfaces.IGeocodingProvider;
import com.nomorelaps.business.interfaces.IParkingService;
import com.nomorelaps.business.interfaces.IParkingSpotService;
import com.nomorelaps.business.interfaces.IReservationService;
import com.nomorelaps.domain.models.Parking;
import com.nomorelaps.domain.models.ParkingSpot;

@ExtendWith(MockitoExtension.class)
class SmartCalendarServiceTest {

    @Mock
    private IParkingService parkingService;
    @Mock
    private IParkingSpotService parkingSpotService;
    @Mock
    private IReservationService reservationService;
    @Mock
    private IGeocodingProvider geocodingProvider;

    @InjectMocks
    private SmartCalendarService smartCalendarService;

    private SmartRecommendationRequest request;

    @BeforeEach
    void setUp() {
        request = new SmartRecommendationRequest();
        request.setLatitude(40.416775);
        request.setLongitude(-3.703790);
        request.setStartTime(LocalDateTime.now().plusHours(1).toString());
        request.setDurationHours(2);
        request.setRadiusKm(5.0);
    }

    @Test
    @DisplayName("Should recommend parking when nearby spots are available")
    void shouldRecommendParking() {
        // Arrange
        Parking parking = new Parking(1L);
        parking.setName("Sol Parking");
        parking.setLatitude(40.417);
        parking.setLongitude(-3.704);
        parking.setPricePerHour(2.5);

        ParkingSpot spot = new ParkingSpot(100L);
        spot.setNumber(1);

        when(parkingService.findNearby(anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(Collections.singletonList(parking));
        when(parkingSpotService.findByParkingId(1L))
                .thenReturn(Collections.singletonList(spot));
        when(reservationService.hasOverlappingReservations(anyLong(), any(), any()))
                .thenReturn(false);

        // Act
        SmartRecommendationResponse response = smartCalendarService.recommend(request);

        // Assert
        assertNotNull(response);
        assertFalse(response.getSuggestions().isEmpty());
        assertEquals("Sol Parking", response.getSuggestions().get(0).getParkingName());
    }

    @Test
    @DisplayName("Should resolve coordinates from destination text if missing")
    void shouldGeocodeWhenCoordsMissing() {
        // Arrange
        request.setLatitude(null);
        request.setLongitude(null);
        request.setDestinationText("Puerta del Sol");

        SmartGeocodeResponse geocodeResponse = new SmartGeocodeResponse("Normalized Address", 40.0, -3.0);
        when(geocodingProvider.geocode("Puerta del Sol")).thenReturn(geocodeResponse);
        when(parkingService.findNearby(anyDouble(), anyDouble(), anyDouble())).thenReturn(Collections.emptyList());

        // Act
        smartCalendarService.recommend(request);

        // Assert
        verify(geocodingProvider).geocode("Puerta del Sol");
    }

    @Test
    @DisplayName("Should delegate geocode call to provider")
    void shouldGeocode() {
        SmartGeocodeResponse expected = new SmartGeocodeResponse("Address", 1.0, 2.0);
        when(geocodingProvider.geocode("query")).thenReturn(expected);
        
        SmartGeocodeResponse actual = smartCalendarService.geocode("query");
        
        assertEquals(expected, actual);
        verify(geocodingProvider).geocode("query");
    }

    @Test
    @DisplayName("Should return empty recommendations when no spots available")
    void shouldReturnEmptyWhenNoSpots() {
        Parking parking = new Parking(1L);
        parking.setLatitude(40.0);
        parking.setLongitude(-3.0);
        
        when(parkingService.findNearby(anyDouble(), anyDouble(), anyDouble())).thenReturn(Collections.singletonList(parking));
        when(parkingSpotService.findByParkingId(1L)).thenReturn(Collections.singletonList(new ParkingSpot(100L)));
        when(reservationService.hasOverlappingReservations(anyLong(), any(), any())).thenReturn(true);
        
        SmartRecommendationResponse response = smartCalendarService.recommend(request);
        
        assertTrue(response.getSuggestions().isEmpty());
    }

    @Test
    @DisplayName("Should throw exception when no coordinates can be resolved")
    void shouldThrowExceptionWhenNoCoords() {
        request.setLatitude(null);
        request.setLongitude(null);
        request.setDestinationText(null);

        assertThrows(IllegalArgumentException.class, () -> {
            smartCalendarService.recommend(request);
        });
    }

    @Test
    @DisplayName("Should throw exception when coords null and destinationText is blank")
    void shouldThrowWhenCoordsNullAndDestBlank() {
        request.setLatitude(null);
        request.setLongitude(null);
        request.setDestinationText("   ");

        assertThrows(IllegalArgumentException.class, () -> smartCalendarService.recommend(request));
    }

    @Test
    @DisplayName("Should skip geocode when only longitude is null but latitude provided")
    void shouldThrowWhenOnlyLongitudeNull() {
        request.setLongitude(null);
        // latitude is present, destinationText null → first if is false, second if throws
        assertThrows(IllegalArgumentException.class, () -> smartCalendarService.recommend(request));
    }

    @Test
    @DisplayName("Should geocode when latitude is null but destinationText is present")
    void shouldGeocodeWhenLatitudeNull() {
        request.setLatitude(null);
        request.setDestinationText("Some Address");
        
        SmartGeocodeResponse geocodeResponse = new SmartGeocodeResponse("Formatted", 40.0, -3.0);
        when(geocodingProvider.geocode("Some Address")).thenReturn(geocodeResponse);
        when(parkingService.findNearby(anyDouble(), anyDouble(), anyDouble())).thenReturn(Collections.emptyList());

        smartCalendarService.recommend(request);

        verify(geocodingProvider).geocode("Some Address");
    }
}

