package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class CompanyResponseTest {
    
    @Test
    void testGettersAndSetters() {
        CompanyResponse response = new CompanyResponse();
        assertNotNull(response);

        response.setId(1L);
        assertEquals(1L, response.getId());

        response.setName("Test");
        assertEquals("Test", response.getName());

        response.setPassword("pass");
        assertEquals("pass", response.getPassword());

        response.setPhone("123");
        assertEquals("123", response.getPhone());

        response.setEmail("email");
        assertEquals("email", response.getEmail());

        response.setCif("cif");
        assertEquals("cif", response.getCif());

        LocalDateTime now = LocalDateTime.now();
        response.setRegisterDay(now);
        assertEquals(now, response.getRegisterDay());
        
        CompanyResponse r2 = new CompanyResponse(1L);
        assertEquals(1L, r2.getId());
        
        CompanyResponse r3 = new CompanyResponse(1L, "Test", "pass", "123", "email", "cif", now);
        assertEquals(1L, r3.getId());
        assertEquals("Test", r3.getName());
    }

    @Test
    void testEqualsAndHashCode() {
        CompanyResponse response1 = new CompanyResponse(1L);
        CompanyResponse response2 = new CompanyResponse(1L);
        CompanyResponse response3 = new CompanyResponse(2L);

        // Same object
        assertEquals(response1, response1);
        
        // Equal objects
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
        
        // Different objects
        assertNotEquals(response1, response3);
        
        // Null and different class
        assertNotEquals(response1, null);
        assertNotEquals(response1, new Object());
        
        // Null ID coverage
        CompanyResponse responseNull = new CompanyResponse(null);
        CompanyResponse responseNull2 = new CompanyResponse(null);
        assertEquals(responseNull, responseNull2);
        assertNotEquals(responseNull, response1);
        assertNotEquals(response1, responseNull);
    }
}
