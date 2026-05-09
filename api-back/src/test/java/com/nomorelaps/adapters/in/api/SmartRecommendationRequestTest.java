package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for SmartRecommendationRequest DTO.
 * Verifies that recommendation parameters are correctly handled and equality is maintained.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class SmartRecommendationRequestTest {

    private SmartRecommendationRequest recommendationRequest;

    @BeforeEach
    void setUp() {
        recommendationRequest = new SmartRecommendationRequest();
    }

    @Test
    @DisplayName("Constructor - Should correctly initialize all recommendation fields")
    void shouldInitializeWithFullConstructor() {
        SmartRecommendationRequest fullRequest = new SmartRecommendationRequest("Cinema", 40.0, -3.0, "2026-05-20T18:00:00", 3, 10.0);

        assertEquals("Cinema", fullRequest.getDestinationText());
        assertEquals(40.0, fullRequest.getLatitude());
        assertEquals(-3.0, fullRequest.getLongitude());
        assertEquals("2026-05-20T18:00:00", fullRequest.getStartTime());
        assertEquals(3, fullRequest.getDurationHours());
        assertEquals(10.0, fullRequest.getRadiusKm());
    }

    @Test
    @DisplayName("destinationText - Should set and get the destination name")
    void shouldSetAndGetDestinationText() {
        recommendationRequest.setDestinationText("Airport T4");
        assertEquals("Airport T4", recommendationRequest.getDestinationText());
    }

    @Test
    @DisplayName("latitude - Should set and get latitude")
    void shouldSetAndGetLatitude() {
        recommendationRequest.setLatitude(40.4168);
        assertEquals(40.4168, recommendationRequest.getLatitude());
    }

    @Test
    @DisplayName("longitude - Should set and get longitude")
    void shouldSetAndGetLongitude() {
        recommendationRequest.setLongitude(-3.7038);
        assertEquals(-3.7038, recommendationRequest.getLongitude());
    }

    @Test
    @DisplayName("startTime - Should set and get start time string")
    void shouldSetAndGetStartTime() {
        recommendationRequest.setStartTime("2026-12-31T23:59:59");
        assertEquals("2026-12-31T23:59:59", recommendationRequest.getStartTime());
    }

    @Test
    @DisplayName("durationHours - Should set and get duration")
    void shouldSetAndGetDurationHours() {
        recommendationRequest.setDurationHours(5);
        assertEquals(5, recommendationRequest.getDurationHours());
    }

    @Test
    @DisplayName("radiusKm - Should set and get search radius")
    void shouldSetAndGetRadiusKm() {
        recommendationRequest.setRadiusKm(2.5);
        assertEquals(2.5, recommendationRequest.getRadiusKm());
    }

    @Test
    @DisplayName("equals - Should be equal for same destination and start time")
    void shouldBeEqualForKeyFields() {
        SmartRecommendationRequest first = new SmartRecommendationRequest("Work", 1.0, 1.0, "10:00", 1, 1.0);
        SmartRecommendationRequest second = new SmartRecommendationRequest("Work", 5.0, 5.0, "10:00", 9, 9.0);
        SmartRecommendationRequest diffDest = new SmartRecommendationRequest("Home", 1.0, 1.0, "10:00", 1, 1.0);
        SmartRecommendationRequest diffTime = new SmartRecommendationRequest("Work", 1.0, 1.0, "12:00", 1, 1.0);

        assertEquals(first, first, "Should be equal to itself");
        assertEquals(first, second, "Should be equal if destination and start time match");
        assertNotEquals(first, diffDest);
        assertNotEquals(first, diffTime);
        assertNotEquals(first, null);
        assertNotEquals(first, new Object(), "Should return false for different class (instanceof test)");
    }

    @Test
    @DisplayName("hashCode - Should be consistent for equal requests")
    void shouldHaveConsistentHashCode() {
        SmartRecommendationRequest first = new SmartRecommendationRequest("Work", 0.0, 0.0, "10:00", 0, 0.0);
        SmartRecommendationRequest second = new SmartRecommendationRequest("Work", 1.0, 1.0, "10:00", 1, 1.0);

        assertEquals(first.hashCode(), second.hashCode());
    }
}
