package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class ParkingResponseTest {
    
    @Test
    void testGettersAndSetters() {
        ParkingResponse response = new ParkingResponse();
        assertNotNull(response);

        response.setId(1L);
        assertEquals(1L, response.getId());

        response.setAddress("Addr");
        assertEquals("Addr", response.getAddress());

        response.setName("Name");
        assertEquals("Name", response.getName());

        response.setLatitude(1.0);
        assertEquals(1.0, response.getLatitude());

        response.setLongitude(2.0);
        assertEquals(2.0, response.getLongitude());

        LocalDateTime now = LocalDateTime.now();
        response.setOpeningTime(now);
        assertEquals(now, response.getOpeningTime());

        response.setClosingTime(now);
        assertEquals(now, response.getClosingTime());

        response.setCreatedAt(now);
        assertEquals(now, response.getCreatedAt());
        
        response.setSanctionAmount(10.0);
        assertEquals(10.0, response.getSanctionAmount());

        response.setSanctionIntervalInMinutes(15);
        assertEquals(15, response.getSanctionIntervalInMinutes());

        response.setPricePerHour(2.5);
        assertEquals(2.5, response.getPricePerHour());
        
        ParkingResponse r2 = new ParkingResponse(1L);
        assertEquals(1L, r2.getId());
        
        ParkingResponse r3 = new ParkingResponse(1L, "Addr", "Name", 1.0, 2.0, now, now, now, 2.5);
        assertEquals(1L, r3.getId());
        assertEquals("Name", r3.getName());
    }

    @Test
    void testEqualsAndHashCode() {
        ParkingResponse response1 = new ParkingResponse(1L);
        ParkingResponse response2 = new ParkingResponse(1L);
        ParkingResponse response3 = new ParkingResponse(2L);

        assertEquals(response1, response1);
        
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
        
        assertNotEquals(response1, response3);
        
        assertNotEquals(response1, null);
        assertNotEquals(response1, new Object());
        
        ParkingResponse responseNull = new ParkingResponse(null);
        ParkingResponse responseNull2 = new ParkingResponse(null);
        assertEquals(responseNull, responseNull2);
        assertNotEquals(responseNull, response1);
        assertNotEquals(response1, responseNull);
    }
}
