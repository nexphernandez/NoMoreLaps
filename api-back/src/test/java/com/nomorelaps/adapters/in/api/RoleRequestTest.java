package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoleRequestTest {

    @Test
    @DisplayName("Should test getters, setters and empty constructor for RoleRequest")
    void testGettersAndSetters() {
        RoleRequest dto = new RoleRequest();
        assertNotNull(dto);

        dto.setName("dummy1");
        assertEquals("dummy1", dto.getName());
        dto.setDescription("dummy1");
        assertEquals("dummy1", dto.getDescription());
    }

}
