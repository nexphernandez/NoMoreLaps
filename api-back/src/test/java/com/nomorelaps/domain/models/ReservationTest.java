package com.nomorelaps.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    @DisplayName("Reservation - Constructors and Getters/Setters should work")
    void testReservation() {
        Reservation r1 = new Reservation();
        assertNotNull(r1.getSanctions());

        Reservation r2 = new Reservation(1L);
        assertEquals(1L, r2.getId());

        LocalDateTime now = LocalDateTime.now();
        User user = new User(1L);
        ParkingSpot spot = new ParkingSpot(1L);
        Set<Sanction> sanctions = new HashSet<>();
        Reservation r3 = new Reservation(1L, now, now.plusHours(2), 20.0, "ACTIVE", now, spot, user, sanctions);
        
        assertEquals(20.0, r3.getPrice());
        assertEquals("ACTIVE", r3.getState());
        assertEquals(user, r3.getUser());
        assertEquals(spot, r3.getParkingSpot());
        assertEquals(sanctions, r3.getSanctions());

        r1.setId(2L);
        r1.setStartTime(now);
        r1.setEndTime(now.plusHours(1));
        r1.setPrice(10.0);
        r1.setState("COMPLETED");
        r1.setCreationTime(now);
        r1.setUser(user);
        r1.setParkingSpot(spot);
        r1.setSanctions(sanctions);

        assertEquals(2L, r1.getId());
        assertEquals(10.0, r1.getPrice());
        assertEquals("COMPLETED", r1.getState());
    }

    @Test
    @DisplayName("Reservation - Equals and HashCode")
    void testReservationEquals() {
        Reservation r1 = new Reservation(1L);
        Reservation r2 = new Reservation(1L);
        Reservation r3 = new Reservation(2L);

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
        assertNotEquals(r1, r3);
        assertNotEquals(r1, null);
        assertNotEquals(r1, new Object());
        assertEquals(r1, r1);
    }
}
