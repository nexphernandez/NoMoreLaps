package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for ParkingSpotResponse DTO.
 * Verifies that parking spot response data is correctly mapped and logical equality is maintained.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class ParkingSpotResponseTest {

    private ParkingSpotResponse spotResponse;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        spotResponse = new ParkingSpotResponse();
        now = LocalDateTime.now();
    }

    @Test
    @DisplayName("Constructor(id) - Should initialize with ID")
    void shouldInitializeWithIdConstructor() {
        ParkingSpotResponse idOnly = new ParkingSpotResponse(200L);
        assertEquals(200L, idOnly.getId());
    }

    @Test
    @DisplayName("Full Constructor - Should correctly map all fields")
    void shouldInitializeWithFullConstructor() {
        ParkingSpotResponse fullResponse = new ParkingSpotResponse(1L, true, 42, now);

        assertEquals(1L, fullResponse.getId());
        assertTrue(fullResponse.getState());
        assertEquals(42, fullResponse.getNumber());
        assertEquals(now, fullResponse.getRegisterDate());
    }

    @Test
    @DisplayName("id - Should set and get the ID")
    void shouldSetAndGetId() {
        spotResponse.setId(500L);
        assertEquals(500L, spotResponse.getId());
    }

    @Test
    @DisplayName("state - Should set and get the status")
    void shouldSetAndGetState() {
        spotResponse.setState(true);
        assertTrue(spotResponse.getState());
        assertTrue(spotResponse.isState());
        
        spotResponse.setState(false);
        assertFalse(spotResponse.getState());
    }

    @Test
    @DisplayName("number - Should set and get the spot number")
    void shouldSetAndGetNumber() {
        spotResponse.setNumber(7);
        assertEquals(7, spotResponse.getNumber());
    }

    @Test
    @DisplayName("registerDate - Should set and get registration timestamp")
    void shouldSetAndGetRegisterDate() {
        spotResponse.setRegisterDate(now);
        assertEquals(now, spotResponse.getRegisterDate());
    }

    @Test
    @DisplayName("equals - Should be equal for same ID")
    void shouldBeEqualForSameId() {
        ParkingSpotResponse first = new ParkingSpotResponse(1L);
        ParkingSpotResponse second = new ParkingSpotResponse(1L);
        ParkingSpotResponse third = new ParkingSpotResponse(2L);

        assertEquals(first, first, "Should be equal to itself");
        assertEquals(first, second, "Should be equal if IDs match");
        assertNotEquals(first, third, "Should not be equal if IDs differ");
        assertNotEquals(first, null, "Should not be equal to null");
        assertNotEquals(first, "not a response", "Should return false for different class (instanceof test)");
    }

    @Test
    @DisplayName("hashCode - Should be consistent for same ID")
    void shouldHaveConsistentHashCode() {
        ParkingSpotResponse first = new ParkingSpotResponse(1L);
        ParkingSpotResponse second = new ParkingSpotResponse(1L);

        assertEquals(first.hashCode(), second.hashCode());
    }
}
