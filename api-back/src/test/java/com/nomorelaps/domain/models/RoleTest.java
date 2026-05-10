package com.nomorelaps.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Role domain model.
 * Verifies data integrity and all branches of equals/hashCode with extreme granularity
 * as per the New Backend Test Refactoring Plan.
 */
class RoleTest {

    private Role testRole;

    @BeforeEach
    void setUp() {
        testRole = new Role(1L);
    }


    @Test
    @DisplayName("Constructor - Empty: Should initialize with default values")
    void shouldInitializeWithDefaultValuesUsingEmptyConstructor() {
        Role role = new Role();
        assertNull(role.getId());
    }

    @Test
    @DisplayName("Constructor - ID: Should initialize with specified identifier")
    void shouldInitializeWithIdUsingIdConstructor() {
        Role role = new Role(5L);
        assertEquals(5L, role.getId());
    }

    @Test
    @DisplayName("Constructor - Full: Should initialize all fields correctly")
    void shouldInitializeAllFieldsUsingFullConstructor() {
        Role role = new Role(1L, "ADMIN", "Administrator role");
        
        assertEquals(1L, role.getId());
        assertEquals("ADMIN", role.getName());
        assertEquals("Administrator role", role.getDescription());
    }


    @Test
    @DisplayName("Getter/Setter - Name: Should preserve string value")
    void shouldSetAndGetName() {
        testRole.setName("USER");
        assertEquals("USER", testRole.getName());
    }

    @Test
    @DisplayName("Getter/Setter - Description: Should preserve string value")
    void shouldSetAndGetDescription() {
        testRole.setDescription("Regular user");
        assertEquals("Regular user", testRole.getDescription());
    }


    @Test
    @DisplayName("equals - Same instance: Should return true")
    void equals_ShouldReturnTrueForSameInstance() {
        assertEquals(testRole, testRole);
    }

    @Test
    @DisplayName("equals - Null comparison: Should return false")
    void equals_ShouldReturnFalseForNull() {
        assertNotEquals(testRole, null);
    }

    @Test
    @DisplayName("equals - Different class: Should return false")
    void equals_ShouldReturnFalseForDifferentType() {
        assertNotEquals(testRole, "Some String");
    }

    @Test
    @DisplayName("equals - Same ID: Should return true")
    void equals_ShouldReturnTrueForSameId() {
        Role other = new Role(1L);
        assertEquals(testRole, other);
    }

    @Test
    @DisplayName("equals - Different ID: Should return false")
    void equals_ShouldReturnFalseForDifferentId() {
        Role other = new Role(2L);
        assertNotEquals(testRole, other);
    }

    @Test
    @DisplayName("equals - Both IDs null: Should return true")
    void equals_ShouldReturnTrueForBothIdsNull() {
        Role r1 = new Role();
        Role r2 = new Role();
        assertEquals(r1, r2);
    }

    @Test
    @DisplayName("equals - One ID null, other not: Should return false")
    void equals_ShouldReturnFalseWhenOneIdIsNull() {
        Role r1 = new Role(1L);
        Role r2 = new Role();
        assertNotEquals(r1, r2);
        assertNotEquals(r2, r1);
    }


    @Test
    @DisplayName("hashCode - Same ID: Should produce identical code")
    void hashCode_ShouldBeSameForSameId() {
        Role other = new Role(1L);
        assertEquals(testRole.hashCode(), other.hashCode());
    }

    @Test
    @DisplayName("hashCode - Different ID: Should produce different code")
    void hashCode_ShouldBeDifferentForDifferentId() {
        Role other = new Role(2L);
        assertNotEquals(testRole.hashCode(), other.hashCode());
    }

    @Test
    @DisplayName("hashCode - Null ID: Should produce stable code")
    void hashCode_ShouldBeStableForNullId() {
        Role r1 = new Role();
        Role r2 = new Role();
        assertEquals(r1.hashCode(), r2.hashCode());
    }
}
