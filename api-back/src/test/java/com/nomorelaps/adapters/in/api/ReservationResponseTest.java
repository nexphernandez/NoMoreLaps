package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for ReservationResponse DTO.
 * Separates constructor, accessors, and logical methods (equals/hashCode) into granular tests.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class ReservationResponseTest {

    private ReservationResponse response;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        response = new ReservationResponse();
        now = LocalDateTime.now();
    }

    @Test
    @DisplayName("Constructor(id) - Should initialize with ID")
    void shouldInitializeWithIdConstructor() {
        ReservationResponse idResponse = new ReservationResponse(10L);
        assertEquals(10L, idResponse.getId());
    }

    @Test
    @DisplayName("Full Constructor - Should correctly map all provided fields")
    void shouldInitializeWithFullConstructor() {
        LocalDateTime start = now.plusHours(1);
        LocalDateTime end = now.plusHours(3);
        
        ReservationResponse full = new ReservationResponse(1L, start, end, 30.0, "ACTIVE", now, 101L, 500L, "Central Park", "John Doe", 20.0, 10.0, true);

        assertEquals(1L, full.getId());
        assertEquals(start, full.getStartTime());
        assertEquals(end, full.getEndTime());
        assertEquals(30.0, full.getPrice());
        assertEquals("ACTIVE", full.getState());
        assertEquals(now, full.getCreationTime());
        assertEquals(101L, full.getParkingSpotId());
        assertEquals(500L, full.getUserId());
        assertEquals("Central Park", full.getParkingName());
        assertEquals("John Doe", full.getUserName());
        assertEquals(20.0, full.getBasePrice());
        assertEquals(10.0, full.getSanctionPrice());
        assertTrue(full.isPaid());
    }

    @Test
    @DisplayName("price - Should set and get price")
    void shouldSetAndGetPrice() {
        response.setPrice(45.50);
        assertEquals(45.50, response.getPrice());
    }

    @Test
    @DisplayName("basePrice - Should set and get base price")
    void shouldSetAndGetBasePrice() {
        response.setBasePrice(12.0);
        assertEquals(12.0, response.getBasePrice());
    }

    @Test
    @DisplayName("sanctionPrice - Should set and get sanction price")
    void shouldSetAndGetSanctionPrice() {
        response.setSanctionPrice(7.5);
        assertEquals(7.5, response.getSanctionPrice());
    }

    @Test
    @DisplayName("equals - Should be equal for same ID")
    void shouldBeEqualForSameId() {
        ReservationResponse r1 = new ReservationResponse(100L);
        ReservationResponse r2 = new ReservationResponse(100L);
        ReservationResponse r3 = new ReservationResponse(200L);

        assertEquals(r1, r1, "Should be equal to itself");
        assertEquals(r1, r2, "Should be equal if IDs match");
        assertNotEquals(r1, r3, "Should not be equal if IDs differ");
        assertNotEquals(r1, null, "Should not be equal to null");
        assertNotEquals(r1, "Some String", "Should not be equal to other types");
    }

    @Test
    @DisplayName("hashCode - Should have consistent hash for same ID")
    void shouldHaveConsistentHashCode() {
        ReservationResponse r1 = new ReservationResponse(100L);
        ReservationResponse r2 = new ReservationResponse(100L);

        assertEquals(r1.hashCode(), r2.hashCode(), "Same ID should produce same hash code");
    }
}
