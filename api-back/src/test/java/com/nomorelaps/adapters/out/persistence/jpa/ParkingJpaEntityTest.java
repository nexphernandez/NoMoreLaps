package com.nomorelaps.adapters.out.persistence.jpa;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ParkingJpaEntityTest {

    @Test
    void testGettersAndSetters() {
        ParkingJpaEntity entity = new ParkingJpaEntity();
        assertNotNull(entity);

        entity.setId(1L);
        assertEquals(1L, entity.getId());

        entity.setAddress("Addr");
        assertEquals("Addr", entity.getAddress());

        entity.setName("Name");
        assertEquals("Name", entity.getName());

        entity.setLatitude(1.0);
        assertEquals(1.0, entity.getLatitude());

        entity.setLongitude(2.0);
        assertEquals(2.0, entity.getLongitude());

        LocalDateTime now = LocalDateTime.now();
        entity.setOpeningTime(now);
        assertEquals(now, entity.getOpeningTime());

        entity.setClosingTime(now);
        assertEquals(now, entity.getClosingTime());

        entity.setCreatedAt(now);
        assertEquals(now, entity.getCreatedAt());

        entity.setPricePerHour(3.5);
        assertEquals(3.5, entity.getPricePerHour());

        CompanyJpaEntity company = new CompanyJpaEntity();
        entity.setCompany(company);
        assertEquals(company, entity.getCompany());

        entity.setSanctionAmount(15.0);
        assertEquals(15.0, entity.getSanctionAmount());

        entity.setSanctionIntervalInMinutes(20);
        assertEquals(20, entity.getSanctionIntervalInMinutes());

        Set<ParkingSpotJpaEntity> spots = new HashSet<>();
        entity.setParkingSpots(spots);
        assertEquals(spots, entity.getParkingSpots());

        Set<DynamicPriceJpaEntity> prices = new HashSet<>();
        entity.setDynamicPrice(prices);
        assertEquals(prices, entity.getDynamicPrice());

        ParkingJpaEntity entity2 = new ParkingJpaEntity(2L);
        assertEquals(2L, entity2.getId());

        ParkingJpaEntity entity3 = new ParkingJpaEntity(3L, "Addr", "Name", 1.0, 2.0, now, now, now, 3.5, company, 15.0, 20, spots, prices);
        assertEquals(3L, entity3.getId());
        assertEquals("Name", entity3.getName());
    }

    @Test
    void testEqualsAndHashCode() {
        ParkingJpaEntity entity1 = new ParkingJpaEntity(1L);
        ParkingJpaEntity entity2 = new ParkingJpaEntity(1L);
        ParkingJpaEntity entity3 = new ParkingJpaEntity(2L);

        assertEquals(entity1, entity1);

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());

        assertNotEquals(entity1, entity3);

        assertNotEquals(entity1, null);
        assertNotEquals(entity1, new Object());

        ParkingJpaEntity entityNull1 = new ParkingJpaEntity(null);
        ParkingJpaEntity entityNull2 = new ParkingJpaEntity(null);
        assertEquals(entityNull1, entityNull2);
        assertNotEquals(entityNull1, entity1);
        assertNotEquals(entity1, entityNull1);
    }

    @Test
    void testOnCreate() {
        ParkingJpaEntity entity = new ParkingJpaEntity();
        assertNull(entity.getCreatedAt());

        entity.onCreate(); 
        assertNotNull(entity.getCreatedAt());

        LocalDateTime originalTime = entity.getCreatedAt();
        entity.onCreate(); 
        assertEquals(originalTime, entity.getCreatedAt());
    }
}
