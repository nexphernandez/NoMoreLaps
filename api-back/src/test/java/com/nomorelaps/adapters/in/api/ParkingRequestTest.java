package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class ParkingRequestTest {
    
    @Test
    void testGettersAndSetters() {
        ParkingRequest req = new ParkingRequest();
        assertNotNull(req);

        req.setAddress("Addr");
        assertEquals("Addr", req.getAddress());

        req.setName("Name");
        assertEquals("Name", req.getName());

        req.setLatitude(1.0);
        assertEquals(1.0, req.getLatitude());

        req.setLongitude(2.0);
        assertEquals(2.0, req.getLongitude());

        LocalDateTime now = LocalDateTime.now();
        req.setOpeningTime(now);
        assertEquals(now, req.getOpeningTime());

        req.setClosingTime(now);
        assertEquals(now, req.getClosingTime());
        
        req.setPricePerHour(2.5);
        assertEquals(2.5, req.getPricePerHour());
        
        ParkingRequest r2 = new ParkingRequest("Addr", "Name", 1.0, 2.0, now, now, 2.5);
        assertEquals("Name", r2.getName());
    }
}
