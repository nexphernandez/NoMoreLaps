package com.nomorelaps.adapters.out.persistence.jpa;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;

/**
 * Unit tests for RoleJpaEntity.
 * Verifies data integrity, all constructors, and all branches of equals/hashCode.
 */
class RoleJpaEntityTest {

    private RoleJpaEntity testRole;

    @BeforeEach
    void setUp() {
        testRole = new RoleJpaEntity();
    }

    @Test
    @DisplayName("Constructor - Empty should initialize object")
    void shouldInitializeEmpty() {
        assertNotNull(new RoleJpaEntity());
    }

    @Test
    @DisplayName("Constructor - ID constructor should correctly set ID")
    void shouldInitializeWithId() {
        RoleJpaEntity entity = new RoleJpaEntity(5L);
        assertEquals(5L, entity.getId());
    }

    @Test
    @DisplayName("Constructor - Full constructor should correctly set all fields")
    void shouldInitializeWithAllFields() {
        Set<UserJpaEntity> users = new HashSet<>();
        RoleJpaEntity entity = new RoleJpaEntity(1L, "ADMIN", users);

        assertEquals(1L, entity.getId());
        assertEquals("ADMIN", entity.getName());
        assertEquals(users, entity.getUsers());
    }

    @Test
    @DisplayName("Setters - Should update basic fields")
    void shouldSetBasicFields() {
        testRole.setId(1L);
        assertEquals(1L, testRole.getId());
        
        testRole.setName("USER");
        assertEquals("USER", testRole.getName());
    }

    @Test
    @DisplayName("Relationships - Should update Users collection")
    void shouldSetRelationships() {
        Set<UserJpaEntity> users = new HashSet<>();
        testRole.setUsers(users);
        assertEquals(users, testRole.getUsers());
    }

    @Test
    @DisplayName("Equals - Should handle same object and same ID")
    void shouldVerifyEquality() {
        RoleJpaEntity r1 = new RoleJpaEntity(1L);
        RoleJpaEntity r2 = new RoleJpaEntity(1L);
        RoleJpaEntity r3 = new RoleJpaEntity(2L);

        assertEquals(r1, r1);
        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
        assertNotEquals(r1, r3);
        assertNotEquals(r1, null);
    }

    @Test
    @DisplayName("Equals - Should handle null IDs")
    void equalsNullIds() {
        RoleJpaEntity r1 = new RoleJpaEntity(null);
        RoleJpaEntity r2 = new RoleJpaEntity(null);
        assertEquals(r1, r2);
    }
}
