package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoleResponseTest {

    @Test
    @DisplayName("Should test getters, setters and empty constructor for RoleResponse")
    void testGettersAndSetters() {
        RoleResponse dto = new RoleResponse();
        assertNotNull(dto);

        dto.setId(1L);
        assertEquals(1L, dto.getId());
        dto.setName("dummy1");
        assertEquals("dummy1", dto.getName());
        dto.setDescription("dummy1");
        assertEquals("dummy1", dto.getDescription());
    }

    @Test
    @DisplayName("Should test parameterized constructors for RoleResponse")
    void testConstructors() {
        RoleResponse dto1 = new RoleResponse(1L);
        assertEquals(1L, dto1.getId());
        assertNull(dto1.getName());

        RoleResponse dto2 = new RoleResponse(2L, "Admin", "Admin role");
        assertEquals(2L, dto2.getId());
        assertEquals("Admin", dto2.getName());
        assertEquals("Admin role", dto2.getDescription());
    }


    @Test
    @DisplayName("Should test equals and hashCode branches for RoleResponse")
    void testEqualsAndHashCode() {
        RoleResponse dto1 = new RoleResponse();
        RoleResponse dto2 = new RoleResponse();
        RoleResponse dto3 = new RoleResponse();

        dto1.setId(1L);
        dto2.setId(1L);
        dto3.setId(2L);
        dto1.setName("dummy1");
        dto2.setName("dummy1");
        dto3.setName("dummy2");
        dto1.setDescription("dummy1");
        dto2.setDescription("dummy1");
        dto3.setDescription("dummy2");

        assertEquals(dto1, dto1);
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1, null);
        assertNotEquals(dto1, new Object());
        assertEquals(dto1.hashCode(), dto2.hashCode());

        RoleResponse tempid = new RoleResponse();
        tempid.setId(1L);
        tempid.setName("dummy1");
        tempid.setDescription("dummy1");
        tempid.setId(null);
        dto1.equals(tempid);
        tempid.equals(dto1);
        tempid.setId(2L);
        dto1.equals(tempid);

        RoleResponse tempname = new RoleResponse();
        tempname.setId(1L);
        tempname.setName("dummy1");
        tempname.setDescription("dummy1");
        tempname.setName(null);
        dto1.equals(tempname);
        tempname.equals(dto1);
        tempname.setName("dummy2");
        dto1.equals(tempname);

        RoleResponse tempdescription = new RoleResponse();
        tempdescription.setId(1L);
        tempdescription.setName("dummy1");
        tempdescription.setDescription("dummy1");
        tempdescription.setDescription(null);
        dto1.equals(tempdescription);
        tempdescription.equals(dto1);
        tempdescription.setDescription("dummy2");
        dto1.equals(tempdescription);

    }
}
