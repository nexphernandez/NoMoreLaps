package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AuthRequestTest {
    
    @Test
    void testGettersAndSetters() {
        AuthRequest req = new AuthRequest();
        assertNotNull(req);

        req.setEmail("email");
        assertEquals("email", req.getEmail());

        req.setPassword("pass");
        assertEquals("pass", req.getPassword());
        
        AuthRequest r2 = new AuthRequest("email", "pass");
        assertEquals("email", r2.getEmail());
    }
}
