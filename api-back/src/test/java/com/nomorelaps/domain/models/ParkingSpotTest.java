package com.nomorelaps.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for ParkingSpot domain model.
 * Verifies data integrity and all branches of equals/hashCode with extreme granularity
 * as per the New Backend Test Refactoring Plan.
 */
class ParkingSpotTest {

    private ParkingSpot testSpot;

    @BeforeEach
    void setUp() {
        testSpot = new ParkingSpot(1L);
    }


    @Test
    @DisplayName("Constructor - Empty: Should initialize with default values")
    void shouldInitializeWithDefaultValuesUsingEmptyConstructor() {
        ParkingSpot spot = new ParkingSpot();
        assertNull(spot.getId());
        assertFalse(spot.isState());
    }

    @Test
    @DisplayName("Constructor - ID: Should initialize with specified identifier")
    void shouldInitializeWithIdUsingIdConstructor() {
        ParkingSpot spot = new ParkingSpot(5L);
        assertEquals(5L, spot.getId());
    }

    @Test
    @DisplayName("Constructor - Full: Should initialize all fields correctly")
    void shouldInitializeAllFieldsUsingFullConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Parking parking = new Parking(10L);
        
        ParkingSpot spot = new ParkingSpot(1L, true, 101, now, parking);
        
        assertEquals(1L, spot.getId());
        assertTrue(spot.isState());
        assertEquals(101, spot.getNumber());
        assertEquals(now, spot.getRegisterDate());
        assertEquals(parking, spot.getParking());
    }


    @Test
    @DisplayName("Getter/Setter - State: Should preserve boolean flag")
    void shouldSetAndGetState() {
        testSpot.setState(true);
        assertTrue(testSpot.isState());
    }

    @Test
    @DisplayName("Getter/Setter - Number: Should preserve integer identifier")
    void shouldSetAndGetNumber() {
        testSpot.setNumber(42);
        assertEquals(42, testSpot.getNumber());
    }

    @Test
    @DisplayName("Getter/Setter - RegisterDate: Should preserve timestamp")
    void shouldSetAndGetRegisterDate() {
        LocalDateTime now = LocalDateTime.now();
        testSpot.setRegisterDate(now);
        assertEquals(now, testSpot.getRegisterDate());
    }

    @Test
    @DisplayName("Getter/Setter - Parking: Should preserve relationship")
    void shouldSetAndGetParking() {
        Parking parking = new Parking(1L);
        testSpot.setParking(parking);
        assertEquals(parking, testSpot.getParking());
    }


    @Test
    @DisplayName("equals - Same instance: Should return true")
    void equals_ShouldReturnTrueForSameInstance() {
        assertEquals(testSpot, testSpot);
    }

    @Test
    @DisplayName("equals - Null comparison: Should return false")
    void equals_ShouldReturnFalseForNull() {
        assertNotEquals(testSpot, null);
    }

    @Test
    @DisplayName("equals - Different class: Should return false")
    void equals_ShouldReturnFalseForDifferentType() {
        assertNotEquals(testSpot, "Some String");
    }

    @Test
    @DisplayName("equals - Same ID: Should return true")
    void equals_ShouldReturnTrueForSameId() {
        ParkingSpot other = new ParkingSpot(1L);
        assertEquals(testSpot, other);
    }

    @Test
    @DisplayName("equals - Different ID: Should return false")
    void equals_ShouldReturnFalseForDifferentId() {
        ParkingSpot other = new ParkingSpot(2L);
        assertNotEquals(testSpot, other);
    }

    @Test
    @DisplayName("equals - Both IDs null: Should return true")
    void equals_ShouldReturnTrueForBothIdsNull() {
        ParkingSpot s1 = new ParkingSpot();
        ParkingSpot s2 = new ParkingSpot();
        assertEquals(s1, s2);
    }

    @Test
    @DisplayName("equals - One ID null, other not: Should return false")
    void equals_ShouldReturnFalseWhenOneIdIsNull() {
        ParkingSpot s1 = new ParkingSpot(1L);
        ParkingSpot s2 = new ParkingSpot();
        assertNotEquals(s1, s2);
        assertNotEquals(s2, s1);
    }


    @Test
    @DisplayName("hashCode - Same ID: Should produce identical code")
    void hashCode_ShouldBeSameForSameId() {
        ParkingSpot other = new ParkingSpot(1L);
        assertEquals(testSpot.hashCode(), other.hashCode());
    }

    @Test
    @DisplayName("hashCode - Different ID: Should produce different code")
    void hashCode_ShouldBeDifferentForDifferentId() {
        ParkingSpot other = new ParkingSpot(2L);
        assertNotEquals(testSpot.hashCode(), other.hashCode());
    }

    @Test
    @DisplayName("hashCode - Null ID: Should produce stable code")
    void hashCode_ShouldBeStableForNullId() {
        ParkingSpot s1 = new ParkingSpot();
        ParkingSpot s2 = new ParkingSpot();
        assertEquals(s1.hashCode(), s2.hashCode());
    }
}
