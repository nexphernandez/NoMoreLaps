package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for RoleResponse DTO.
 * Verifies that role response data is correctly mapped and logical equality is maintained.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class RoleResponseTest {

    private RoleResponse roleResponse;

    @BeforeEach
    void setUp() {
        roleResponse = new RoleResponse();
    }

    @Test
    @DisplayName("Constructor(id) - Should initialize with ID")
    void shouldInitializeWithIdConstructor() {
        RoleResponse idOnly = new RoleResponse(15L);
        assertEquals(15L, idOnly.getId());
        assertNull(idOnly.getName());
    }

    @Test
    @DisplayName("Full Constructor - Should correctly map all fields")
    void shouldInitializeWithFullConstructor() {
        RoleResponse fullResponse = new RoleResponse(1L, "MANAGER", "Can manage parking spots");

        assertEquals(1L, fullResponse.getId());
        assertEquals("MANAGER", fullResponse.getName());
        assertEquals("Can manage parking spots", fullResponse.getDescription());
    }

    @Test
    @DisplayName("id - Should set and get the ID")
    void shouldSetAndGetId() {
        roleResponse.setId(10L);
        assertEquals(10L, roleResponse.getId());
    }

    @Test
    @DisplayName("name - Should set and get the name")
    void shouldSetAndGetName() {
        roleResponse.setName("MODERATOR");
        assertEquals("MODERATOR", roleResponse.getName());
    }

    @Test
    @DisplayName("description - Should set and get the description")
    void shouldSetAndGetDescription() {
        roleResponse.setDescription("Moderates user comments");
        assertEquals("Moderates user comments", roleResponse.getDescription());
    }

    @Test
    @DisplayName("equals - Should be equal for same ID")
    void shouldBeEqualForSameId() {
        RoleResponse first = new RoleResponse(1L);
        RoleResponse second = new RoleResponse(1L);
        RoleResponse third = new RoleResponse(2L);

        assertEquals(first, first, "Should be equal to itself");
        assertEquals(first, second, "Should be equal if IDs match");
        assertNotEquals(first, third, "Should not be equal if IDs differ");
        assertNotEquals(first, null, "Should not be equal to null");
        assertNotEquals(first, "different", "Should return false for different class (instanceof test)");
    }

    @Test
    @DisplayName("hashCode - Should be consistent for same ID")
    void shouldHaveConsistentHashCode() {
        RoleResponse first = new RoleResponse(1L);
        RoleResponse second = new RoleResponse(1L);

        assertEquals(first.hashCode(), second.hashCode());
    }
}
