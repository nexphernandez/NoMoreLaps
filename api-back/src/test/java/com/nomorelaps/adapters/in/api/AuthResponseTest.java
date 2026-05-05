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
        
        AuthResponse r2 = new AuthResponse("token", "msg");
        assertEquals("token", r2.getToken());
    }
}
