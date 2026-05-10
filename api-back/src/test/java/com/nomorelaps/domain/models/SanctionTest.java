package com.nomorelaps.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Sanction domain model.
 * Verifies data integrity and all branches of equals/hashCode with extreme granularity
 * as per the New Backend Test Refactoring Plan.
 */
class SanctionTest {

    private Sanction testSanction;

    @BeforeEach
    void setUp() {
        testSanction = new Sanction(1L);
    }


    @Test
    @DisplayName("Constructor - Empty: Should initialize with default values")
    void shouldInitializeWithDefaultValuesUsingEmptyConstructor() {
        Sanction sanction = new Sanction();
        assertNull(sanction.getId());
        assertFalse(sanction.isPaid());
    }

    @Test
    @DisplayName("Constructor - ID: Should initialize with specified identifier")
    void shouldInitializeWithIdUsingIdConstructor() {
        Sanction sanction = new Sanction(5L);
        assertEquals(5L, sanction.getId());
    }

    @Test
    @DisplayName("Constructor - Full: Should initialize all fields correctly")
    void shouldInitializeAllFieldsUsingFullConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Reservation res = new Reservation(10L);
        User user = new User(20L);
        
        Sanction sanction = new Sanction(1L, 50.0, "Late arrival", true, now, res, user);
        
        assertEquals(1L, sanction.getId());
        assertEquals(50.0, sanction.getAmount());
        assertEquals("Late arrival", sanction.getReason());
        assertTrue(sanction.isPaid());
        assertEquals(now, sanction.getArrivalTime());
        assertEquals(res, sanction.getReservation());
        assertEquals(user, sanction.getUser());
    }


    @Test
    @DisplayName("Getter/Setter - Amount: Should preserve double value")
    void shouldSetAndGetAmount() {
        testSanction.setAmount(100.0);
        assertEquals(100.0, testSanction.getAmount());
    }

    @Test
    @DisplayName("Getter/Setter - Reason: Should preserve string value")
    void shouldSetAndGetReason() {
        testSanction.setReason("Expired");
        assertEquals("Expired", testSanction.getReason());
    }

    @Test
    @DisplayName("Getter/Setter - Paid: Should preserve boolean flag")
    void shouldSetAndGetPaid() {
        testSanction.setPaid(true);
        assertTrue(testSanction.isPaid());
    }

    @Test
    @DisplayName("Getter/Setter - ArrivalTime: Should preserve timestamp")
    void shouldSetAndGetArrivalTime() {
        LocalDateTime now = LocalDateTime.now();
        testSanction.setArrivalTime(now);
        assertEquals(now, testSanction.getArrivalTime());
    }

    @Test
    @DisplayName("Getter/Setter - Reservation: Should preserve relationship")
    void shouldSetAndGetReservation() {
        Reservation res = new Reservation(1L);
        testSanction.setReservation(res);
        assertEquals(res, testSanction.getReservation());
    }

    @Test
    @DisplayName("Getter/Setter - User: Should preserve relationship")
    void shouldSetAndGetUser() {
        User user = new User(1L);
        testSanction.setUser(user);
        assertEquals(user, testSanction.getUser());
    }


    @Test
    @DisplayName("equals - Same instance: Should return true")
    void equals_ShouldReturnTrueForSameInstance() {
        assertEquals(testSanction, testSanction);
    }

    @Test
    @DisplayName("equals - Null comparison: Should return false")
    void equals_ShouldReturnFalseForNull() {
        assertNotEquals(testSanction, null);
    }

    @Test
    @DisplayName("equals - Different class: Should return false")
    void equals_ShouldReturnFalseForDifferentType() {
        assertNotEquals(testSanction, "Some String");
    }

    @Test
    @DisplayName("equals - Same ID: Should return true")
    void equals_ShouldReturnTrueForSameId() {
        Sanction other = new Sanction(1L);
        assertEquals(testSanction, other);
    }

    @Test
    @DisplayName("equals - Different ID: Should return false")
    void equals_ShouldReturnFalseForDifferentId() {
        Sanction other = new Sanction(2L);
        assertNotEquals(testSanction, other);
    }

    @Test
    @DisplayName("equals - Both IDs null: Should return true")
    void equals_ShouldReturnTrueForBothIdsNull() {
        Sanction s1 = new Sanction();
        Sanction s2 = new Sanction();
        assertEquals(s1, s2);
    }

    @Test
    @DisplayName("equals - One ID null, other not: Should return false")
    void equals_ShouldReturnFalseWhenOneIdIsNull() {
        Sanction s1 = new Sanction(1L);
        Sanction s2 = new Sanction();
        assertNotEquals(s1, s2);
        assertNotEquals(s2, s1);
    }


    @Test
    @DisplayName("hashCode - Same ID: Should produce identical code")
    void hashCode_ShouldBeSameForSameId() {
        Sanction other = new Sanction(1L);
        assertEquals(testSanction.hashCode(), other.hashCode());
    }

    @Test
    @DisplayName("hashCode - Different ID: Should produce different code")
    void hashCode_ShouldBeDifferentForDifferentId() {
        Sanction other = new Sanction(2L);
        assertNotEquals(testSanction.hashCode(), other.hashCode());
    }

    @Test
    @DisplayName("hashCode - Null ID: Should produce stable code")
    void hashCode_ShouldBeStableForNullId() {
        Sanction s1 = new Sanction();
        Sanction s2 = new Sanction();
        assertEquals(s1.hashCode(), s2.hashCode());
    }
}
