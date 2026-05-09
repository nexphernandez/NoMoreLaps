package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for RoleRequest DTO.
 * Verifies that role metadata is correctly handled.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class RoleRequestTest {

    private RoleRequest roleRequest;

    @BeforeEach
    void setUp() {
        roleRequest = new RoleRequest();
    }

    @Test
    @DisplayName("Constructor - Should correctly initialize name and description")
    void shouldInitializeWithParameterizedConstructor() {
        RoleRequest adminRole = new RoleRequest("ADMIN", "System administrator access");

        assertEquals("ADMIN", adminRole.getName());
        assertEquals("System administrator access", adminRole.getDescription());
    }

    @Test
    @DisplayName("name - Should set and get the role name")
    void shouldSetAndGetName() {
        roleRequest.setName("USER");
        assertEquals("USER", roleRequest.getName());
    }

    @Test
    @DisplayName("description - Should set and get the role description")
    void shouldSetAndGetDescription() {
        roleRequest.setDescription("Standard user privileges");
        assertEquals("Standard user privileges", roleRequest.getDescription());
    }
}
