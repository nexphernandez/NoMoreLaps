package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CompanyRequestTest {
    
    @Test
    void testGettersAndSetters() {
        CompanyRequest req = new CompanyRequest();
        assertNotNull(req);

        req.setName("Test");
        assertEquals("Test", req.getName());

        req.setApiKey("key");
        assertEquals("key", req.getApiKey());

        req.setPassword("pass");
        assertEquals("pass", req.getPassword());

        req.setPhone("123");
        assertEquals("123", req.getPhone());

        req.setEmail("email");
        assertEquals("email", req.getEmail());

        req.setCif("cif");
        assertEquals("cif", req.getCif());
        
        CompanyRequest r2 = new CompanyRequest("Test", "key", "pass", "123", "email", "cif");
        assertEquals("Test", r2.getName());
    }
}
