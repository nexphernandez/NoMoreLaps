package com.nomorelaps.adapters.out.persistence.jpa;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SanctionJpaEntityTest {

    @Test
    void testGettersAndSetters() {
        SanctionJpaEntity entity = new SanctionJpaEntity();
        assertNotNull(entity);

        entity.setId(1L);
        assertEquals(1L, entity.getId());

        entity.setAmount(50.0);
        assertEquals(50.0, entity.getAmount());

        entity.setReason("Late");
        assertEquals("Late", entity.getReason());

        entity.setPaid(true);
        assertTrue(entity.isPaid());
        assertTrue(entity.getPaid());

        LocalDateTime now = LocalDateTime.now();
        entity.setArrivalTime(now);
        assertEquals(now, entity.getArrivalTime());

        ReservationJpaEntity reservation = new ReservationJpaEntity();
        entity.setReservation(reservation);
        assertEquals(reservation, entity.getReservation());

        UserJpaEntity user = new UserJpaEntity();
        entity.setUser(user);
        assertEquals(user, entity.getUser());

        SanctionJpaEntity entity2 = new SanctionJpaEntity(2L);
        assertEquals(2L, entity2.getId());

        SanctionJpaEntity entity3 = new SanctionJpaEntity(3L, 100.0, "Very Late", false, now, reservation, user);
        assertEquals(3L, entity3.getId());
        assertEquals(100.0, entity3.getAmount());
    }

    @Test
    void testEqualsAndHashCode() {
        SanctionJpaEntity entity1 = new SanctionJpaEntity(1L);
        SanctionJpaEntity entity2 = new SanctionJpaEntity(1L);
        SanctionJpaEntity entity3 = new SanctionJpaEntity(2L);

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
        SanctionJpaEntity entityNull1 = new SanctionJpaEntity(null);
        SanctionJpaEntity entityNull2 = new SanctionJpaEntity(null);
        assertEquals(entityNull1, entityNull2);
        assertNotEquals(entityNull1, entity1);
        assertNotEquals(entity1, entityNull1);
    }

    @Test
    void testOnCreate() {
        SanctionJpaEntity entity = new SanctionJpaEntity();
        assertNull(entity.getArrivalTime());

        entity.onCreate(); // Directly call pre-persist
        assertNotNull(entity.getArrivalTime());

        LocalDateTime originalTime = entity.getArrivalTime();
        entity.onCreate(); // Should not overwrite existing time
        assertEquals(originalTime, entity.getArrivalTime());
    }
}
