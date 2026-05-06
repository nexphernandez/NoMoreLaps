package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class ReservationRequestTest {
    
    @Test
    void testGettersAndSetters() {
        ReservationRequest req = new ReservationRequest();
        assertNotNull(req);

        LocalDateTime now = LocalDateTime.now();
        req.setStartTime(now);
        assertEquals(now, req.getStartTime());

        req.setEndTime(now);
        assertEquals(now, req.getEndTime());
        
        req.setTotalPrice(10.5);
        assertEquals(10.5, req.getTotalPrice());
        
        req.setStatus("ACTIVE");
        assertEquals("ACTIVE", req.getStatus());
        
        req.setParkingSpotId(2L);
        assertEquals(2L, req.getParkingSpotId());

        req.setUserId(3L);
        assertEquals(3L, req.getUserId());
        
        ReservationRequest r2 = new ReservationRequest(now, now, 10.5, "ACTIVE", 3L, 2L);
        assertEquals(2L, r2.getParkingSpotId());
    }
}
