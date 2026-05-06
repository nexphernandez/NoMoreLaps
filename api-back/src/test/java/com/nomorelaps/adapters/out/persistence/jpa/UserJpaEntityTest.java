package com.nomorelaps.adapters.out.persistence.jpa;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserJpaEntityTest {

    @Test
    void testGettersAndSetters() {
        UserJpaEntity entity = new UserJpaEntity();
        assertNotNull(entity);

        entity.setId(1L);
        assertEquals(1L, entity.getId());

        entity.setName("Name");
        assertEquals("Name", entity.getName());

        entity.setEmail("email");
        assertEquals("email", entity.getEmail());

        entity.setPassword("pass");
        assertEquals("pass", entity.getPassword());

        entity.setCalendarEnable(true);
        assertTrue(entity.getCalendarEnable());

        entity.setAvatar("avatar");
        assertEquals("avatar", entity.getAvatar());

        entity.setPhone("123");
        assertEquals("123", entity.getPhone());

        LocalDateTime now = LocalDateTime.now();
        entity.setCreateAt(now);
        assertEquals(now, entity.getCreateAt());

        RoleJpaEntity role = new RoleJpaEntity();
        entity.setRole(role);
        assertEquals(role, entity.getRole());

        Set<CompanyJpaEntity> companies = new HashSet<>();
        entity.setCompanies(companies);
        assertEquals(companies, entity.getCompanies());

        Set<ReservationJpaEntity> reservations = new HashSet<>();
        entity.setReservations(reservations);
        assertEquals(reservations, entity.getReservations());

        Set<SanctionJpaEntity> sanctions = new HashSet<>();
        entity.setSanctions(sanctions);
        assertEquals(sanctions, entity.getSanctions());

        UserJpaEntity entity2 = new UserJpaEntity(2L);
        assertEquals(2L, entity2.getId());

        UserJpaEntity entity3 = new UserJpaEntity(3L, "Name", "email", "pass", true, "avatar", "123", now, role, companies, reservations, sanctions);
        assertEquals(3L, entity3.getId());
        assertEquals("Name", entity3.getName());
    }

    @Test
    void testEqualsAndHashCode() {
        UserJpaEntity entity1 = new UserJpaEntity(1L);
        UserJpaEntity entity2 = new UserJpaEntity(1L);
        UserJpaEntity entity3 = new UserJpaEntity(2L);

        assertEquals(entity1, entity1);

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());

        assertNotEquals(entity1, entity3);

        assertNotEquals(entity1, null);
        assertNotEquals(entity1, new Object());

        UserJpaEntity entityNull1 = new UserJpaEntity(null);
        UserJpaEntity entityNull2 = new UserJpaEntity(null);
        assertEquals(entityNull1, entityNull2);
        assertNotEquals(entityNull1, entity1);
        assertNotEquals(entity1, entityNull1);
    }

    @Test
    void testOnCreate() {
        UserJpaEntity entity = new UserJpaEntity();
        assertNull(entity.getCreateAt());

        entity.onCreate(); 
        assertNotNull(entity.getCreateAt());

        LocalDateTime originalTime = entity.getCreateAt();
        entity.onCreate(); 
        assertEquals(originalTime, entity.getCreateAt());
    }
}
