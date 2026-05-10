package com.nomorelaps.adapters.out.persistence.jpa;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Unit tests for ParkingSpotJpaEntity.
 * Verifies data integrity, all constructors, and all branches of equals/hashCode.
 */
class ParkingSpotJpaEntityTest {

    private ParkingSpotJpaEntity testSpot;

    @BeforeEach
    void setUp() {
        testSpot = new ParkingSpotJpaEntity();
    }

    @Test
    @DisplayName("Constructor - Empty should initialize object")
    void shouldInitializeEmpty() {
        assertNotNull(new ParkingSpotJpaEntity());
    }

    @Test
    @DisplayName("Constructor - ID constructor should correctly set ID")
    void shouldInitializeWithId() {
        ParkingSpotJpaEntity entity = new ParkingSpotJpaEntity(10L);
        assertEquals(10L, entity.getId());
    }

    @Test
    @DisplayName("Constructor - Full constructor should correctly set all fields")
    void shouldInitializeWithAllFields() {
        LocalDateTime now = LocalDateTime.now();
        ParkingJpaEntity parking = new ParkingJpaEntity(5L);
        Set<ReservationJpaEntity> reservations = new HashSet<>();

        ParkingSpotJpaEntity entity = new ParkingSpotJpaEntity(1L, true, 101, now, parking, reservations);

        assertEquals(1L, entity.getId());
        assertTrue(entity.getState());
        assertEquals(101, entity.getNumber());
        assertEquals(now, entity.getRegisterDate());
        assertEquals(parking, entity.getParking());
        assertEquals(reservations, entity.getReservations());
    }

    @Test
    @DisplayName("Setters - Should update basic fields")
    void shouldSetBasicFields() {
        testSpot.setId(1L);
        assertEquals(1L, testSpot.getId());
        
        testSpot.setState(false);
        assertFalse(testSpot.getState());
        assertFalse(testSpot.isState());
        
        testSpot.setNumber(55);
        assertEquals(55, testSpot.getNumber());
        
        LocalDateTime now = LocalDateTime.now();
        testSpot.setRegisterDate(now);
        assertEquals(now, testSpot.getRegisterDate());
    }

    @Test
    @DisplayName("Relationships - Should update Parking and Reservations")
    void shouldSetRelationships() {
        ParkingJpaEntity parking = new ParkingJpaEntity(5L);
        Set<ReservationJpaEntity> reservations = new HashSet<>();

        testSpot.setParking(parking);
        testSpot.setReservations(reservations);

        assertEquals(parking, testSpot.getParking());
        assertEquals(reservations, testSpot.getReservations());
    }

    @Test
    @DisplayName("onCreate - Should set register date if null")
    void shouldSetTimestampOnCreate() {
        assertNull(testSpot.getRegisterDate());
        testSpot.onCreate();
        assertNotNull(testSpot.getRegisterDate());
    }

    @Test
    @DisplayName("Equals - Should handle same object and same ID")
    void shouldVerifyEquality() {
        ParkingSpotJpaEntity s1 = new ParkingSpotJpaEntity(1L);
        ParkingSpotJpaEntity s2 = new ParkingSpotJpaEntity(1L);
        ParkingSpotJpaEntity s3 = new ParkingSpotJpaEntity(2L);

        assertEquals(s1, s1);
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1, s3);
        assertNotEquals(s1, null);
    }

    @Test
    @DisplayName("Equals - Should handle null IDs")
    void equalsNullIds() {
        ParkingSpotJpaEntity s1 = new ParkingSpotJpaEntity(null);
        ParkingSpotJpaEntity s2 = new ParkingSpotJpaEntity(null);
        assertEquals(s1, s2);
    }
}
