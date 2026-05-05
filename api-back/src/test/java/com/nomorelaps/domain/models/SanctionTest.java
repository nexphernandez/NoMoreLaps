package com.nomorelaps.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SanctionTest {

    @Test
    @DisplayName("Sanction - Constructors and Getters/Setters should work")
    void testSanction() {
        Sanction s1 = new Sanction();
        Sanction s2 = new Sanction(1L);
        assertEquals(1L, s2.getId());

        LocalDateTime now = LocalDateTime.now();
        Reservation reservation = new Reservation(1L);
        User user = new User(1L);
        Sanction s3 = new Sanction(1L, 50.0, "Overtime", false, now, reservation, user);
        
        assertEquals(50.0, s3.getAmount());
        assertEquals("Overtime", s3.getReason());
        assertEquals(now, s3.getArrivalTime());
        assertFalse(s3.isPaid());
        assertEquals(reservation, s3.getReservation());
        assertEquals(user, s3.getUser());

        s1.setId(2L);
        s1.setAmount(100.0);
        s1.setReason("Damage");
        s1.setArrivalTime(now);
        s1.setPaid(true);
        s1.setReservation(reservation);

        assertEquals(2L, s1.getId());
        assertEquals(100.0, s1.getAmount());
        assertTrue(s1.isPaid());
    }

    @Test
    @DisplayName("Sanction - Equals and HashCode")
    void testSanctionEquals() {
        Sanction s1 = new Sanction(1L);
        Sanction s2 = new Sanction(1L);
        Sanction s3 = new Sanction(2L);

        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1, s3);
        assertNotEquals(s1, null);
        assertNotEquals(s1, new Object());
        assertEquals(s1, s1);
    }
}
