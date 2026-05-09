package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for SmartRecommendationItemResponse DTO.
 * Verifies that individual suggestion details are correctly mapped and equality is maintained.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class SmartRecommendationItemResponseTest {

    private SmartRecommendationItemResponse itemResponse;

    @BeforeEach
    void setUp() {
        itemResponse = new SmartRecommendationItemResponse();
    }

    @Test
    @DisplayName("Constructor - Should correctly initialize all recommendation item fields")
    void shouldInitializeWithFullConstructor() {
        SmartRecommendationItemResponse fullItem = new SmartRecommendationItemResponse(
                101L, "City Center Parking", "Main St 10", 0.5, 12, 505L, 14, "18:00", "21:00", "5.25€ total"
        );

        assertEquals(101L, fullItem.getParkingId());
        assertEquals("City Center Parking", fullItem.getParkingName());
        assertEquals("Main St 10", fullItem.getAddress());
        assertEquals(0.5, fullItem.getDistanceKm());
        assertEquals(12, fullItem.getAvailableSpots());
        assertEquals(505L, fullItem.getSuggestedSpotId());
        assertEquals(14, fullItem.getSuggestedSpotNumber());
        assertEquals("18:00", fullItem.getStartTime());
        assertEquals("21:00", fullItem.getEndTime());
        assertEquals("5.25€ total", fullItem.getPriceLabel());
    }

    @Test
    @DisplayName("parkingId - Should set and get the parking identifier")
    void shouldSetAndGetParkingId() {
        itemResponse.setParkingId(99L);
        assertEquals(99L, itemResponse.getParkingId());
    }

    @Test
    @DisplayName("parkingName - Should set and get the name")
    void shouldSetAndGetParkingName() {
        itemResponse.setParkingName("Sol Parking");
        assertEquals("Sol Parking", itemResponse.getParkingName());
    }

    @Test
    @DisplayName("address - Should set and get the address")
    void shouldSetAndGetAddress() {
        itemResponse.setAddress("Calle Sol, 1");
        assertEquals("Calle Sol, 1", itemResponse.getAddress());
    }

    @Test
    @DisplayName("distanceKm - Should set and get distance")
    void shouldSetAndGetDistanceKm() {
        itemResponse.setDistanceKm(1.25);
        assertEquals(1.25, itemResponse.getDistanceKm());
    }

    @Test
    @DisplayName("availableSpots - Should set and get availability")
    void shouldSetAndGetAvailableSpots() {
        itemResponse.setAvailableSpots(5);
        assertEquals(5, itemResponse.getAvailableSpots());
    }

    @Test
    @DisplayName("suggestedSpotId - Should set and get spot ID")
    void shouldSetAndGetSuggestedSpotId() {
        itemResponse.setSuggestedSpotId(303L);
        assertEquals(303L, itemResponse.getSuggestedSpotId());
    }

    @Test
    @DisplayName("suggestedSpotNumber - Should set and get spot number")
    void shouldSetAndGetSuggestedSpotNumber() {
        itemResponse.setSuggestedSpotNumber(22);
        assertEquals(22, itemResponse.getSuggestedSpotNumber());
    }

    @Test
    @DisplayName("startTime - Should set and get start time")
    void shouldSetAndGetStartTime() {
        itemResponse.setStartTime("10:00");
        assertEquals("10:00", itemResponse.getStartTime());
    }

    @Test
    @DisplayName("endTime - Should set and get end time")
    void shouldSetAndGetEndTime() {
        itemResponse.setEndTime("12:00");
        assertEquals("12:00", itemResponse.getEndTime());
    }

    @Test
    @DisplayName("priceLabel - Should set and get price info")
    void shouldSetAndGetPriceLabel() {
        itemResponse.setPriceLabel("Low Price");
        assertEquals("Low Price", itemResponse.getPriceLabel());
    }

    @Test
    @DisplayName("equals - Should be equal for same parking ID")
    void shouldBeEqualForSameParkingId() {
        SmartRecommendationItemResponse first = new SmartRecommendationItemResponse();
        first.setParkingId(1L);
        
        SmartRecommendationItemResponse second = new SmartRecommendationItemResponse();
        second.setParkingId(1L);
        
        SmartRecommendationItemResponse third = new SmartRecommendationItemResponse();
        third.setParkingId(2L);

        assertEquals(first, first, "Should be equal to itself");
        assertEquals(first, second, "Should be equal if parking IDs match");
        assertNotEquals(first, third, "Should not be equal if parking IDs differ");
        assertNotEquals(first, null, "Should not be equal to null");
        assertNotEquals(first, 123, "Should return false for different class (instanceof test)");
    }

    @Test
    @DisplayName("hashCode - Should be consistent for same parking ID")
    void shouldHaveConsistentHashCode() {
        SmartRecommendationItemResponse first = new SmartRecommendationItemResponse();
        first.setParkingId(1L);
        
        SmartRecommendationItemResponse second = new SmartRecommendationItemResponse();
        second.setParkingId(1L);

        assertEquals(first.hashCode(), second.hashCode());
    }
}
