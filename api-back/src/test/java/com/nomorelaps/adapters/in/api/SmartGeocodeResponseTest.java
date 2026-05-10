package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for SmartGeocodeResponse DTO.
 * Verifies that geocoding results are correctly mapped and logical equality is maintained.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class SmartGeocodeResponseTest {

    private SmartGeocodeResponse geocodeResponse;

    @BeforeEach
    void setUp() {
        geocodeResponse = new SmartGeocodeResponse();
    }

    @Test
    @DisplayName("Constructor - Should correctly initialize all fields")
    void shouldInitializeWithParameterizedConstructor() {
        SmartGeocodeResponse response = new SmartGeocodeResponse("Calle Falsa 123, Madrid", 40.0, -3.0);

        assertEquals("Calle Falsa 123, Madrid", response.getFormattedAddress());
        assertEquals(40.0, response.getLatitude());
        assertEquals(-3.0, response.getLongitude());
    }

    @Test
    @DisplayName("formattedAddress - Should set and get the address string")
    void shouldSetAndGetFormattedAddress() {
        geocodeResponse.setFormattedAddress("Paseo de la Castellana, 1");
        assertEquals("Paseo de la Castellana, 1", geocodeResponse.getFormattedAddress());
    }

    @Test
    @DisplayName("latitude - Should set and get the latitude")
    void shouldSetAndGetLatitude() {
        geocodeResponse.setLatitude(40.4168);
        assertEquals(40.4168, geocodeResponse.getLatitude());
    }

    @Test
    @DisplayName("longitude - Should set and get the longitude")
    void shouldSetAndGetLongitude() {
        geocodeResponse.setLongitude(-3.7038);
        assertEquals(-3.7038, geocodeResponse.getLongitude());
    }

    @Test
    @DisplayName("equals - Should be equal for identical responses")
    void shouldBeEqualForIdenticalResponses() {
        SmartGeocodeResponse first = new SmartGeocodeResponse("Addr A", 1.0, 1.0);
        SmartGeocodeResponse second = new SmartGeocodeResponse("Addr A", 1.0, 1.0);
        SmartGeocodeResponse diffAddr = new SmartGeocodeResponse("Addr B", 1.0, 1.0);
        SmartGeocodeResponse diffLat = new SmartGeocodeResponse("Addr A", 2.0, 1.0);
        SmartGeocodeResponse diffLon = new SmartGeocodeResponse("Addr A", 1.0, 2.0);

        assertEquals(first, first, "Should be equal to itself");
        assertEquals(first, second, "Should be equal if all fields match");
        assertNotEquals(first, diffAddr);
        assertNotEquals(first, diffLat);
        assertNotEquals(first, diffLon);
        assertNotEquals(first, null);
        assertNotEquals(first, new Object(), "Should return false for different class (instanceof test)");
    }

    @Test
    @DisplayName("hashCode - Should be consistent for equal responses")
    void shouldHaveConsistentHashCode() {
        SmartGeocodeResponse first = new SmartGeocodeResponse("Addr A", 1.0, 1.0);
        SmartGeocodeResponse second = new SmartGeocodeResponse("Addr A", 1.0, 1.0);

        assertEquals(first.hashCode(), second.hashCode());
    }
}
