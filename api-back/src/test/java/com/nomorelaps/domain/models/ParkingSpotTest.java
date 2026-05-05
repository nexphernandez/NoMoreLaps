package com.nomorelaps.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParkingSpotTest {

    @Test
    @DisplayName("ParkingSpot - Constructors and Getters/Setters should work")
    void testParkingSpot() {
        ParkingSpot s1 = new ParkingSpot();
        
        ParkingSpot s2 = new ParkingSpot(1L);
        assertEquals(1L, s2.getId());

        LocalDateTime now = LocalDateTime.now();
        Parking parking = new Parking(1L);
        ParkingSpot s3 = new ParkingSpot(1L, true, 101, now, parking);
        
        assertEquals(101, s3.getNumber());
        assertTrue(s3.isState());
        assertEquals(parking, s3.getParking());
        assertEquals(now, s3.getRegisterDate());

        s1.setId(2L);
        s1.setNumber(202);
        s1.setState(false);
        s1.setRegisterDate(now);
        s1.setParking(parking);

        assertEquals(2L, s1.getId());
        assertEquals(202, s1.getNumber());
        assertFalse(s1.isState());
    }

    @Test
    @DisplayName("ParkingSpot - Equals and HashCode")
    void testParkingSpotEquals() {
        ParkingSpot s1 = new ParkingSpot(1L);
        ParkingSpot s2 = new ParkingSpot(1L);
        ParkingSpot s3 = new ParkingSpot(2L);

        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1, s3);
        assertNotEquals(s1, null);
        assertNotEquals(s1, new Object());
        assertEquals(s1, s1);
    }
}
