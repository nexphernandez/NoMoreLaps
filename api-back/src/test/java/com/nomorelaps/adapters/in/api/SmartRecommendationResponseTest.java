package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for SmartRecommendationResponse DTO.
 * Verifies that recommendation suggestions are correctly mapped and logical equality is maintained.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class SmartRecommendationResponseTest {

    private SmartRecommendationResponse recommendationResponse;

    @BeforeEach
    void setUp() {
        recommendationResponse = new SmartRecommendationResponse();
    }

    @Test
    @DisplayName("Constructor - Should correctly initialize all response fields")
    void shouldInitializeWithFullConstructor() {
        List<SmartRecommendationItemResponse> suggestions = new ArrayList<>();
        
        SmartRecommendationResponse response = new SmartRecommendationResponse("Mall", 40.5, -3.8, suggestions);

        assertEquals("Mall", response.getDestinationText());
        assertEquals(40.5, response.getLatitude());
        assertEquals(-3.8, response.getLongitude());
        assertEquals(suggestions, response.getSuggestions());
    }

    @Test
    @DisplayName("destinationText - Should set and get the destination name")
    void shouldSetAndGetDestinationText() {
        recommendationResponse.setDestinationText("Gran Via, 1");
        assertEquals("Gran Via, 1", recommendationResponse.getDestinationText());
    }

    @Test
    @DisplayName("latitude - Should set and get the latitude")
    void shouldSetAndGetLatitude() {
        recommendationResponse.setLatitude(40.4168);
        assertEquals(40.4168, recommendationResponse.getLatitude());
    }

    @Test
    @DisplayName("longitude - Should set and get the longitude")
    void shouldSetAndGetLongitude() {
        recommendationResponse.setLongitude(-3.7038);
        assertEquals(-3.7038, recommendationResponse.getLongitude());
    }

    @Test
    @DisplayName("suggestions - Should set and get the list of items")
    void shouldSetAndGetSuggestions() {
        List<SmartRecommendationItemResponse> list = new ArrayList<>();
        recommendationResponse.setSuggestions(list);
        assertEquals(list, recommendationResponse.getSuggestions());
    }

    @Test
    @DisplayName("equals - Should be equal for identical coordinate and text context")
    void shouldBeEqualForIdenticalContext() {
        SmartRecommendationResponse first = new SmartRecommendationResponse("Point A", 1.0, 1.0, new ArrayList<>());
        SmartRecommendationResponse second = new SmartRecommendationResponse("Point A", 1.0, 1.0, null);
        SmartRecommendationResponse diffText = new SmartRecommendationResponse("Point B", 1.0, 1.0, new ArrayList<>());
        SmartRecommendationResponse diffLat = new SmartRecommendationResponse("Point A", 2.0, 1.0, new ArrayList<>());
        SmartRecommendationResponse diffLon = new SmartRecommendationResponse("Point A", 1.0, 2.0, new ArrayList<>());

        assertEquals(first, first, "Should be equal to itself");
        assertEquals(first, second, "Should be equal if text and coordinates match (ignoring suggestions)");
        assertNotEquals(first, diffText, "Should not be equal if destinationText differs");
        assertNotEquals(first, diffLat, "Should not be equal if latitude differs");
        assertNotEquals(first, diffLon, "Should not be equal if longitude differs");
        assertNotEquals(first, null, "Should not be equal to null");
        assertNotEquals(first, "not a response", "Should return false for different class (instanceof test)");
    }

    @Test
    @DisplayName("hashCode - Should be consistent for equal responses")
    void shouldHaveConsistentHashCode() {
        SmartRecommendationResponse first = new SmartRecommendationResponse("Point A", 1.0, 1.0, new ArrayList<>());
        SmartRecommendationResponse second = new SmartRecommendationResponse("Point A", 1.0, 1.0, null);

        assertEquals(first.hashCode(), second.hashCode());
    }
}
