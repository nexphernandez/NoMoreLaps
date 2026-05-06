package com.nomorelaps.adapters.out.persistence.jpa;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ReservationJpaEntityTest {

    @Test
    void testGettersAndSetters() {
        ReservationJpaEntity entity = new ReservationJpaEntity();
        assertNotNull(entity);

        entity.setId(1L);
        assertEquals(1L, entity.getId());

        LocalDateTime now = LocalDateTime.now();
        entity.setStartTime(now);
        assertEquals(now, entity.getStartTime());

        entity.setEndTime(now);
        assertEquals(now, entity.getEndTime());

        entity.setPrice(20.0);
        assertEquals(20.0, entity.getPrice());

        entity.setState("ACTIVE");
        assertEquals("ACTIVE", entity.getState());

        entity.setCreationTime(now);
        assertEquals(now, entity.getCreationTime());

        ParkingSpotJpaEntity spot = new ParkingSpotJpaEntity();
        entity.setParkingSpot(spot);
        assertEquals(spot, entity.getParkingSpot());

        UserJpaEntity user = new UserJpaEntity();
        entity.setUser(user);
        assertEquals(user, entity.getUser());

        Set<SanctionJpaEntity> sanctions = new HashSet<>();
        entity.setSanctions(sanctions);
        assertEquals(sanctions, entity.getSanctions());

        ReservationJpaEntity entity2 = new ReservationJpaEntity(2L);
        assertEquals(2L, entity2.getId());

        ReservationJpaEntity entity3 = new ReservationJpaEntity(3L, now, now, 30.0, "COMPLETED", now, spot, user, sanctions);
        assertEquals(3L, entity3.getId());
        assertEquals("COMPLETED", entity3.getState());
        assertEquals(30.0, entity3.getPrice());
    }

    @Test
    void testEqualsAndHashCode() {
        ReservationJpaEntity entity1 = new ReservationJpaEntity(1L);
        ReservationJpaEntity entity2 = new ReservationJpaEntity(1L);
        ReservationJpaEntity entity3 = new ReservationJpaEntity(2L);

        assertEquals(entity1, entity1);

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());

        assertNotEquals(entity1, entity3);

        assertNotEquals(entity1, null);
        assertNotEquals(entity1, new Object());

        ReservationJpaEntity entityNull1 = new ReservationJpaEntity(null);
        ReservationJpaEntity entityNull2 = new ReservationJpaEntity(null);
        assertEquals(entityNull1, entityNull2);
        assertNotEquals(entityNull1, entity1);
        assertNotEquals(entity1, entityNull1);
    }

    @Test
    void testOnCreate() {
        ReservationJpaEntity entity = new ReservationJpaEntity();
        assertNull(entity.getCreationTime());

        entity.onCreate(); 
        assertNotNull(entity.getCreationTime());

        LocalDateTime originalTime = entity.getCreationTime();
        entity.onCreate(); 
        assertEquals(originalTime, entity.getCreationTime());
    }
}
