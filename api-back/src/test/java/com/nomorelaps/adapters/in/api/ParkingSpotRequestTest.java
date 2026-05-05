package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParkingSpotRequestTest {

    @Test
    @DisplayName("ParkingSpotRequest - Constructors and Accessors")
    void testRequest() {
        ParkingSpotRequest req1 = new ParkingSpotRequest();
        req1.setState(true);
        req1.setNumber(10);
        
        assertTrue(req1.isState());
        assertTrue(req1.getState());
        assertEquals(10, req1.getNumber());

        ParkingSpotRequest req2 = new ParkingSpotRequest(false, 20);
        assertFalse(req2.isState());
        assertEquals(20, req2.getNumber());
    }
}
