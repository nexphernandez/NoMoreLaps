package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class UserResponseTest {
    
    @Test
    void testGettersAndSetters() {
        UserResponse response = new UserResponse();
        assertNotNull(response);

        response.setId(1L);
        assertEquals(1L, response.getId());

        response.setName("Name");
        assertEquals("Name", response.getName());

        response.setEmail("email");
        assertEquals("email", response.getEmail());

        response.setPassword("pass");
        assertEquals("pass", response.getPassword());

        response.setCalendarEnable(true);
        assertTrue(response.isCalendarEnable());
        assertTrue(response.getEnable());

        response.setAvatar("avatar");
        assertEquals("avatar", response.getAvatar());
        
        response.setPhone("123");
        assertEquals("123", response.getPhone());

        LocalDateTime now = LocalDateTime.now();
        response.setCreateAt(now);
        assertEquals(now, response.getCreateAt());

        UserResponse r2 = new UserResponse(1L);
        assertEquals(1L, r2.getId());
        
        UserResponse r3 = new UserResponse(1L, "Name", "email", "pass", true, "avatar", "123", now);
        assertEquals(1L, r3.getId());
        assertEquals("Name", r3.getName());
    }

    @Test
    void testEqualsAndHashCode() {
        UserResponse response1 = new UserResponse(1L);
        UserResponse response2 = new UserResponse(1L);
        UserResponse response3 = new UserResponse(2L);

        assertEquals(response1, response1);
        
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
        
        assertNotEquals(response1, response3);
        
        assertNotEquals(response1, null);
        assertNotEquals(response1, new Object());
        
        UserResponse responseNull = new UserResponse(null);
        UserResponse responseNull2 = new UserResponse(null);
        assertEquals(responseNull, responseNull2);
        assertNotEquals(responseNull, response1);
        assertNotEquals(response1, responseNull);
    }
}
