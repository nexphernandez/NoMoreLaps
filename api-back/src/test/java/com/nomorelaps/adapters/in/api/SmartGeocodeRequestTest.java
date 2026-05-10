package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for SmartGeocodeRequest DTO.
 * Verifies that geocoding queries are correctly handled and equality is maintained.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class SmartGeocodeRequestTest {

    private SmartGeocodeRequest geocodeRequest;

    @BeforeEach
    void setUp() {
        geocodeRequest = new SmartGeocodeRequest();
    }

    @Test
    @DisplayName("Constructor - Should correctly initialize the query string")
    void shouldInitializeWithParameterizedConstructor() {
        SmartGeocodeRequest madridRequest = new SmartGeocodeRequest("Calle Gran Via, Madrid");

        assertEquals("Calle Gran Via, Madrid", madridRequest.getQuery());
    }

    @Test
    @DisplayName("query - Should set and get the search query")
    void shouldSetAndGetQuery() {
        geocodeRequest.setQuery("Barcelona Airport");
        assertEquals("Barcelona Airport", geocodeRequest.getQuery());
    }

    @Test
    @DisplayName("equals - Should be equal for same query string")
    void shouldBeEqualForSameQuery() {
        SmartGeocodeRequest first = new SmartGeocodeRequest("Search A");
        SmartGeocodeRequest second = new SmartGeocodeRequest("Search A");
        SmartGeocodeRequest third = new SmartGeocodeRequest("Search B");

        assertEquals(first, first, "Should be equal to itself");
        assertEquals(first, second, "Should be equal if queries match");
        assertNotEquals(first, third, "Should not be equal if queries differ");
        assertNotEquals(first, null, "Should not be equal to null");
        assertNotEquals(first, "not a request", "Should return false for different class (instanceof test)");
    }

    @Test
    @DisplayName("hashCode - Should be consistent for same query")
    void shouldHaveConsistentHashCode() {
        SmartGeocodeRequest first = new SmartGeocodeRequest("Search A");
        SmartGeocodeRequest second = new SmartGeocodeRequest("Search A");

        assertEquals(first.hashCode(), second.hashCode());
    }
}
