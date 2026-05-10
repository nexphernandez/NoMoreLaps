package com.nomorelaps.adapters.out.persistence.jpa;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Unit tests for ReservationJpaEntity.
 * Verifies data integrity, all constructors, and all branches of equals/hashCode.
 */
class ReservationJpaEntityTest {

    private ReservationJpaEntity testReservation;

    @BeforeEach
    void setUp() {
        testReservation = new ReservationJpaEntity();
    }

    @Test
    @DisplayName("Constructor - Empty should initialize object")
    void shouldInitializeEmpty() {
        assertNotNull(new ReservationJpaEntity());
    }

    @Test
    @DisplayName("Constructor - ID constructor should correctly set ID")
    void shouldInitializeWithId() {
        ReservationJpaEntity entity = new ReservationJpaEntity(50L);
        assertEquals(50L, entity.getId());
    }

    @Test
    @DisplayName("Constructor - Full constructor should correctly set all fields")
    void shouldInitializeWithAllFields() {
        LocalDateTime now = LocalDateTime.now();
        UserJpaEntity user = new UserJpaEntity(10L);
        ParkingSpotJpaEntity spot = new ParkingSpotJpaEntity(20L);
        Set<SanctionJpaEntity> sanctions = new HashSet<>();

        ReservationJpaEntity entity = new ReservationJpaEntity(1L, now, now.plusHours(1), 15.0, "ACTIVE", 
            now, spot, user, sanctions);

        assertEquals(1L, entity.getId());
        assertEquals(now, entity.getStartTime());
        assertEquals(now.plusHours(1), entity.getEndTime());
        assertEquals(15.0, entity.getPrice());
        assertEquals("ACTIVE", entity.getState());
        assertEquals(now, entity.getCreationTime());
        assertEquals(spot, entity.getParkingSpot());
        assertEquals(user, entity.getUser());
        assertEquals(sanctions, entity.getSanctions());
    }

    @Test
    @DisplayName("Setters - Should update basic fields")
    void shouldSetBasicFields() {
        LocalDateTime now = LocalDateTime.now();
        testReservation.setId(1L);
        assertEquals(1L, testReservation.getId());
        
        testReservation.setStartTime(now);
        assertEquals(now, testReservation.getStartTime());
        
        testReservation.setEndTime(now.plusHours(2));
        assertEquals(now.plusHours(2), testReservation.getEndTime());
        
        testReservation.setPrice(25.0);
        assertEquals(25.0, testReservation.getPrice());
        
        testReservation.setBasePrice(20.0);
        assertEquals(20.0, testReservation.getBasePrice());
        
        testReservation.setState("CANCELLED");
        assertEquals("CANCELLED", testReservation.getState());
        
        testReservation.setPaid(true);
        assertTrue(testReservation.isPaid());
        
        testReservation.setCreationTime(now);
        assertEquals(now, testReservation.getCreationTime());
    }

    @Test
    @DisplayName("Relationships - Should update User, Spot and Sanctions")
    void shouldSetRelationships() {
        UserJpaEntity user = new UserJpaEntity(10L);
        ParkingSpotJpaEntity spot = new ParkingSpotJpaEntity(20L);
        Set<SanctionJpaEntity> sanctions = new HashSet<>();

        testReservation.setUser(user);
        testReservation.setParkingSpot(spot);
        testReservation.setSanctions(sanctions);

        assertEquals(user, testReservation.getUser());
        assertEquals(spot, testReservation.getParkingSpot());
        assertEquals(sanctions, testReservation.getSanctions());
    }

    @Test
    @DisplayName("onCreate - Should set creation timestamp if null")
    void shouldSetTimestampOnCreateIfNull() {
        assertNull(testReservation.getCreationTime());
        testReservation.onCreate();
        assertNotNull(testReservation.getCreationTime());
    }

    @Test
    @DisplayName("onCreate - Should not overwrite creation timestamp if already set")
    void shouldNotOverwriteTimestampOnCreate() {
        LocalDateTime past = LocalDateTime.now().minusDays(1);
        testReservation.setCreationTime(past);
        testReservation.onCreate();
        assertEquals(past, testReservation.getCreationTime());
    }

    @Test
    @DisplayName("Equals - Should handle same object and same ID")
    void shouldVerifyEquality() {
        ReservationJpaEntity r1 = new ReservationJpaEntity(1L);
        ReservationJpaEntity r2 = new ReservationJpaEntity(1L);
        ReservationJpaEntity r3 = new ReservationJpaEntity(2L);

        assertEquals(r1, r1);
        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
        assertNotEquals(r1, r3);
        assertNotEquals(r1, null);
    }

    @Test
    @DisplayName("Equals - Should handle null IDs")
    void equalsNullIds() {
        ReservationJpaEntity r1 = new ReservationJpaEntity(null);
        ReservationJpaEntity r2 = new ReservationJpaEntity(null);
        assertEquals(r1, r2);
    }
}
