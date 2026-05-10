package com.nomorelaps.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Reservation domain model.
 * Verifies data integrity and all branches of equals/hashCode with extreme granularity
 * as per the New Backend Test Refactoring Plan.
 */
class ReservationTest {

    private Reservation testReservation;

    @BeforeEach
    void setUp() {
        testReservation = new Reservation(1L);
    }


    @Test
    @DisplayName("Constructor - Empty: Should initialize with default values")
    void shouldInitializeWithDefaultValuesUsingEmptyConstructor() {
        Reservation reservation = new Reservation();
        assertNull(reservation.getId());
        assertNotNull(reservation.getSanctions());
        assertTrue(reservation.getSanctions().isEmpty());
    }

    @Test
    @DisplayName("Constructor - ID: Should initialize with specified identifier")
    void shouldInitializeWithIdUsingIdConstructor() {
        Reservation reservation = new Reservation(5L);
        assertEquals(5L, reservation.getId());
    }

    @Test
    @DisplayName("Constructor - Full: Should initialize all fields correctly")
    void shouldInitializeAllFieldsUsingFullConstructor() {
        LocalDateTime now = LocalDateTime.now();
        ParkingSpot spot = new ParkingSpot(10L);
        User user = new User(20L);
        Set<Sanction> sanctions = new HashSet<>();
        
        Reservation reservation = new Reservation(1L, now, now, 15.0, "ACTIVE", now, spot, user, sanctions);
        
        assertEquals(1L, reservation.getId());
        assertEquals(now, reservation.getStartTime());
        assertEquals(now, reservation.getEndTime());
        assertEquals(15.0, reservation.getPrice());
        assertEquals("ACTIVE", reservation.getState());
        assertEquals(now, reservation.getCreationTime());
        assertEquals(spot, reservation.getParkingSpot());
        assertEquals(user, reservation.getUser());
        assertEquals(sanctions, reservation.getSanctions());
    }


    @Test
    @DisplayName("Getter/Setter - StartTime: Should preserve timestamp")
    void shouldSetAndGetStartTime() {
        LocalDateTime now = LocalDateTime.now();
        testReservation.setStartTime(now);
        assertEquals(now, testReservation.getStartTime());
    }

    @Test
    @DisplayName("Getter/Setter - EndTime: Should preserve timestamp")
    void shouldSetAndGetEndTime() {
        LocalDateTime now = LocalDateTime.now();
        testReservation.setEndTime(now);
        assertEquals(now, testReservation.getEndTime());
    }

    @Test
    @DisplayName("Getter/Setter - Price: Should preserve double value")
    void shouldSetAndGetPrice() {
        testReservation.setPrice(25.5);
        assertEquals(25.5, testReservation.getPrice());
    }

    @Test
    @DisplayName("Getter/Setter - State: Should preserve string value")
    void shouldSetAndGetState() {
        testReservation.setState("COMPLETED");
        assertEquals("COMPLETED", testReservation.getState());
    }

    @Test
    @DisplayName("Getter/Setter - CreationTime: Should preserve timestamp")
    void shouldSetAndGetCreationTime() {
        LocalDateTime now = LocalDateTime.now();
        testReservation.setCreationTime(now);
        assertEquals(now, testReservation.getCreationTime());
    }

    @Test
    @DisplayName("Getter/Setter - BasePrice: Should preserve double value")
    void shouldSetAndGetBasePrice() {
        testReservation.setBasePrice(20.0);
        assertEquals(20.0, testReservation.getBasePrice());
    }

    @Test
    @DisplayName("Getter/Setter - Paid: Should preserve boolean flag")
    void shouldSetAndGetPaid() {
        testReservation.setPaid(true);
        assertTrue(testReservation.isPaid());
    }

    @Test
    @DisplayName("Getter/Setter - ParkingSpot: Should preserve relationship")
    void shouldSetAndGetParkingSpot() {
        ParkingSpot spot = new ParkingSpot(1L);
        testReservation.setParkingSpot(spot);
        assertEquals(spot, testReservation.getParkingSpot());
    }

    @Test
    @DisplayName("Getter/Setter - User: Should preserve relationship")
    void shouldSetAndGetUser() {
        User user = new User(1L);
        testReservation.setUser(user);
        assertEquals(user, testReservation.getUser());
    }

    @Test
    @DisplayName("Getter/Setter - Sanctions: Should preserve set")
    void shouldSetAndGetSanctions() {
        Set<Sanction> sanctions = new HashSet<>();
        testReservation.setSanctions(sanctions);
        assertEquals(sanctions, testReservation.getSanctions());
    }


    @Test
    @DisplayName("equals - Same instance: Should return true")
    void equals_ShouldReturnTrueForSameInstance() {
        assertEquals(testReservation, testReservation);
    }

    @Test
    @DisplayName("equals - Null comparison: Should return false")
    void equals_ShouldReturnFalseForNull() {
        assertNotEquals(testReservation, null);
    }

    @Test
    @DisplayName("equals - Different class: Should return false")
    void equals_ShouldReturnFalseForDifferentType() {
        assertNotEquals(testReservation, "Some String");
    }

    @Test
    @DisplayName("equals - Same ID: Should return true")
    void equals_ShouldReturnTrueForSameId() {
        Reservation other = new Reservation(1L);
        assertEquals(testReservation, other);
    }

    @Test
    @DisplayName("equals - Different ID: Should return false")
    void equals_ShouldReturnFalseForDifferentId() {
        Reservation other = new Reservation(2L);
        assertNotEquals(testReservation, other);
    }

    @Test
    @DisplayName("equals - Both IDs null: Should return true")
    void equals_ShouldReturnTrueForBothIdsNull() {
        Reservation r1 = new Reservation();
        Reservation r2 = new Reservation();
        assertEquals(r1, r2);
    }

    @Test
    @DisplayName("equals - One ID null, other not: Should return false")
    void equals_ShouldReturnFalseWhenOneIdIsNull() {
        Reservation r1 = new Reservation(1L);
        Reservation r2 = new Reservation();
        assertNotEquals(r1, r2);
        assertNotEquals(r2, r1);
    }


    @Test
    @DisplayName("hashCode - Same ID: Should produce identical code")
    void hashCode_ShouldBeSameForSameId() {
        Reservation other = new Reservation(1L);
        assertEquals(testReservation.hashCode(), other.hashCode());
    }

    @Test
    @DisplayName("hashCode - Different ID: Should produce different code")
    void hashCode_ShouldBeDifferentForDifferentId() {
        Reservation other = new Reservation(2L);
        assertNotEquals(testReservation.hashCode(), other.hashCode());
    }

    @Test
    @DisplayName("hashCode - Null ID: Should produce stable code")
    void hashCode_ShouldBeStableForNullId() {
        Reservation r1 = new Reservation();
        Reservation r2 = new Reservation();
        assertEquals(r1.hashCode(), r2.hashCode());
    }
}
