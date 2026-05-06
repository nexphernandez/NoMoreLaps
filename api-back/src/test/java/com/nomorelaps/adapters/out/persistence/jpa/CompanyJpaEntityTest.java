package com.nomorelaps.adapters.out.persistence.jpa;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CompanyJpaEntityTest {

    @Test
    void testGettersAndSetters() {
        CompanyJpaEntity entity = new CompanyJpaEntity();
        assertNotNull(entity);

        entity.setId(1L);
        assertEquals(1L, entity.getId());

        entity.setName("Name");
        assertEquals("Name", entity.getName());

        entity.setPassword("pass");
        assertEquals("pass", entity.getPassword());

        entity.setApiKey("key");
        assertEquals("key", entity.getApiKey());

        entity.setPhone("123");
        assertEquals("123", entity.getPhone());

        entity.setEmail("email");
        assertEquals("email", entity.getEmail());

        entity.setCif("cif");
        assertEquals("cif", entity.getCif());

        LocalDateTime now = LocalDateTime.now();
        entity.setRegisterDay(now);
        assertEquals(now, entity.getRegisterDay());

        UserJpaEntity user = new UserJpaEntity();
        entity.setUser(user);
        assertEquals(user, entity.getUser());

        Set<ParkingJpaEntity> parkings = new HashSet<>();
        entity.setParkings(parkings);
        assertEquals(parkings, entity.getParkings());

        CompanyJpaEntity entity2 = new CompanyJpaEntity(2L);
        assertEquals(2L, entity2.getId());

        CompanyJpaEntity entity3 = new CompanyJpaEntity(3L, "Name", "pass", "key", "123", "email", "cif", now, user, parkings);
        assertEquals(3L, entity3.getId());
        assertEquals("Name", entity3.getName());
    }

    @Test
    void testEqualsAndHashCode() {
        CompanyJpaEntity entity1 = new CompanyJpaEntity(1L);
        CompanyJpaEntity entity2 = new CompanyJpaEntity(1L);
        CompanyJpaEntity entity3 = new CompanyJpaEntity(2L);

        assertEquals(entity1, entity1);

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());

        assertNotEquals(entity1, entity3);

        assertNotEquals(entity1, null);
        assertNotEquals(entity1, new Object());

        CompanyJpaEntity entityNull1 = new CompanyJpaEntity(null);
        CompanyJpaEntity entityNull2 = new CompanyJpaEntity(null);
        assertEquals(entityNull1, entityNull2);
        assertNotEquals(entityNull1, entity1);
        assertNotEquals(entity1, entityNull1);
    }

    @Test
    void testOnCreate() {
        CompanyJpaEntity entity = new CompanyJpaEntity();
        assertNull(entity.getRegisterDay());

        entity.onCreate(); 
        assertNotNull(entity.getRegisterDay());

        LocalDateTime originalTime = entity.getRegisterDay();
        entity.onCreate(); 
        assertEquals(originalTime, entity.getRegisterDay());
    }
}
