package com.nomorelaps.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoleTest {

    @Test
    @DisplayName("Role - Constructors and Getters/Setters should work")
    void testRole() {
        Role r1 = new Role();
        Role r2 = new Role(1L);
        assertEquals(1L, r2.getId());

        Role r3 = new Role(1L, "ADMIN", "Administrator");
        assertEquals("ADMIN", r3.getName());
        assertEquals("Administrator", r3.getDescription());

        r1.setId(2L);
        r1.setName("USER");
        r1.setDescription("Standard User");

        assertEquals(2L, r1.getId());
        assertEquals("USER", r1.getName());
    }

    @Test
    @DisplayName("Role - Equals and HashCode")
    void testRoleEquals() {
        Role r1 = new Role(1L);
        Role r2 = new Role(1L);
        Role r3 = new Role(2L);

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
        assertNotEquals(r1, r3);
        assertNotEquals(r1, null);
        assertNotEquals(r1, new Object());
        assertEquals(r1, r1);
    }
}
