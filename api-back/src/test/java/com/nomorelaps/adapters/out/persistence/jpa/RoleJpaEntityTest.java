package com.nomorelaps.adapters.out.persistence.jpa;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RoleJpaEntityTest {

    @Test
    void testGettersAndSetters() {
        RoleJpaEntity entity = new RoleJpaEntity();
        assertNotNull(entity);

        entity.setId(1L);
        assertEquals(1L, entity.getId());

        entity.setName("ADMIN");
        assertEquals("ADMIN", entity.getName());

        Set<UserJpaEntity> users = new HashSet<>();
        users.add(new UserJpaEntity());
        entity.setUsers(users);
        assertEquals(users, entity.getUsers());

        RoleJpaEntity entity2 = new RoleJpaEntity(2L);
        assertEquals(2L, entity2.getId());

        RoleJpaEntity entity3 = new RoleJpaEntity(3L, "USER", users);
        assertEquals(3L, entity3.getId());
        assertEquals("USER", entity3.getName());
        assertEquals(users, entity3.getUsers());
    }

    @Test
    void testEqualsAndHashCode() {
        RoleJpaEntity entity1 = new RoleJpaEntity(1L);
        RoleJpaEntity entity2 = new RoleJpaEntity(1L);
        RoleJpaEntity entity3 = new RoleJpaEntity(2L);

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
        RoleJpaEntity entityNull1 = new RoleJpaEntity(null);
        RoleJpaEntity entityNull2 = new RoleJpaEntity(null);
        assertEquals(entityNull1, entityNull2);
        assertNotEquals(entityNull1, entity1);
        assertNotEquals(entity1, entityNull1);
    }
}
