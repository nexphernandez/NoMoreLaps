package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationResponseTest {

    @Test
    @DisplayName("Should test constructors, getters, setters, equals and hashCode")
    void testReservationResponse() {
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = start.plusHours(2);
        LocalDateTime now = LocalDateTime.now();

        // All fields constructor
        ReservationResponse r1 = new ReservationResponse(1L, start, end, 15.0, "ACTIVE", now, 101L, 500L, "Central Park", "John Doe", 10.0, 5.0, true);
        
        assertEquals(1L, r1.getId());
        assertEquals(start, r1.getStartTime());
        assertEquals(end, r1.getEndTime());
        assertEquals(15.0, r1.getPrice());
        assertEquals("ACTIVE", r1.getState());
        assertEquals(now, r1.getCreationTime());
        assertEquals(101L, r1.getParkingSpotId());
        assertEquals(500L, r1.getUserId());
        assertEquals("Central Park", r1.getParkingName());
        assertEquals("John Doe", r1.getUserName());
        assertEquals(10.0, r1.getBasePrice());
        assertEquals(5.0, r1.getSanctionPrice());
        assertTrue(r1.isPaid());

        // Setter for basePrice (specifically requested)
        ReservationResponse r2 = new ReservationResponse();
        r2.setBasePrice(25.5);
        assertEquals(25.5, r2.getBasePrice());

        // Equals and HashCode
        ReservationResponse r3 = new ReservationResponse(1L);
        assertEquals(r1, r3);
        assertEquals(r1.hashCode(), r3.hashCode());
        assertNotEquals(r1, new ReservationResponse(2L));
        assertNotEquals(r1, null);
        assertNotEquals(r1, "String");
        assertEquals(r1, r1);
    }
}
