package com.nomorelaps.adapters.out.persistence.jpa;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ParkingSpotJpaEntityTest {

    @Test
    void testGettersAndSetters() {
        ParkingSpotJpaEntity entity = new ParkingSpotJpaEntity();
        assertNotNull(entity);

        entity.setId(1L);
        assertEquals(1L, entity.getId());

        entity.setState(false);
        assertFalse(entity.isState());
        assertFalse(entity.getState());

        entity.setNumber(10);
        assertEquals(10, entity.getNumber());

        LocalDateTime now = LocalDateTime.now();
        entity.setRegisterDate(now);
        assertEquals(now, entity.getRegisterDate());

        ParkingJpaEntity parking = new ParkingJpaEntity();
        entity.setParking(parking);
        assertEquals(parking, entity.getParking());

        Set<ReservationJpaEntity> reservations = new HashSet<>();
        entity.setReservations(reservations);
        assertEquals(reservations, entity.getReservations());

        ParkingSpotJpaEntity entity2 = new ParkingSpotJpaEntity(2L);
        assertEquals(2L, entity2.getId());

        ParkingSpotJpaEntity entity3 = new ParkingSpotJpaEntity(3L, true, 5, now, parking, reservations);
        assertEquals(3L, entity3.getId());
        assertTrue(entity3.getState());
        assertEquals(5, entity3.getNumber());
    }

    @Test
    void testEqualsAndHashCode() {
        ParkingSpotJpaEntity entity1 = new ParkingSpotJpaEntity(1L);
        ParkingSpotJpaEntity entity2 = new ParkingSpotJpaEntity(1L);
        ParkingSpotJpaEntity entity3 = new ParkingSpotJpaEntity(2L);

        // Same object
        assertEquals(entity1, entity1);

        // Equal objects
        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());

        // Different objects
        assertNotEquals(entity1, entity3);

        // Null and different class
        assertNotEquals(entity1, null);
        assertNotEquals(entity1, new Object());

        // Null ID coverage
        ParkingSpotJpaEntity entityNull1 = new ParkingSpotJpaEntity(null);
        ParkingSpotJpaEntity entityNull2 = new ParkingSpotJpaEntity(null);
        assertEquals(entityNull1, entityNull2);
        assertNotEquals(entityNull1, entity1);
        assertNotEquals(entity1, entityNull1);
    }

    @Test
    void testOnCreate() {
        ParkingSpotJpaEntity entity = new ParkingSpotJpaEntity();
        assertNull(entity.getRegisterDate());

        entity.onCreate(); // Directly call pre-persist
        assertNotNull(entity.getRegisterDate());

        LocalDateTime originalTime = entity.getRegisterDate();
        entity.onCreate(); // Should not overwrite existing time
        assertEquals(originalTime, entity.getRegisterDate());
    }
}
