package com.nomorelaps.domain.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

class NotificationTest {

    @Test
    @DisplayName("Should test constructors, equals and hashCode of Notification")
    void testNotificationModel() {
        LocalDateTime now = LocalDateTime.now();
        Notification n1 = new Notification(1L, "msg", "type", true, 10L, now);
        
        assertEquals(1L, n1.getId());
        assertEquals("msg", n1.getMessage());
        assertEquals("type", n1.getType());
        assertTrue(n1.getIsRead());
        assertEquals(10L, n1.getCompanyId());
        assertEquals(now, n1.getCreatedAt());

        Notification n2 = new Notification(1L);
        Notification n3 = new Notification(2L);

        assertEquals(n1, n1); // this == o
        assertEquals(n1, n2);
        assertNotEquals(n1, n3);
        assertNotEquals(n1, null);
        assertNotEquals(n1, "string");
        
        assertEquals(n1.hashCode(), n2.hashCode());
        assertNotEquals(n1.hashCode(), n3.hashCode());
    }
}
