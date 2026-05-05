package com.nomorelaps.adapters.out.persistence.jpa;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DynamicPriceJpaEntityTest {

    @Test
    void testGettersAndSetters() {
        DynamicPriceJpaEntity entity = new DynamicPriceJpaEntity();
        assertNotNull(entity);

        entity.setId(1L);
        assertEquals(1L, entity.getId());

        entity.setDayOfWeek(1);
        assertEquals(1, entity.getDayOfWeek());

        entity.setStartHour("08:00");
        assertEquals("08:00", entity.getStartHour());

        entity.setEndHour("18:00");
        assertEquals("18:00", entity.getEndHour());

        entity.setMinPrice(5.0);
        assertEquals(5.0, entity.getMinPrice());

        entity.setMaxPrice(15.0);
        assertEquals(15.0, entity.getMaxPrice());

        LocalDateTime now = LocalDateTime.now();
        entity.setCreateAt(now);
        assertEquals(now, entity.getCreateAt());

        ParkingJpaEntity parking = new ParkingJpaEntity();
        entity.setParking(parking);
        assertEquals(parking, entity.getParking());

        DynamicPriceJpaEntity entity2 = new DynamicPriceJpaEntity(2L);
        assertEquals(2L, entity2.getId());

        DynamicPriceJpaEntity entity3 = new DynamicPriceJpaEntity(3L, 2, "09:00", "17:00", 6.0, 16.0, now, parking);
        assertEquals(3L, entity3.getId());
        assertEquals(2, entity3.getDayOfWeek());
    }

    @Test
    void testEqualsAndHashCode() {
        DynamicPriceJpaEntity entity1 = new DynamicPriceJpaEntity(1L);
        DynamicPriceJpaEntity entity2 = new DynamicPriceJpaEntity(1L);
        DynamicPriceJpaEntity entity3 = new DynamicPriceJpaEntity(2L);

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
        DynamicPriceJpaEntity entityNull1 = new DynamicPriceJpaEntity(null);
        DynamicPriceJpaEntity entityNull2 = new DynamicPriceJpaEntity(null);
        assertEquals(entityNull1, entityNull2);
        assertNotEquals(entityNull1, entity1);
        assertNotEquals(entity1, entityNull1);
    }

    @Test
    void testOnCreate() {
        DynamicPriceJpaEntity entity = new DynamicPriceJpaEntity();
        assertNull(entity.getCreateAt());

        entity.onCreate(); // Directly call pre-persist
        assertNotNull(entity.getCreateAt());

        LocalDateTime originalTime = entity.getCreateAt();
        entity.onCreate(); // Should not overwrite existing time
        assertEquals(originalTime, entity.getCreateAt());
    }
}
