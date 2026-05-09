package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NotificationResponseTest {

    @Test
    @DisplayName("Should test all constructors and getters/setters")
    void testNotificationResponse() {
        LocalDateTime now = LocalDateTime.now();
        
        NotificationResponse r1 = new NotificationResponse(1L, "Msg", "TYPE", true, 10L, now);
        assertEquals(1L, r1.getId());
        assertEquals("Msg", r1.getMessage());
        assertEquals("TYPE", r1.getType());
        assertTrue(r1.getIsRead());
        assertEquals(10L, r1.getCompanyId());
        assertEquals(now, r1.getCreatedAt());

        NotificationResponse r2 = new NotificationResponse(2L);
        assertEquals(2L, r2.getId());

        NotificationResponse r3 = new NotificationResponse();
        r3.setId(3L);
        r3.setMessage("New Msg");
        r3.setType("NEW_TYPE");
        r3.setIsRead(false);
        r3.setCompanyId(20L);
        r3.setCreatedAt(now.plusDays(1));

        assertEquals(3L, r3.getId());
        assertEquals("New Msg", r3.getMessage());
        assertEquals("NEW_TYPE", r3.getType());
        assertFalse(r3.getIsRead());
        assertEquals(20L, r3.getCompanyId());
        assertEquals(now.plusDays(1), r3.getCreatedAt());
    }
}
