package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class ReservationResponseTest {
    
    @Test
    void testGettersAndSetters() {
        ReservationResponse response = new ReservationResponse();
        assertNotNull(response);

        response.setId(1L);
        assertEquals(1L, response.getId());

        LocalDateTime now = LocalDateTime.now();
        response.setStartTime(now);
        assertEquals(now, response.getStartTime());

        response.setEndTime(now);
        assertEquals(now, response.getEndTime());

        response.setPrice(10.5);
        assertEquals(10.5, response.getPrice());

        response.setState("ACTIVE");
        assertEquals("ACTIVE", response.getState());

        response.setCreationTime(now);
        assertEquals(now, response.getCreationTime());
        
        response.setParkingSpotId(2L);
        assertEquals(2L, response.getParkingSpotId());

        response.setUserId(3L);
        assertEquals(3L, response.getUserId());

        response.setParkingName("Name");
        assertEquals("Name", response.getParkingName());

        ReservationResponse r2 = new ReservationResponse(1L);
        assertEquals(1L, r2.getId());
        
        ReservationResponse r3 = new ReservationResponse(1L, now, now, 10.5, "ACTIVE", now, 2L, 3L, "Name");
        assertEquals(1L, r3.getId());
        assertEquals("ACTIVE", r3.getState());
    }

    @Test
    void testEqualsAndHashCode() {
        ReservationResponse response1 = new ReservationResponse(1L);
        ReservationResponse response2 = new ReservationResponse(1L);
        ReservationResponse response3 = new ReservationResponse(2L);

        assertEquals(response1, response1);
        
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
        
        assertNotEquals(response1, response3);
        
        assertNotEquals(response1, null);
        assertNotEquals(response1, new Object());
        
        ReservationResponse responseNull = new ReservationResponse(null);
        ReservationResponse responseNull2 = new ReservationResponse(null);
        assertEquals(responseNull, responseNull2);
        assertNotEquals(responseNull, response1);
        assertNotEquals(response1, responseNull);
    }
}
