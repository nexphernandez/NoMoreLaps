package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AuthResponseTest {
    
    @Test
    void testGettersAndSetters() {
        AuthResponse res = new AuthResponse();
        assertNotNull(res);

        res.setToken("token");
        assertEquals("token", res.getToken());

        res.setMessage("msg");
        assertEquals("msg", res.getMessage());

        res.setCompanyId(1L);
        assertEquals(1L, res.getCompanyId());

        res.setEmail("test@company.com");
        assertEquals("test@company.com", res.getEmail());

        res.setName("Test Company");
        assertEquals("Test Company", res.getName());

        res.setUserId(10L);
        assertEquals(10L, res.getUserId());
        
        AuthResponse r2 = new AuthResponse("token", "msg", 1L, "test@company.com", "Test Company", 10L);
        assertEquals("token", r2.getToken());
        assertEquals(1L, r2.getCompanyId());
        assertEquals("test@company.com", r2.getEmail());
        assertEquals(10L, r2.getUserId());
    }
}
