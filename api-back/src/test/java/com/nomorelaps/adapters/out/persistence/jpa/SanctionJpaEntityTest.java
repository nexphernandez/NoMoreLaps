package com.nomorelaps.adapters.out.persistence.jpa;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

/**
 * Unit tests for SanctionJpaEntity.
 * Verifies data integrity, all constructors, and all branches of equals/hashCode.
 */
class SanctionJpaEntityTest {

    private SanctionJpaEntity testSanction;

    @BeforeEach
    void setUp() {
        testSanction = new SanctionJpaEntity();
    }

    @Test
    @DisplayName("Constructor - Empty should initialize object")
    void shouldInitializeEmpty() {
        assertNotNull(new SanctionJpaEntity());
    }

    @Test
    @DisplayName("Constructor - ID constructor should correctly set ID")
    void shouldInitializeWithId() {
        SanctionJpaEntity entity = new SanctionJpaEntity(10L);
        assertEquals(10L, entity.getId());
    }

    @Test
    @DisplayName("Constructor - Full constructor should correctly set all fields")
    void shouldInitializeWithAllFields() {
        LocalDateTime now = LocalDateTime.now();
        UserJpaEntity user = new UserJpaEntity(10L);
        ReservationJpaEntity reservation = new ReservationJpaEntity(100L);

        SanctionJpaEntity entity = new SanctionJpaEntity(1L, 50.0, "Late", true, now, reservation, user);

        assertEquals(1L, entity.getId());
        assertEquals(50.0, entity.getAmount());
        assertEquals("Late", entity.getReason());
        assertTrue(entity.getPaid());
        assertEquals(now, entity.getArrivalTime());
        assertEquals(reservation, entity.getReservation());
        assertEquals(user, entity.getUser());
    }

    @Test
    @DisplayName("Setters - Should update basic fields")
    void shouldSetBasicFields() {
        testSanction.setId(1L);
        assertEquals(1L, testSanction.getId());
        
        testSanction.setAmount(100.0);
        assertEquals(100.0, testSanction.getAmount());
        
        testSanction.setReason("Expired");
        assertEquals("Expired", testSanction.getReason());
        
        testSanction.setPaid(true);
        assertTrue(testSanction.getPaid());
        assertTrue(testSanction.isPaid());
        
        LocalDateTime now = LocalDateTime.now();
        testSanction.setArrivalTime(now);
        assertEquals(now, testSanction.getArrivalTime());
    }

    @Test
    @DisplayName("Relationships - Should update User and Reservation")
    void shouldSetRelationships() {
        UserJpaEntity user = new UserJpaEntity(10L);
        ReservationJpaEntity reservation = new ReservationJpaEntity(100L);

        testSanction.setUser(user);
        testSanction.setReservation(reservation);

        assertEquals(user, testSanction.getUser());
        assertEquals(reservation, testSanction.getReservation());
    }

    @Test
    @DisplayName("onCreate - Should set arrival time if null")
    void shouldSetTimestampOnCreate() {
        assertNull(testSanction.getArrivalTime());
        testSanction.onCreate();
        assertNotNull(testSanction.getArrivalTime());
    }

    @Test
    @DisplayName("Equals - Should handle same object and same ID")
    void shouldVerifyEquality() {
        SanctionJpaEntity s1 = new SanctionJpaEntity(1L);
        SanctionJpaEntity s2 = new SanctionJpaEntity(1L);
        SanctionJpaEntity s3 = new SanctionJpaEntity(2L);

        assertEquals(s1, s1);
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1, s3);
        assertNotEquals(s1, null);
    }

    @Test
    @DisplayName("Equals - Should handle null IDs")
    void equalsNullIds() {
        SanctionJpaEntity s1 = new SanctionJpaEntity(null);
        SanctionJpaEntity s2 = new SanctionJpaEntity(null);
        assertEquals(s1, s2);
    }
}
