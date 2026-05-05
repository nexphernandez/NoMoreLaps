package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UserRequestTest {
    
    @Test
    void testGettersAndSetters() {
        UserRequest req = new UserRequest();
        assertNotNull(req);

        req.setName("Name");
        assertEquals("Name", req.getName());

        req.setEmail("email");
        assertEquals("email", req.getEmail());

        req.setPassword("pass");
        assertEquals("pass", req.getPassword());

        req.setCalendarEnable(true);
        assertTrue(req.isCalendarEnable());
        assertTrue(req.getEnable());

        req.setAvatar("avatar");
        assertEquals("avatar", req.getAvatar());
        
        req.setPhone("123");
        assertEquals("123", req.getPhone());
        
        UserRequest r2 = new UserRequest("Name", "email", "pass", true, "avatar", "123");
        assertEquals("Name", r2.getName());
    }
}
